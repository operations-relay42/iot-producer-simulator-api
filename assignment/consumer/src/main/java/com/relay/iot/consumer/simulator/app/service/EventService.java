package com.relay.iot.consumer.simulator.app.service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.relay.iot.consumer.simulator.app.dao.EventCrudRepository;
import com.relay.iot.consumer.simulator.app.domain.EventEntity;
import com.relay.iot.consumer.simulator.app.model.EventResponse;
import com.relay.iot.consumer.simulator.app.model.SensorFilter;
import com.relay.iot.consumer.simulator.app.model.SensorResult;
import com.relay.iot.consumer.simulator.app.model.event.Event;
import com.relay.iot.consumer.simulator.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

/**
 * @author omidp
 *
 */
@Service
@Slf4j
public class EventService {

	EventCrudRepository eventDao;

	ReactiveMongoTemplate mongoOperation;
	
	OperatorServiceFactory operatorService;
	

	public EventService(EventCrudRepository eventDao, ReactiveMongoTemplate mongoOperation, OperatorServiceFactory operatorService) {
		this.eventDao = eventDao;
		this.mongoOperation = mongoOperation;
		this.operatorService = operatorService;
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

	@Transactional
	public Mono<SensorResult> querySensorData(SensorFilter filter) {
		Query query = new Query();
		query.addCriteria(Criteria.where("timestamp").gte(filter.getFromDateTime())
				.andOperator(Criteria.where("timestamp").lte(filter.getToDateTime())))

		;

		if (StringUtil.isNotEmpty(filter.getEventType()))
			query.addCriteria(Criteria.where("type").is(filter.getEventType()));
		if (filter.getClusterId() != null)
			query.addCriteria(Criteria.where("clusterId").is(filter.getClusterId()));
		Flux<EventEntity> events = mongoOperation.find(query, EventEntity.class);
		//
		
		Mono<BigDecimal> calculate = operatorService.getInstance(filter.getOperation()).calculate(events);
		
		//
		 
		return events.map(this::toEventResponse).collectList().flatMap(m -> {
			SensorResult sensorResult = new SensorResult();
			sensorResult.setResultList(m);
			sensorResult.setResultCount(m.size());
			Mono<SensorResult> just = Mono.just(sensorResult);
			Mono<Tuple2<SensorResult, BigDecimal>> zipWith = just.zipWith(calculate);
			return zipWith.map(t -> {
				List<EventResponse> resultList = t.getT1().getResultList();
				SensorResult sr = new SensorResult();
				//TODO: it might not be a good idea to put resultlist in the response, improve this response 
//				sr.setResultList(resultList);
				sr.setResultCount(resultList.size());
				switch (filter.getOperation()) {
				case AVERAGE:
					sr.setAverage(t.getT2());
					break;
				case MIN:
					sr.setMin(t.getT2());
					break;
				case MAX:
					sr.setMax(t.getT2());
					break;
				case MEDIAN:
					sr.setMedian(t.getT2());
					break;
				default:
					break;
				}
				return sr;
			});
		});
	}
	
	private EventResponse toEventResponse(EventEntity ee) {
		EventResponse er = new EventResponse();
		er.setType(ee.getType());
		er.setName(ee.getName());
		er.setId(ee.getId());
		er.setClusterId(ee.getClusterId());
		er.setGroupId(ee.getGroupId());
		er.setTimestamp(ee.getTimestamp());
		er.setTopic(ee.getTopic());
		ee.setValue(ee.getValue());

		return er;
	}

	

}
