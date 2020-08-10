package br.com.iot.producer.simulator.api.controller.events;

import br.com.iot.producer.simulator.api.controller.events.request.ClusterEventRequest;
import br.com.iot.producer.simulator.api.service.ClusterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Validated
@RestController
@RequestMapping("/clusters")
public class ClusterController {

    private static final Logger LOG = LoggerFactory.getLogger(ClusterController.class);

    private final ClusterService clusterService;

    public ClusterController(ClusterService clusterService) {
        this.clusterService = clusterService;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> produceClusterEvents(@Size(min = 1, max = 10, message = "{invalid.request.list.size}")
                                           @RequestBody List<@Valid ClusterEventRequest> request) {
        clusterService.processAllClusters(request)
                .doOnSubscribe(subscription -> LOG.info("==== Received request -> {}", request))
                .subscribe(null,
                        throwable -> LOG.error("=== Failed to process request " + request, throwable),
                        () -> LOG.info("===== Process Ended for request -> {}", request));

        return Mono.empty();
    }
}
