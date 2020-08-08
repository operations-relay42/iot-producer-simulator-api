package br.com.iot.producer.simulator.api.stream.producer;

import br.com.iot.producer.simulator.api.model.event.SensorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@EnableBinding(SensorDataOutput.class)
public class SensorEventProducer {

    private static final Logger LOG = LoggerFactory.getLogger(SensorEventProducer.class);

    private final MessageChannel messageChannel;

    public SensorEventProducer(SensorDataOutput dataOutput) {
        this.messageChannel = dataOutput.output();
    }

    public Mono<Void> sendEvent(SensorEvent event) {
        return Mono.fromSupplier(() -> buildMessage(event))
                .doFirst(() -> LOG.debug("==== Sending message {}", event))
                .map(messageChannel::send)
                .doOnSuccess(sent -> LOG.debug("==== Event sent? {}", sent))
                .then();
    }

    protected Message<SensorEvent> buildMessage(SensorEvent event) {
        return MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.MESSAGE_KEY, event.getId())
                .build();
    }
}
