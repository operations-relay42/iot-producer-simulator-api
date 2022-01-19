package com.relay.iot.consumer.simulator.app.model.event;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventModel implements Event {

	private Long id;

	private BigDecimal value;

	private OffsetDateTime timestamp;

	private String type;

	private String name;

	private Long clusterId;

}