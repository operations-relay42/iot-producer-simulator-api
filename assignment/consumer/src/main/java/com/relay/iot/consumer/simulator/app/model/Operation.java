package com.relay.iot.consumer.simulator.app.model;

import java.util.stream.Stream;

public enum Operation {
	AVERAGE, MEDIAN, MAX, MIN;

	public static Operation findByValue(String val) {
		return Stream.of(Operation.values()).filter(f -> f.name().equalsIgnoreCase(val)).findFirst()
				.orElseThrow(() -> new RuntimeException("invalid operation"));
	}

}
