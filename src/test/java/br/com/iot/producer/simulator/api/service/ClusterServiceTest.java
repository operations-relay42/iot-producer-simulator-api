package br.com.iot.producer.simulator.api.service;

import br.com.iot.producer.simulator.api.controller.events.request.ClusterEventRequest;
import br.com.iot.producer.simulator.api.controller.events.request.ImmutableClusterEventRequest;
import br.com.iot.producer.simulator.api.mapper.EventMapper;
import br.com.iot.producer.simulator.api.model.event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ClusterServiceTest {

    private ClusterService testClass;

    private EventService eventService;

    @BeforeEach
    void setUp() {
        eventService = mock(EventService.class);
        when(eventService.processEvent(any(), anyInt(), anyInt())).thenReturn(Flux.empty());
        testClass = new ClusterService(eventService, Mappers.getMapper(EventMapper.class));
    }

    @Test
    void testProcessEventCluster() {
        ClusterEventRequest clusterEventRequest = new ImmutableClusterEventRequest.Builder()
                .total(2)
                .clusterSize(3)
                .type("Temperature").build();
        StepVerifier.create(testClass.processSingleEventCluster(clusterEventRequest)).verifyComplete();

        verify(eventService, times(3)).processEvent(any(Event.class), anyInt(), anyInt());
    }

    @Test
    void testProduceEventsSingle() {
        testClass = spy(new ClusterService(eventService, Mappers.getMapper(EventMapper.class)) {
            @Override
            protected ParallelFlux<Void> processSingleEventCluster(ClusterEventRequest request) {
                return ParallelFlux.from(Flux.empty());
            }
        });

        ClusterEventRequest clusterEventRequest = new ImmutableClusterEventRequest.Builder()
                .total(2)
                .type("Temperature").build();


        StepVerifier.create(testClass.processAllClusters(List.of(clusterEventRequest))).verifyComplete();

        verify(testClass, times(1)).processSingleEventCluster(any(ClusterEventRequest.class));
    }

    @Test
    void testProduceEventsMultiple() {
        testClass = spy(new ClusterService(eventService, Mappers.getMapper(EventMapper.class)) {
            @Override
            protected ParallelFlux<Void> processSingleEventCluster(ClusterEventRequest request) {
                return ParallelFlux.from(Flux.empty());
            }
        });

        ClusterEventRequest clusterEventRequest1 = new ImmutableClusterEventRequest.Builder()
                .total(2)
                .type("Temperature").build();


        ClusterEventRequest clusterEventRequest2 = new ImmutableClusterEventRequest.Builder()
                .total(2)
                .type("cpu").build();


        StepVerifier.create(testClass.processAllClusters(List.of(clusterEventRequest1, clusterEventRequest2))).verifyComplete();

        verify(testClass, times(2)).processSingleEventCluster(any(ClusterEventRequest.class));
    }

}