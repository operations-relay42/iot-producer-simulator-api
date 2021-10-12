package com.relay.iot.producer.simulator.api.controller.events;

import com.relay.iot.producer.simulator.api.controller.events.request.EventRequest;
import com.relay.iot.producer.simulator.api.service.EventService;
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
@RequestMapping("/events")
public class EventController {

    private static final Logger LOG = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> produceEvents(@Size(min = 1, max = 10, message = "{invalid.request.list.size}")
                                    @RequestBody List<@Valid EventRequest> request) {
        eventService.processAllEvents(request)
                .doOnSubscribe(subscription -> LOG.info("==== Received request -> {}", request))
                .subscribe(null,
                        throwable -> LOG.error("=== Failed to process request " + request, throwable),
                        () -> LOG.info("===== Process Ended for request -> {}", request));

        return Mono.empty();
    }
}
