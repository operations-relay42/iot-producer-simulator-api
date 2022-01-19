package com.relay.iot.consumer.simulator.app.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.reactivestreams.client.MongoClient;
import com.relay.iot.consumer.simulator.app.dao.EventCrudRepository;
import com.relay.iot.consumer.simulator.app.domain.EventEntity;

@Configuration
@EnableReactiveMongoRepositories(basePackageClasses = EventCrudRepository.class)
@EntityScan(basePackageClasses = EventEntity.class)
public class MongoReactiveApplication {

	@Bean
	public ReactiveMongoTemplate reactiveMongoTemplate(MongoClient mongoClient) {
		return new ReactiveMongoTemplate(mongoClient, "iotdb");
	}
	
	@Bean
	ApplicationRunner ar(ReactiveMongoTemplate rt)
	{
		return args -> {
			
		};
	}

}