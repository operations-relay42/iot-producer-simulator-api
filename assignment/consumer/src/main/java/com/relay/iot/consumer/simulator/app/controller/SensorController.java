package com.relay.iot.consumer.simulator.app.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.relay.iot.consumer.simulator.app.model.Operation;
import com.relay.iot.consumer.simulator.app.model.SensorFilter;
import com.relay.iot.consumer.simulator.app.model.SensorResult;
import com.relay.iot.consumer.simulator.app.service.EventQueryService;
import com.relay.iot.consumer.simulator.util.DateUtil;

import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("/sensors")
public class SensorController {

	
	private final EventQueryService eventService;

    public SensorController(EventQueryService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{operation}")
    public Mono<SensorResult> get(@PathVariable("operation") String operation,
    		@RequestParam("eventType") Optional<String> eventType,
    		@RequestParam("clusterId") Optional<Long> clusterId,
    		@RequestParam("fromDate") String fromDate,
    		@RequestParam("toDate") String toDate) {
    	Operation op = Operation.findByValue(operation);
    	Date from = DateUtil.toDate(fromDate);
    	Date to = DateUtil.toDate(toDate);
    	SensorFilter sensorFilter = new SensorFilter(op, from, to, eventType.orElse(""), clusterId.orElse(null));
        Mono<SensorResult> result = eventService.querySensorData(sensorFilter);
        return result;
    }
    
    
    
}
