package br.com.iot.producer.simulator.api.controller.events;

import br.com.iot.producer.simulator.api.ApplicationStarter;
import br.com.iot.producer.simulator.api.config.WebSecurityConfigStub;
import br.com.iot.producer.simulator.api.controller.events.request.SensorEventRequest;
import br.com.iot.producer.simulator.api.model.EventType;
import br.com.iot.producer.simulator.api.service.SensorEventsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@WebFluxTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {ApplicationStarter.class, WebSecurityConfigStub.class})
class SensorEventsControllerTest {

    private WebTestClient webClient;

    @MockBean
    private SensorEventsService sensorEventsService;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        webClient = WebTestClient.bindToApplicationContext(applicationContext)
                .configureClient()
                .baseUrl("/sensor/events")
                .build();
        when(sensorEventsService.produceEvents(anyList())).thenReturn(Mono.empty());
    }

    @Test
    void testProduceEventsOK() {
        final SensorEventRequest request = new SensorEventRequest(10, EventType.TEMPERATURE, 10, null);
        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isAccepted();

        verify(sensorEventsService, only()).produceEvents(anyList());
    }

    @Test
    void testProduceEventsInvalidEmpty() {
        webClient.post()
                .bodyValue(List.of())
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);

        verify(sensorEventsService, never()).produceEvents(anyList());
    }

    @Test
    void testProduceEventsInvalidEverySmaller() {
        final SensorEventRequest request = new SensorEventRequest(10, EventType.TEMPERATURE, -10, null);
        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);

        verify(sensorEventsService, never()).produceEvents(anyList());
    }

    @Test
    void testProduceEventsInvalidEveryBigger() {
        final SensorEventRequest request = new SensorEventRequest(10, EventType.TEMPERATURE, 80, null);
        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);

        verify(sensorEventsService, never()).produceEvents(anyList());
    }

    @Test
    void testProduceEventsInvalidEveryNull() {
        final SensorEventRequest request = new SensorEventRequest(10, EventType.TEMPERATURE, null, null);
        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isAccepted();

        verify(sensorEventsService, only()).produceEvents(anyList());
    }

    @Test
    void testProduceEventsInvalidTotalNegative() {
        final SensorEventRequest request = new SensorEventRequest(-10, EventType.TEMPERATURE, 10, null);
        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);

        verify(sensorEventsService, never()).produceEvents(anyList());
    }

    @Test
    void testProduceEventsInvalidTotalNull() {
        final SensorEventRequest request = new SensorEventRequest(null, EventType.TEMPERATURE, 10, null);
        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);

        verify(sensorEventsService, never()).produceEvents(anyList());
    }

    @Test
    void testProduceEventsInvalidEventType() {
        final SensorEventRequest request = new SensorEventRequest(10, null, 10, null);
        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);

        verify(sensorEventsService, never()).produceEvents(anyList());
    }
}