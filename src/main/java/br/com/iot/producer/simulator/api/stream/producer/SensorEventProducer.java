package br.com.iot.producer.simulator.api.stream.producer;

import br.com.iot.producer.simulator.api.model.event.SensorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Component
public class SensorEventProducer {

    private static final Logger LOG = LoggerFactory.getLogger(SensorEventProducer.class);

    private final KafkaTemplate<Long, SensorEvent> kafkaTemplate;

    public SensorEventProducer(KafkaTemplate<Long, SensorEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public Mono<Void> sendEvent(SensorEvent event) {
        return Mono.fromFuture(doSend(event))
                .doFirst(() -> LOG.debug("==== Sending message {} ====", event))
                .doOnSuccess(sent -> LOG.debug("==== Event sent? {}", sent.getRecordMetadata().offset()))
                .then();
    }

    protected CompletableFuture<SendResult<Long, SensorEvent>> doSend(SensorEvent event) {
        return kafkaTemplate.send("iot-data", event.getId(), event).completable();
    }
}
