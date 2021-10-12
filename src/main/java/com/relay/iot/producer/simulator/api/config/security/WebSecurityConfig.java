package com.relay.iot.producer.simulator.api.config.security;

import com.relay.iot.producer.simulator.api.config.filter.ContextPathFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@Profile("!test")
public class WebSecurityConfig {

	private final ContextPathFilter contextPathFilter;

	public WebSecurityConfig(@Value("${spring.webflux.base-path}") final String contextPath) {
		this.contextPathFilter = new ContextPathFilter(contextPath);
	}

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		return http
			.addFilterAt(this.contextPathFilter, SecurityWebFiltersOrder.FIRST)
			.formLogin().disable()
			.csrf().disable()
			.httpBasic().disable()
			.logout().disable()
			.authorizeExchange()
			.anyExchange().permitAll()
			.and()
			.build();


	}
}