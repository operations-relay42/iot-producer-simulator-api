package com.relay.iot.consumer.simulator.app.stream.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author omidp
 *
 */
public interface EventDataInput {

	/**
	 * Input channel name.
	 */
	String INPUT = "iot-data";

	/**
	 * @return input channel.
	 */
	@Input(EventDataInput.INPUT)
	SubscribableChannel input();

}