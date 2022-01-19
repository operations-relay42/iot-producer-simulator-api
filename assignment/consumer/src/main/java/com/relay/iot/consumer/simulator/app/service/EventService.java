package com.relay.iot.consumer.simulator.app.service;

import java.util.Date;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.relay.iot.consumer.simulator.app.dao.EventCrudRepository;
import com.relay.iot.consumer.simulator.app.domain.EventEntity;
import com.relay.iot.consumer.simulator.app.model.event.Event;

import lombok.extern.slf4j.Slf4j;

/**
 * @author omidp
 *
 */
@Service
@Slf4j
public class EventService {

	EventCrudRepository eventDao;

	ReactiveMongoTemplate mongoOperation;
	

	public EventService(EventCrudRepository eventDao, ReactiveMongoTemplate mongoOperation) {
		this.eventDao = eventDao;
		this.mongoOperation = mongoOperation;
	}

	@Transactional
	public void save(Event event, MessageHeaders headers) {
		EventEntity ee = new EventEntity();
		ee.setName(event.getName());
		ee.setId(event.getId());
		ee.setClusterId(event.getClusterId());
		ee.setType(event.getType());
		ee.setValue(event.getValue());
		ee.setTimestamp(new Date(event.getTimestamp().toInstant().toEpochMilli()));
		//
		ee.setGroupId("" + headers.get("kafka_groupId"));
		ee.setTopic("" + headers.get("kafka_receivedTopic"));
		eventDao.save(ee).doOnSuccess((it) -> log.info("event saved {}", it.getUid())).subscribe();

	}

	

}
