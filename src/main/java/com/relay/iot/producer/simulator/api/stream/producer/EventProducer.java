package com.relay.iot.producer.simulator.api.stream.producer;

import com.relay.iot.producer.simulator.api.model.event.Event;
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
@EnableBinding(EventDataOutput.class)
public class EventProducer {

    private static final Logger LOG = LoggerFactory.getLogger(EventProducer.class);

    private final MessageChannel messageChannel;

    public EventProducer(EventDataOutput dataOutput) {
        this.messageChannel = dataOutput.output();
    }

    public Mono<Void> sendEvent(Event event) {
        return Mono.fromSupplier(() -> buildMessage(event))
                .doFirst(() -> LOG.debug("==== Sending message {}", event))
                .map(messageChannel::send)
                .doOnSuccess(sent -> LOG.debug("==== Event sent? {}", sent))
                .then();
    }

    protected Message<Event> buildMessage(Event event) {
        return MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.MESSAGE_KEY, event.getId())
                .build();
    }
}
