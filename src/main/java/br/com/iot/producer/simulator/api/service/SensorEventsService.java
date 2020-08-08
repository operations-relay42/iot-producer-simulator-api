package br.com.iot.producer.simulator.api.service;

import br.com.iot.producer.simulator.api.controller.events.request.SensorEventRequest;
import br.com.iot.producer.simulator.api.model.event.RandomSensorEvent;
import br.com.iot.producer.simulator.api.stream.producer.SensorEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SensorEventsService {

    private static final Logger LOG = LoggerFactory.getLogger(SensorEventsService.class);

    private final SensorEventProducer sensorEventProducer;

    public SensorEventsService(SensorEventProducer sensorEventProducer) {
        this.sensorEventProducer = sensorEventProducer;
    }

    public ParallelFlux<Void> produceEvents(List<SensorEventRequest> events) {
        return Flux.fromIterable(events)
                .doFirst(() -> LOG.debug("=== Starting event generator. -> {} ", LocalDateTime.now()))
                .parallel(events.size())
                .runOn(Schedulers.boundedElastic())
                .flatMap(this::processEventCluster);
    }

    private ParallelFlux<Void> processEventCluster(SensorEventRequest request) {
        return Flux.range(0, request.getClusterSize())
                .parallel(request.getClusterSize())
                .runOn(Schedulers.boundedElastic())
                .flatMap(integer -> processSingleEvent(request));
    }

    protected Flux<Void> processSingleEvent(SensorEventRequest request) {
        final RandomSensorEvent randomSensorEvent = new RandomSensorEvent(request.getType());

        return Flux.range(0, request.getTotal())
                .delayElements(Duration.ofSeconds(request.getHeartBeat()))
                .flatMap(integer -> sendEvent(randomSensorEvent));
    }

    protected Mono<Void> sendEvent(RandomSensorEvent randomSensorEvent) {
        return sensorEventProducer.sendEvent(randomSensorEvent);
    }
}
