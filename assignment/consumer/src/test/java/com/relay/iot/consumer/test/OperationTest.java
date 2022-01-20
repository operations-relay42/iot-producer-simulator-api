package com.relay.iot.consumer.test;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.relay.iot.consumer.simulator.app.service.AverageOperator;
import com.relay.iot.consumer.simulator.app.service.MaxOperator;
import com.relay.iot.consumer.simulator.app.service.MedianOperator;
import com.relay.iot.consumer.simulator.app.service.MinOperator;
import com.relay.iot.consumer.test.integration.EventSub;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class OperationTest {

	@Test
	void testMaxOperation() {
		
		MaxOperator mo = new MaxOperator();
		EventSub eventSub = new EventSub() {
			@Override
			public BigDecimal getValue() {
				return new BigDecimal(200);
			}
		};
		StepVerifier
			.create(mo.calculate(Flux.fromStream(Stream.of(eventSub, new EventSub(), new EventSub()))))
			.expectNext(new BigDecimal(200))
			.verifyComplete();
	}
	
	@Test
	void testMinOperation() {
		
		MinOperator mo = new MinOperator();
		EventSub eventSub = new EventSub() {
			@Override
			public BigDecimal getValue() {
				return BigDecimal.ZERO;
			}
		};
		StepVerifier
			.create(mo.calculate(Flux.fromStream(Stream.of(eventSub, new EventSub(), new EventSub()))))
			.expectNext(BigDecimal.ZERO)
			.verifyComplete();
	}
	
	@Test
	void testAvgOperation() {
		
		var mo = new AverageOperator();
		
		StepVerifier
			.create(mo.calculate(Flux.fromStream(Stream.of(new EventSub(), new EventSub(), new EventSub()))))
			.expectNext(BigDecimal.TEN)
			.verifyComplete();
	}
	
	@Test
	void testMedianOperation() {
		
		var mo = new MedianOperator();
		EventSub eventSub1 = new EventSub() {
			@Override
			public BigDecimal getValue() {
				return BigDecimal.ONE;
			}
		};
		EventSub eventSub2 = new EventSub() {
			@Override
			public BigDecimal getValue() {
				return BigDecimal.ZERO;
			}
		};
		EventSub eventSub3 = new EventSub() {
			@Override
			public BigDecimal getValue() {
				return BigDecimal.TEN;
			}
		};
		StepVerifier
			.create(mo.calculate(Flux.fromStream(Stream.of(eventSub1, eventSub2, eventSub3))))
			.expectNext(BigDecimal.ONE)
			.verifyComplete();
	}
	
	@Test
	void testMedianOddOperation() {
		
		var mo = new MedianOperator();
		EventSub eventSub1 = new EventSub() {
			@Override
			public BigDecimal getValue() {
				return BigDecimal.ONE;
			}
		};
		EventSub eventSub2 = new EventSub() {
			@Override
			public BigDecimal getValue() {
				return BigDecimal.ZERO;
			}
		};
		EventSub eventSub3 = new EventSub() {
			@Override
			public BigDecimal getValue() {
				return BigDecimal.TEN;
			}
		};
		EventSub eventSub4 = new EventSub() {
			@Override
			public BigDecimal getValue() {
				return new BigDecimal(5);
			}
		};
		StepVerifier
			.create(mo.calculate(Flux.fromStream(Stream.of(eventSub1, eventSub2, eventSub3, eventSub4))))
			.expectNext(new BigDecimal(5.5))
			.verifyComplete();
	}

}
