package br.com.iot.producer.simulator.api.stream.producer;

import br.com.iot.producer.simulator.api.model.event.SensorEvent;
import br.com.iot.producer.simulator.api.model.event.SensorEventSub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import reactor.test.StepVerifier;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class SensorEventProducerTest {

    private SensorEventProducer testClass;
    private SensorDataOutput dataOutput;
    private MessageChannel messageChannel;

    @BeforeEach
    void setUp() {
        dataOutput = mock(SensorDataOutput.class);
        messageChannel = mock(MessageChannel.class);

        when(messageChannel.send(any())).thenReturn(true);
        when(dataOutput.output()).thenReturn(messageChannel);

        testClass = new SensorEventProducer(dataOutput);
    }

    @Test
    void testBuildMessage() {
        final Message<SensorEvent> actual = testClass.buildMessage(new SensorEventSub());

        assertEquals(12345L, actual.getHeaders().get(KafkaHeaders.MESSAGE_KEY));
    }

    @Test
    void testSendEvent() {


        StepVerifier.create(testClass.sendEvent(new SensorEventSub())).verifyComplete();
        verify(messageChannel, times(1)).send(any());
    }
}