package com.relay.iot.producer.simulator.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@EnableWebFlux
@Configuration
@Profile("!test")
public class WebFluxConfig implements WebFluxConfigurer {

    private final Jackson2JsonEncoder encoder;
    private final Jackson2JsonDecoder decoder;
    private final LocalValidatorFactoryBean localValidatorFactoryBean;

    public WebFluxConfig(Jackson2JsonEncoder encoder, Jackson2JsonDecoder decoder, LocalValidatorFactoryBean localValidatorFactoryBean) {
        this.encoder = encoder;
        this.decoder = decoder;
        this.localValidatorFactoryBean = localValidatorFactoryBean;
    }

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.defaultCodecs().jackson2JsonEncoder(encoder);
        configurer.defaultCodecs().jackson2JsonDecoder(decoder);
    }

    @Override
    public Validator getValidator() {
        return localValidatorFactoryBean;
    }
}
