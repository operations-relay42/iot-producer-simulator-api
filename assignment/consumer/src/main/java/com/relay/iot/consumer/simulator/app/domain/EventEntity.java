package com.relay.iot.consumer.simulator.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.relay.iot.consumer.simulator.app.model.event.Event;

@Document(collection = "events")
public class EventEntity implements Serializable, Event {

	@Id
	private String uid;

	private Long id;

	private BigDecimal value;

	private Date timestampDate;

	@Indexed
	private String type;

	private String name;

	private Long clusterId;

	private long offset;

	private long partitionId;

	private String topic;

	private String groupId;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Date getTimestampDate() {
		return timestampDate;
	}

	public void setTimestampDate(Date timestampDate) {
		this.timestampDate = timestampDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getClusterId() {
		return clusterId;
	}

	public void setClusterId(Long clusterId) {
		this.clusterId = clusterId;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public long getPartitionId() {
		return partitionId;
	}

	public void setPartitionId(long partitionId) {
		this.partitionId = partitionId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Transient
	@Override
	public OffsetDateTime getTimestamp() {
		if(getTimestampDate() == null)
			return null;
		return getTimestampDate().toInstant()
				  .atOffset(ZoneOffset.UTC);
	}

}
