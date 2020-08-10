package br.com.iot.producer.simulator.api.service;

import br.com.iot.producer.simulator.api.controller.events.request.ClusterEventRequest;
import br.com.iot.producer.simulator.api.mapper.EventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClusterService {

    private static final Logger LOG = LoggerFactory.getLogger(ClusterService.class);

    private final EventService eventService;
    private final EventMapper eventMapper;

    public ClusterService(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    public ParallelFlux<Void> processAllClusters(List<ClusterEventRequest> events) {
        return Flux.fromIterable(events)
                .doFirst(() -> LOG.debug("=== Starting cluster event generator. -> {} ", LocalDateTime.now()))
                .parallel(events.size())
                .runOn(Schedulers.boundedElastic())
                .flatMap(this::processSingleEventCluster)
                .doOnComplete(() -> LOG.info("==== Cluster event generation ended {}", LocalDateTime.now()));
    }

    protected ParallelFlux<Void> processSingleEventCluster(ClusterEventRequest request) {
        return Flux.range(0, request.getClusterSize())
                .doFirst(() -> LOG.debug("==== Going to process event cluster -> {}", request))
                .parallel(request.getClusterSize())
                .runOn(Schedulers.boundedElastic())
                .map(processStep -> eventMapper.fromRequest(request))
                .flatMap(randomSensorEvent -> eventService.processEvent(randomSensorEvent, request.getTotal(), request.getHeartBeat()))
                .doOnComplete(() -> LOG.debug("==== Ended event process for cluster -> {}", request));
    }

}
