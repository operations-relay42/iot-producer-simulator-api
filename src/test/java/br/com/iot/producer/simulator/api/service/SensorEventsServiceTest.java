package br.com.iot.producer.simulator.api.service;

import br.com.iot.producer.simulator.api.controller.events.request.SensorEventRequest;
import br.com.iot.producer.simulator.api.mapper.SensorEventMapper;
import br.com.iot.producer.simulator.api.model.event.SensorEvent;
import br.com.iot.producer.simulator.api.stream.producer.SensorEventProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SensorEventsServiceTest {

    private SensorEventsService testClass;
    private SensorEventProducer eventProducer;

    @BeforeEach
    void setUp() {
        eventProducer = mock(SensorEventProducer.class);
        testClass = new SensorEventsService(eventProducer, Mappers.getMapper(SensorEventMapper.class));
        when(eventProducer.sendEvent(any(SensorEvent.class))).thenReturn(Mono.empty());
    }

    @Test
    void testSendEvent() {
        SensorEvent event = new SensorEvent();
        event.setId(10L);
        event.setName("name");
        event.setTimestamp(OffsetDateTime.now().toEpochSecond());
        event.setValue(BigDecimal.ONE);

        StepVerifier.create(testClass.sendEvent(event)).verifyComplete();
        verify(eventProducer, only()).sendEvent(any());
    }

    @Test
    void testProcessSingleEvent() {
        StepVerifier.create(testClass.processSingleEvent(new SensorEventRequest(2, "TEMPERATURE", 10, 10L, "name")))
                .verifyComplete();
    }
}