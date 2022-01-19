package com.relay.iot.consumer.simulator.app.stream.consumer;

import java.util.Base64;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.relay.iot.consumer.simulator.app.model.event.EventModel;
import com.relay.iot.consumer.simulator.app.service.EventService;
import com.relay.iot.consumer.simulator.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author omidp
 *
 */
@Component
@EnableBinding(EventDataInput.class)
@Slf4j
public class EventConsumer {

	EventService eventService;

	public EventConsumer(EventService eventService) {
		this.eventService = eventService;
	}

	@StreamListener(EventDataInput.INPUT)
	public void process(Message<?> message) {
		MessageHeaders headers = message.getHeaders();
		Object payload = message.getPayload();
		log.info("Payload {}", payload);
		String body = null;
		if (payload instanceof String) {
			body = "" + payload;
			//let's handle both encoded string and json
			if (JsonUtil.isJson(body) == false) {
				// TODO: find out where it is encoding to base64
				body = decode(body);
			}
		}
		EventModel event = JsonUtil.toObject(body, EventModel.class);
		log.info("eventmodel {}", event.toString());
		eventService.save(event, headers);
	}

	public String decode(String payload) {
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String val = new String(decoder.decode(payload.replaceAll("\"", "")));
		return val;
	}
}
