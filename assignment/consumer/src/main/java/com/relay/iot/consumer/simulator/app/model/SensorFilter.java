package com.relay.iot.consumer.simulator.app.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SensorFilter implements Serializable {
	private Operation operation;
	private Date fromDateTime;
	private Date toDateTime;
	private String eventType;
	private Long clusterId;

}