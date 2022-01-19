package com.relay.iot.consumer.simulator.app.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.relay.iot.consumer.simulator.app.domain.EventEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface EventCrudRepository 
  extends ReactiveCrudRepository<EventEntity, String> {
 
}