package br.com.iot.producer.simulator.api.service;

import br.com.iot.producer.simulator.api.mapper.EventMapper;
import br.com.iot.producer.simulator.api.model.event.Event;
import br.com.iot.producer.simulator.api.model.event.ModifiableRandomEvent;
import br.com.iot.producer.simulator.api.stream.producer.EventProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class EventServiceTest {

    private EventService testClass;
    private EventProducer eventProducer;

    @BeforeEach
    void setUp() {
        eventProducer = mock(EventProducer.class);
        testClass = new EventService(eventProducer, Mappers.getMapper(EventMapper.class));
        when(eventProducer.sendEvent(any(Event.class))).thenReturn(Mono.empty());
    }

    @Test
    void testSendEvent() {

        StepVerifier.create(testClass.sendEvent(ModifiableRandomEvent.create().setType("TEMPERATURE"))).verifyComplete();

        verify(eventProducer, only()).sendEvent(any());
    }

    @Test
    void testProcessEvent() {
        testClass = spy(new EventService(eventProducer, Mappers.getMapper(EventMapper.class)) {
            @Override
            protected Mono<Void> sendEvent(Event event) {
                return Mono.empty();
            }
        });

        StepVerifier.create(testClass.processEvent(ModifiableRandomEvent.create().setType("TEMPERATURE"), 2, 1)).verifyComplete();

        verify(testClass, times(2)).sendEvent(any(Event.class));
    }


}