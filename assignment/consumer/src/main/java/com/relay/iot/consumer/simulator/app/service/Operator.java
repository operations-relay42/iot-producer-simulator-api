package com.relay.iot.consumer.simulator.app.service;

import java.math.BigDecimal;

import com.relay.iot.consumer.simulator.app.model.Operation;
import com.relay.iot.consumer.simulator.app.model.event.Event;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Operator {

	public Operation getOperation();

	public Mono<BigDecimal> calculate(Flux<Event> events);
	
	
}
