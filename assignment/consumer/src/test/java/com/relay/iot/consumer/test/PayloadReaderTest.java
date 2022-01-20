package com.relay.iot.consumer.test;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.relay.iot.consumer.simulator.app.model.event.EventModel;
import com.relay.iot.consumer.simulator.app.stream.consumer.JsonDecoderPayloadReader;
import com.relay.iot.consumer.simulator.app.stream.consumer.PayloadReader;
import com.relay.iot.consumer.test.integration.EventSub;

public class PayloadReaderTest {

	
	
	@Test
    void testJsonDecoder() {
        PayloadReader pr = new JsonDecoderPayloadReader();
        String payload = "ewogICAgICAgICJ0b3RhbCI6IDEyMCwKICAgICAgICAidHlwZSI6ICJURU1QRVJBVFVSRSIsCiAgICAgICAgImhlYXJ0QmVhdCI6IDUsCiAgICAgICAgImlkIjogMSwKICAgICAgICAibmFtZSI6ICJMaXZpbmcgUm9vbSBUZW1wIiwKICAgICAgICAiY2x1c3RlcklkIjogIjEiCiAgICB9";
        boolean support = pr.support("application/json", payload);
        Assert.isTrue(support);
        EventModel read = pr.read(payload);
        Assert.isTrue(read.getId().intValue() == 1);
        
    }
	
	
	@Test()
    void testJsonDecoderNotSupport() {
        PayloadReader pr = new JsonDecoderPayloadReader();
        String payload = "ewogICAgICAgICJ0b3RhbCI6IDEyMCwKICAgICAgICAidHlwZSI6ICJURU1QRVJBVFVSRSIsCiAgICAgICAgImhlYXJ0QmVhdCI6IDUsCiAgICAgICAgImlkIjogMSwKICAgICAgICAibmFtZSI6ICJMaXZpbmcgUm9vbSBUZW1wIiwKICAgICAgICAiY2x1c3RlcklkIjogIjEiCiAgICB9";
        boolean support = pr.support("application/json", new EventSub());
        Assert.isTrue(!support);
        
    }
	
	@Test
    void testPlainJsonDecoder() {
        PayloadReader pr = new JsonDecoderPayloadReader();
        String payload = "{\r\n"
        		+ "  \"total\": 120,\r\n"
        		+ "  \"type\": \"TEMPERATURE\",\r\n"
        		+ "  \"heartBeat\": 5,\r\n"
        		+ "  \"id\": 1,\r\n"
        		+ "  \"name\": \"Living Room Temp\",\r\n"
        		+ "  \"clusterId\": \"1\"\r\n"
        		+ "}";
        boolean support = pr.support("application/json", payload);
        Assert.isTrue(support);
        EventModel read = pr.read(payload);
        Assert.isTrue(read.getId().intValue() == 1);
    }
	
}
