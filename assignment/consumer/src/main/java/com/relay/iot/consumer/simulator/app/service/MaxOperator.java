package com.relay.iot.consumer.simulator.app.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.relay.iot.consumer.simulator.app.domain.EventEntity;
import com.relay.iot.consumer.simulator.app.model.Operation;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.math.MathFlux;

@Service
public class MaxOperator implements Operator{

	@Override
	public Operation getOperation() {
		return Operation.MAX;
	}

	@Override
	public Mono<BigDecimal>  calculate(Flux<EventEntity> events) {
		return MathFlux.max(events.map(EventEntity::getValue));
	}

}
