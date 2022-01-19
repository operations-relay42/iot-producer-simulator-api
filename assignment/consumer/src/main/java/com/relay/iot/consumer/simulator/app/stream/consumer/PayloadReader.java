package com.relay.iot.consumer.simulator.app.stream.consumer;

import com.relay.iot.consumer.simulator.app.model.event.EventModel;

/**
 * @author omidp
 *
 */
public interface PayloadReader {

	public boolean support(String contentType, Object payload);
	
	public EventModel read(Object payload);
	
}
