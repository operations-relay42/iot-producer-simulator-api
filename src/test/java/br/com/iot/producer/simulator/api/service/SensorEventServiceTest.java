package br.com.iot.producer.simulator.api.service;

import br.com.iot.producer.simulator.api.controller.events.request.ImmutableSensorEventRequest;
import br.com.iot.producer.simulator.api.controller.events.request.SensorEventRequest;
import br.com.iot.producer.simulator.api.model.event.RandomSensorEvent;
import br.com.iot.producer.simulator.api.model.event.SensorEvent;
import br.com.iot.producer.simulator.api.stream.producer.SensorEventProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

class SensorEventServiceTest {

    private SensorEventService testClass;
    private SensorEventProducer eventProducer;

    @BeforeEach
    void setUp() {
        eventProducer = mock(SensorEventProducer.class);
        testClass = new SensorEventService(eventProducer);
        when(eventProducer.sendEvent(any(SensorEvent.class))).thenReturn(Mono.empty());
    }

    @Test
    void testSendEvent() {

        RandomSensorEvent randomSensorEvent = new RandomSensorEvent("TEMPERATURE");
        StepVerifier.create(testClass.sendEvent(randomSensorEvent, 1)).verifyComplete();

        verify(eventProducer, only()).sendEvent(any());
    }

    @Test
    void testProcessEvent() {
        testClass = spy(new SensorEventService(eventProducer) {
            @Override
            protected Mono<Void> sendEvent(SensorEvent sensorEvent, Integer clusterId) {
                return Mono.empty();
            }
        });
        SensorEventRequest sensorEventRequest = new ImmutableSensorEventRequest.Builder()
                .total(2)
                .heartBeat(1)
                .type("Temperature").build();
        StepVerifier.create(testClass.processEvent(sensorEventRequest, 1)).verifyComplete();

        verify(testClass, times(2)).sendEvent(any(SensorEvent.class), anyInt());
    }

    @Test
    void testProcessEventCluster() {
        testClass = spy(new SensorEventService(eventProducer) {
            @Override
            protected Flux<Void> processEvent(SensorEventRequest request, Integer clusterId) {
                return Flux.empty();
            }
        });

        SensorEventRequest sensorEventRequest = new ImmutableSensorEventRequest.Builder()
                .total(2)
                .clusterSize(3)
                .type("Temperature").build();
        StepVerifier.create(testClass.processEventCluster(sensorEventRequest)).verifyComplete();

        verify(testClass, times(3)).processEvent(any(SensorEventRequest.class), anyInt());
    }

    @Test
    void testProduceEventsSingle() {
        testClass = spy(new SensorEventService(eventProducer) {
            @Override
            protected ParallelFlux<Void> processEventCluster(SensorEventRequest request) {
                return ParallelFlux.from(Flux.empty());
            }
        });

        SensorEventRequest sensorEventRequest = new ImmutableSensorEventRequest.Builder()
                .total(2)
                .type("Temperature").build();


        StepVerifier.create(testClass.produceEvents(List.of(sensorEventRequest))).verifyComplete();

        verify(testClass, times(1)).processEventCluster(any(SensorEventRequest.class));
    }

    @Test
    void testProduceEventsMultiple() {
        testClass = spy(new SensorEventService(eventProducer) {
            @Override
            protected ParallelFlux<Void> processEventCluster(SensorEventRequest request) {
                return ParallelFlux.from(Flux.empty());
            }
        });

        SensorEventRequest sensorEventRequest1 = new ImmutableSensorEventRequest.Builder()
                .total(2)
                .type("Temperature").build();


        SensorEventRequest sensorEventRequest2 = new ImmutableSensorEventRequest.Builder()
                .total(2)
                .type("cpu").build();


        StepVerifier.create(testClass.produceEvents(List.of(sensorEventRequest1, sensorEventRequest2))).verifyComplete();

        verify(testClass, times(2)).processEventCluster(any(SensorEventRequest.class));
    }
}