package com.relay.iot.consumer.test.integration;

import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.messaging.MessageHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.relay.iot.consumer.simulator.app.dao.EventCrudRepository;
import com.relay.iot.consumer.simulator.app.domain.EventEntity;
import com.relay.iot.consumer.simulator.app.model.Operation;
import com.relay.iot.consumer.simulator.app.model.SensorFilter;
import com.relay.iot.consumer.simulator.app.model.SensorResult;
import com.relay.iot.consumer.simulator.app.service.EventStoreService;
import com.relay.iot.consumer.test.integration.EventStoreServiceTest.TestContextConfig;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@Slf4j
@Import(TestContextConfig.class)
@Disabled
public class EventStoreServiceTest {

	private static final String CONNECTION_STRING = "mongodb://%s:%d";

	private MongodExecutable mongodExecutable;

	@Autowired
	private EventStoreService eventService;

	@BeforeEach
	void setup() throws Exception {
		String ip = "localhost";
		int port = 27017;

		ImmutableMongodConfig mongodConfig = MongodConfig.builder().version(Version.Main.PRODUCTION)
				.net(new Net(ip, port, Network.localhostIsIPv6())).build();

		MongodStarter starter = MongodStarter.getDefaultInstance();
		mongodExecutable = starter.prepare(mongodConfig);
		mongodExecutable.start();
	}

	@Test
	public void testEventService() {
		Assert.notNull(eventService);
		EventSub es = new EventSub();
		eventService.save(es, new MessageHeaders(new HashMap<>()));

	}

	@AfterEach
	void clean() {
		mongodExecutable.stop();
	}

	@TestConfiguration
	@EnableReactiveMongoRepositories(basePackageClasses = EventCrudRepository.class)
	@EntityScan(basePackageClasses = EventEntity.class)
	public static class TestContextConfig {
		@Bean
		public ReactiveMongoTemplate reactiveMongoTemplate(MongoClient mongoClient) {
			return new ReactiveMongoTemplate(mongoClient, "iotdb");
		}

		@Bean
		MongoClient mongoClient() {
			return MongoClients.create("mongodb://localhost:27017");
		}

		@Bean
		EventStoreService es(EventCrudRepository eventDao) {
			return new EventStoreService(eventDao);
		}

	}

}
