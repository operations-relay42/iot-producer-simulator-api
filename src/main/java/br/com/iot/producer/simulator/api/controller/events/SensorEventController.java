package br.com.iot.producer.simulator.api.controller.events;

import br.com.iot.producer.simulator.api.controller.events.request.SensorEventRequest;
import br.com.iot.producer.simulator.api.service.SensorEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Validated
@RestController
@RequestMapping("/events")
public class SensorEventController {

    private static final Logger LOG = LoggerFactory.getLogger(SensorEventController.class);

    private final SensorEventService sensorEventService;

    public SensorEventController(SensorEventService sensorEventService) {
        this.sensorEventService = sensorEventService;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> produceEvents(@NotEmpty @RequestBody List<@Valid SensorEventRequest> request) {
        sensorEventService.produceEvents(request)
                .doOnSubscribe(subscription -> LOG.info("==== Received request -> {}", request))
                .subscribe(null,
                        throwable -> LOG.error("=== Failed to process request " + request, throwable),
                        () -> LOG.info("===== Process Ended for request -> {}", request));

        return Mono.empty();
    }
}
