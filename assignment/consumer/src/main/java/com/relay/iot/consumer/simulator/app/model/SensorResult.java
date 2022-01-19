package com.relay.iot.consumer.simulator.app.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SensorResult {

	private List<EventResponse> resultList;
	private int resultCount;
	private BigDecimal average;
	private BigDecimal min;
	private BigDecimal max;
	private BigDecimal median;

}
