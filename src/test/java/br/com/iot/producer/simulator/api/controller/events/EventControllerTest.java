package br.com.iot.producer.simulator.api.controller.events;

import br.com.iot.producer.simulator.api.ApplicationStarter;
import br.com.iot.producer.simulator.api.config.WebSecurityConfigStub;
import br.com.iot.producer.simulator.api.controller.events.request.EventRequest;
import br.com.iot.producer.simulator.api.controller.events.request.ImmutableEventRequest;
import br.com.iot.producer.simulator.api.service.EventService;
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

/** Tests for {@link EventController} */
@WebFluxTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {ApplicationStarter.class, WebSecurityConfigStub.class})
class EventControllerTest {

    private WebTestClient webClient;

    @MockBean
    private EventService eventService;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        webClient = WebTestClient.bindToApplicationContext(applicationContext)
                .configureClient()
                .baseUrl("/events")
                .build();
        when(eventService.processAllEvents(anyList())).thenReturn(ParallelFlux.from(Flux.empty()));
    }

    @Test
    void testProduceEventsOK() {
        final EventRequest request = new ImmutableEventRequest.Builder()
                .total(10)
                .type("TEMPERATURE")
                .name("Name")
                .clusterId(123L)
                .id(1234L)
                .build();

        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isAccepted();

        verify(eventService, only()).processAllEvents(anyList());
    }

    @Test
    void testProduceEventsMissingName() {
        final EventRequest request = new ImmutableEventRequest.Builder()
                .total(10)
                .type("TEMPERATURE")
                .clusterId(123L)
                .id(1234L)
                .build();

        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isBadRequest();

        verify(eventService, never()).processAllEvents(anyList());
    }


    @Test
    void testProduceEventsInvalidEmpty() {
        webClient.post()
                .bodyValue(List.of())
                .exchange()
                .expectStatus()
                .isBadRequest();

        verify(eventService, never()).processAllEvents(anyList());
    }

    @Test
    void testProduceEventsInvalidHearthSmaller() {
        final EventRequest request = new ImmutableEventRequest.Builder()
                .total(10)
                .type("TEMPERATURE")
                .clusterId(123L)
                .id(1234L)
                .heartBeat(-1)
                .build();
        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isBadRequest();

        verify(eventService, never()).processAllEvents(anyList());
    }

    @Test
    void testProduceEventsInvalidHeartBeatBigger() {
        final EventRequest request = new ImmutableEventRequest.Builder()
                .total(10)
                .type("TEMPERATURE")
                .clusterId(123L)
                .id(1234L)
                .heartBeat(80)
                .build();

        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isBadRequest();

        verify(eventService, never()).processAllEvents(anyList());
    }

    @Test
    void testProduceEventsInvalidTotalNegative() {
        final EventRequest request = new ImmutableEventRequest.Builder()
                .total(-10)
                .type("TEMPERATURE")
                .clusterId(123L)
                .id(1234L)
                .build();
        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isBadRequest();

        verify(eventService, never()).processAllEvents(anyList());
    }

    @Test
    void testProduceEventsMissingId() {
        final EventRequest request = new ImmutableEventRequest.Builder()
                .total(10)
                .type("TEMPERATURE")
                .clusterId(123L)
                .build();

        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isBadRequest();

        verify(eventService, never()).processAllEvents(anyList());
    }


    @Test
    void testProduceEventsIdNegative() {
        final EventRequest request = new ImmutableEventRequest.Builder()
                .total(10)
                .id(-1L)
                .type("TEMPERATURE")
                .clusterId(123L)
                .build();

        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isBadRequest();

        verify(eventService, never()).processAllEvents(anyList());
    }
}