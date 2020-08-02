package br.com.iot.producer.simulator.api.service;

import br.com.iot.producer.simulator.api.controller.events.request.SensorEventRequest;
import br.com.iot.producer.simulator.api.model.event.RandomSensorEvent;
import br.com.iot.producer.simulator.api.stream.producer.SensorEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SensorEventsService {

    private static final Logger LOG = LoggerFactory.getLogger(SensorEventsService.class);

    private final SensorEventProducer sensorEventProducer;

    public SensorEventsService(SensorEventProducer sensorEventProducer) {this.sensorEventProducer = sensorEventProducer;}

    public Mono<Void> produceEvents(List<SensorEventRequest> events) {
        Flux.fromIterable(events)
                .doFirst(() -> LOG.debug("=== Starting event generator. -> {} ", LocalDateTime.now()))
                .parallel(events.size())
                .runOn(Schedulers.boundedElastic())
                .flatMap(this::processSingleEvent)
                .subscribe(null,
                        throwable -> LOG.error("=== Could not process the events", throwable),
                        () -> LOG.debug("==== Process Finished -> {}", LocalDateTime.now()));

        return Mono.empty();
    }

    protected Mono<Void> processSingleEvent(SensorEventRequest request) {
        Flux.range(0, request.getTotal())
                .delayElements(Duration.ofSeconds(request.getEvery()))
                .parallel(5)
                .runOn(Schedulers.boundedElastic())
                .flatMap(integer -> sendEvent(request))
                .subscribe(null,
                        throwable -> LOG.error("=== Could not process the events", throwable),
                        () -> LOG.debug("==== Ended parallel process -> {}", request));

        return Mono.empty();
    }

    protected Mono<Void> sendEvent(SensorEventRequest request) {
        return sensorEventProducer.sendEvent(new RandomSensorEvent(request.getId(), request.getType()));
    }
}
