package br.com.iot.producer.simulator.api.service;

import br.com.iot.producer.simulator.api.model.event.SensorEvent;
import br.com.iot.producer.simulator.api.stream.producer.SensorEventProducer;
import org.junit.jupiter.api.BeforeEach;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SensorEventsServiceTest {

    private SensorEventsService testClass;
    private SensorEventProducer eventProducer;

    @BeforeEach
    void setUp() {
        eventProducer = mock(SensorEventProducer.class);
        testClass = new SensorEventsService(eventProducer);
        when(eventProducer.sendEvent(any(SensorEvent.class))).thenReturn(Mono.empty());
    }

//    @Test
//    void testSendEvent() {
//
//        StepVerifier.create(testClass.sendEvent(event)).verifyComplete();
//        verify(eventProducer, only()).sendEvent(any());
//    }
//
//    @Test
//    void testProcessSingleEvent() {
//        StepVerifier.create(testClass.processSingleEvent(new SensorEventRequest(2, "TEMPERATURE", 10, 10L, "name")))
//                .verifyComplete();
//    }
}