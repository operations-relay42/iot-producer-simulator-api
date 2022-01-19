package com.relay.iot.consumer.simulator.app.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("/ping")
public class PingController {

	

    @GetMapping
    public Mono<String> get() {    	
        return Mono.just("pong");
    }
    
    
    
}
