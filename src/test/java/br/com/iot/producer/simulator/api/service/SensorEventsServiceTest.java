package br.com.iot.producer.simulator.api.service;

import br.com.iot.producer.simulator.api.controller.events.request.SensorEventRequest;
import br.com.iot.producer.simulator.api.model.EventType;
import br.com.iot.producer.simulator.api.model.event.SensorEvent;
import br.com.iot.producer.simulator.api.stream.producer.SensorEventProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SensorEventsServiceTest {

    private SensorEventsService testClass;
    private SensorEventProducer eventProducer;

    @BeforeEach
    void setUp() {
        eventProducer = mock(SensorEventProducer.class);
        testClass = new SensorEventsService(eventProducer);
        when(eventProducer.sendEvent(any(SensorEvent.class))).thenReturn(Mono.empty());
    }

    @Test
    void testSendEvent() {
        StepVerifier.create(testClass.sendEvent(new SensorEventRequest(10, EventType.TEMPERATURE, 10, 10L))).verifyComplete();
        verify(eventProducer, only()).sendEvent(any());
    }

    @Test
    void testProcessSingleEvent() {
        StepVerifier.create(testClass.processSingleEvent(new SensorEventRequest(2, EventType.TEMPERATURE, 10, 10L)))
                .verifyComplete();
    }
}