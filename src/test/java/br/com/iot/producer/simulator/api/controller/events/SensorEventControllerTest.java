package br.com.iot.producer.simulator.api.controller.events;

import br.com.iot.producer.simulator.api.ApplicationStarter;
import br.com.iot.producer.simulator.api.config.WebSecurityConfigStub;
import br.com.iot.producer.simulator.api.controller.events.request.ImmutableSensorEventRequest;
import br.com.iot.producer.simulator.api.controller.events.request.SensorEventRequest;
import br.com.iot.producer.simulator.api.service.SensorEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;

import java.util.List;

import static org.mockito.Mockito.*;

@WebFluxTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {ApplicationStarter.class, WebSecurityConfigStub.class})
class SensorEventControllerTest {

    private WebTestClient webClient;

    @MockBean
    private SensorEventService sensorEventService;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        webClient = WebTestClient.bindToApplicationContext(applicationContext)
                .configureClient()
                .baseUrl("/events")
                .build();
        when(sensorEventService.produceEvents(anyList())).thenReturn(ParallelFlux.from(Flux.empty()));
    }

    @Test
    void testProduceEventsOK() {
        final SensorEventRequest request = new ImmutableSensorEventRequest.Builder().total(10).type("TEMPERATURE").build();
        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isAccepted();

        verify(sensorEventService, only()).produceEvents(anyList());
    }

    @Test
    void testProduceEventsInvalidEmpty() {
        webClient.post()
                .bodyValue(List.of())
                .exchange()
                .expectStatus()
                .isBadRequest();

        verify(sensorEventService, never()).produceEvents(anyList());
    }

    @Test
    void testProduceEventsInvalidHearthSmaller() {
        final SensorEventRequest request = new ImmutableSensorEventRequest.Builder().total(10).type("TEMPERATURE").heartBeat(-1).build();
        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isBadRequest();

        verify(sensorEventService, never()).produceEvents(anyList());
    }

    @Test
    void testProduceEventsInvalidHeartBeatBigger() {
        final SensorEventRequest request = new ImmutableSensorEventRequest.Builder().total(10).type("TEMPERATURE").heartBeat(80).build();
        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isBadRequest();

        verify(sensorEventService, never()).produceEvents(anyList());
    }

    @Test
    void testProduceEventsInvalidTotalNegative() {
        final SensorEventRequest request = new ImmutableSensorEventRequest.Builder().total(-10).type("TEMPERATURE").build();
        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isBadRequest();

        verify(sensorEventService, never()).produceEvents(anyList());
    }

    @Test
    void testProduceEventsInvalidClusterNegative() {
        final SensorEventRequest request = new ImmutableSensorEventRequest.Builder().total(10).type("TEMPERATURE").clusterSize(-1).build();
        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isBadRequest();

        verify(sensorEventService, never()).produceEvents(anyList());
    }
}