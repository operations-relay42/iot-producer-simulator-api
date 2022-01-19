package com.relay.iot.consumer.simulator.app.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.relay.iot.consumer.simulator.app.domain.EventEntity;
import com.relay.iot.consumer.simulator.app.model.Operation;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.math.CustomMonoAverageBigDecimal;

@Service
public class AverageOperator implements Operator {

	@Override
	public Operation getOperation() {
		return Operation.AVERAGE;
	}

	@Override
	public Mono<BigDecimal> calculate(Flux<EventEntity> events) {
		CustomMonoAverageBigDecimal<BigDecimal> monoAvgBigDecimal = new CustomMonoAverageBigDecimal<BigDecimal>(
				events.map(EventEntity::getValue), i -> i);
		return monoAvgBigDecimal;
	}

}
