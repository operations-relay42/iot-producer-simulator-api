package com.relay.iot.consumer.simulator.app.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.relay.iot.consumer.simulator.app.domain.EventEntity;
import com.relay.iot.consumer.simulator.app.model.Operation;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.math.MathFlux;

@Service
@Slf4j
public class MinOperator implements Operator {

	@Override
	public Operation getOperation() {
		return Operation.MIN;
	}

	@Override
	public Mono<BigDecimal> calculate(Flux<EventEntity> events) {
		log.info("calculate min");
		return MathFlux.min(events.map(EventEntity::getValue));
	}


}
