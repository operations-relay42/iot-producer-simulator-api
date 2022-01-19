package com.relay.iot.consumer.simulator.app.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventResponse {

	private String uid;

	private Long id;

	private BigDecimal value;

	private Date timestamp;

	private String type;

	private String name;

	private Long clusterId;

	private long offset;

	private long partitionId;

	private String topic;

	private String groupId;
	


}
