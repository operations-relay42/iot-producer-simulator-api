package br.com.iot.producer.simulator.api.stream.producer;

import br.com.iot.producer.simulator.api.model.event.SensorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@EnableBinding({Source.class})
public class SensorEventProducer {

    private static final Logger LOG = LoggerFactory.getLogger(SensorEventProducer.class);

    private final Source source;

    public SensorEventProducer(Source source) {this.source = source;}

    public Mono<Void> sendEvent(SensorEvent event) {
        return Mono.fromCallable(() -> doSend(event))
                .doFirst(() -> LOG.debug("==== Sending message {} ====", event))
                .doOnSuccess(sent -> LOG.debug("==== Event sent? {}", sent))
                .then();
    }

    protected boolean doSend(SensorEvent event) {
        return source.output().send(MessageBuilder.withPayload(event).setHeader(KafkaHeaders.MESSAGE_KEY, event.getId()).build());
    }
}
