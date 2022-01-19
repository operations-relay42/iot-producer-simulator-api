package com.relay.iot.consumer.simulator.app.stream.consumer;

import java.util.Base64;
import java.util.List;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.relay.iot.consumer.simulator.app.model.event.EventModel;
import com.relay.iot.consumer.simulator.app.service.EventService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author omidp
 *
 */
@Component
@EnableBinding(EventDataInput.class)
@Slf4j
public class EventConsumer {

	private EventService eventService;
	private List<PayloadReader> readers;

	public EventConsumer(EventService eventService, List<PayloadReader> readers) {
		this.eventService = eventService;
		this.readers = readers;
	}

	@StreamListener(EventDataInput.INPUT)
	public void process(Message<?> message) {
		MessageHeaders headers = message.getHeaders();
		String contentType = ""+ headers.getOrDefault("contentType", "application/json");
		Object payload = message.getPayload();
		log.info("Payload {}", payload);
		readers.stream().filter(f->f.support(contentType, payload)).findFirst().ifPresent(c->{
			EventModel event = c.read(payload);
			log.info("eventmodel {}", event.toString());
			eventService.save(event, headers);
		});
	}

	
}
