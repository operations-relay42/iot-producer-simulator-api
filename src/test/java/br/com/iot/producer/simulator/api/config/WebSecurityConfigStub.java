package br.com.iot.producer.simulator.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@Profile("test")
public class WebSecurityConfigStub {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .formLogin().disable()
                .csrf().disable()
                .httpBasic().disable()
                .logout().disable()
                .authorizeExchange()
                .anyExchange().permitAll()
                .and()
                .exceptionHandling()
                .and()
                .build();
    }

}