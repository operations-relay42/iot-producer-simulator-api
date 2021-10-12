package com.relay.iot.producer.simulator.api.controller.events;

import com.relay.iot.producer.simulator.api.ApplicationStarter;
import com.relay.iot.producer.simulator.api.config.WebSecurityConfigStub;
import com.relay.iot.producer.simulator.api.controller.events.request.ClusterEventRequest;
import com.relay.iot.producer.simulator.api.controller.events.request.ImmutableClusterEventRequest;
import com.relay.iot.producer.simulator.api.service.ClusterService;
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
class ClusterControllerTest {

    private WebTestClient webClient;

    @MockBean
    private ClusterService clusterService;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        webClient = WebTestClient.bindToApplicationContext(applicationContext)
                .configureClient()
                .baseUrl("/clusters")
                .build();
        when(clusterService.processAllClusters(anyList())).thenReturn(ParallelFlux.from(Flux.empty()));
    }

    @Test
    void testProduceEventsOK() {
        final ClusterEventRequest request = new ImmutableClusterEventRequest.Builder()
                .total(10)
                .type("TEMPERATURE")
                .name("Name")
                .clusterId(123L)
                .build();

        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isAccepted();

        verify(clusterService, only()).processAllClusters(anyList());
    }

    @Test
    void testProduceEventsMissingName() {
        final ClusterEventRequest request = new ImmutableClusterEventRequest.Builder()
                .total(10)
                .clusterId(123L)
                .type("TEMPERATURE").build();
        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isBadRequest();

        verify(clusterService, never()).processAllClusters(anyList());
    }


    @Test
    void testProduceEventsInvalidEmpty() {
        webClient.post()
                .bodyValue(List.of())
                .exchange()
                .expectStatus()
                .isBadRequest();

        verify(clusterService, never()).processAllClusters(anyList());
    }

    @Test
    void testProduceEventsInvalidHearthSmaller() {
        final ClusterEventRequest request = new ImmutableClusterEventRequest.Builder()
                .name("Name")
                .clusterId(123L)
                .total(10)
                .type("TEMPERATURE")
                .heartBeat(-1)
                .build();
        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isBadRequest();

        verify(clusterService, never()).processAllClusters(anyList());
    }

    @Test
    void testProduceEventsInvalidHeartBeatBigger() {
        final ClusterEventRequest request = new ImmutableClusterEventRequest.Builder()
                .name("Name")
                .clusterId(123L)
                .total(10)
                .type("TEMPERATURE")
                .heartBeat(80)
                .build();
        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isBadRequest();

        verify(clusterService, never()).processAllClusters(anyList());
    }

    @Test
    void testProduceEventsInvalidTotalNegative() {
        final ClusterEventRequest request = new ImmutableClusterEventRequest.Builder()
                .name("Name")
                .clusterId(123L)
                .total(-10)
                .type("TEMPERATURE")
                .build();
        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isBadRequest();

        verify(clusterService, never()).processAllClusters(anyList());
    }

    @Test
    void testProduceEventsInvalidClusterNegative() {
        final ClusterEventRequest request = new ImmutableClusterEventRequest.Builder()
                .name("Name")
                .total(10)
                .type("TEMPERATURE")
                .clusterSize(-1)
                .build();
        webClient.post()
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus()
                .isBadRequest();

        verify(clusterService, never()).processAllClusters(anyList());
    }
}