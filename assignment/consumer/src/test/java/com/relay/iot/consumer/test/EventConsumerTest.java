package com.relay.iot.consumer.test;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;

import com.relay.iot.consumer.simulator.app.exception.ConsumerExcpetion;
import com.relay.iot.consumer.simulator.app.service.EventStoreService;
import com.relay.iot.consumer.simulator.app.stream.consumer.EventConsumer;
import com.relay.iot.consumer.simulator.app.stream.consumer.JsonDecoderPayloadReader;
import com.relay.iot.consumer.test.integration.EventSub;

public class EventConsumerTest {

	private EventConsumer testClass;

	@BeforeEach
	void setUp() {
		EventStoreService eventServiceMock = mock(EventStoreService.class);
//
		doNothing().when(eventServiceMock).save(new EventSub(), new MessageHeaders(new HashMap<>()));
		testClass = new EventConsumer(eventServiceMock, Arrays.asList(new JsonDecoderPayloadReader()));
	}
	
	@Test
    void testprocessMessage() {
		String input = "ewogICAgICAgICJ0b3RhbCI6IDEyMCwKICAgICAgICAidHlwZSI6ICJURU1QRVJBVFVSRSIsCiAgICAgICAgImhlYXJ0QmVhdCI6IDUsCiAgICAgICAgImlkIjogMSwKICAgICAgICAibmFtZSI6ICJMaXZpbmcgUm9vbSBUZW1wIiwKICAgICAgICAiY2x1c3RlcklkIjogIjEiCiAgICB9";
        String contentType = null;
		Assertions.assertDoesNotThrow(()->{
			final Message<String> actual = new GenericMessage<>(input);
			testClass.process(actual);
		}, "no reader found for " + contentType);
    }
	
	@Test
    void testprocessMessageEx() {
		String input = "ewogICAgICAgICJ0b3RhbCI6IDEyMCwKICAgICAgICAidHlwZSI6ICJURU1QRVJBVFVSRSIsCiAgICAgICAgImhlYXJ0QmVhdCI6IDUsCiAgICAgICAgImlkIjogMSwKICAgICAgICAibmFtZSI6ICJMaXZpbmcgUm9vbSBUZW1wIiwKICAgICAgICAiY2x1c3RlcklkIjogIjEiCiAgICB9";
        String contentType = "xml";
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("contentType", contentType);
        Assertions.assertThrows(ConsumerExcpetion.class, ()->{
        	final Message<String> actual = new GenericMessage<>(input, headers);
			testClass.process(actual);
        }, "no reader found for " + contentType);
    }

}
