package br.com.iot.producer.simulator.api.stream.producer;

import br.com.iot.producer.simulator.api.model.EventType;
import br.com.iot.producer.simulator.api.model.event.SensorEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.stream.messaging.Source;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SensorEventProducerTest {

    private SensorEventProducer testClass;
    private Source source;

    @BeforeEach
    void setUp() {
        source = mock(Source.class, RETURNS_DEEP_STUBS);
        testClass = new SensorEventProducer(source);
        when(source.output().send(any())).thenReturn(true);
    }

    @Test
    void testDoSend() {
        SensorEvent sensorEvent = new SensorEvent();
        sensorEvent.setId(10L);
        sensorEvent.setTimestamp(LocalDateTime.of(2020, 10, 10, 12, 12).toEpochSecond(ZoneOffset.UTC));
        sensorEvent.setType(EventType.HEART_RATE.name());
        sensorEvent.setValue(BigDecimal.TEN);

        assertTrue(testClass.doSend(sensorEvent));

        verify(source.output(), only()).send(any());
    }

    @Test
    void testSendEvent() {

        SensorEvent sensorEvent = new SensorEvent();
        sensorEvent.setId(10L);
        sensorEvent.setTimestamp(LocalDateTime.of(2020, 10, 10, 12, 12).toEpochSecond(ZoneOffset.UTC));
        sensorEvent.setType(EventType.HEART_RATE.name());
        sensorEvent.setValue(BigDecimal.TEN);

        StepVerifier.create(testClass.sendEvent(sensorEvent)).verifyComplete();

        verify(source.output(), only()).send(any());

    }
}