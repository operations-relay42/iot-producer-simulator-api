package com.relay.iot.producer.simulator.api.mapper;

import com.relay.iot.producer.simulator.api.controller.events.request.ClusterEventRequest;
import com.relay.iot.producer.simulator.api.controller.events.request.EventRequest;
import com.relay.iot.producer.simulator.api.model.event.ModifiableRandomEvent;
import com.relay.iot.producer.simulator.api.model.event.RandomEvent;
import com.relay.iot.producer.simulator.api.utils.RandomUtils;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {

    default RandomEvent fromRequest(ClusterEventRequest request, Integer processStep) {
        return ModifiableRandomEvent.create()
                .setType(request.getType())
                .setClusterId(request.getClusterId())
                .setName(String.format("%s_%s", request.getName(), processStep))
                .setId(RandomUtils.randomInt(request.getClusterSize()));
    }

    default RandomEvent fromRequest(EventRequest request) {
        return ModifiableRandomEvent.create()
                .setType(request.getType())
                .setName(request.getName())
                .setId(request.getId())
                .setClusterId(request.getClusterId());
    }

}
