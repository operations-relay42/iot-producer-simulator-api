package com.relay.iot.consumer.simulator.app.config;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@Configuration
//@Profile("!test")
@EnableWebFluxSecurity
public class WebSecurityConfig {

	
static List<String> permitURI = new ArrayList<>();
	
	static {
		permitURI.add("/swagger-ui.html");
		permitURI.add("/configuration/ui");
		permitURI.add("/swagger-resources/**");
		permitURI.add("/configuration/security");
		permitURI.add("/webjars/*");
		permitURI.add("/webjars/**");
		permitURI.add("/*/api-docs");
		permitURI.add("/*/api-docs/**");
		permitURI.add("/login");
		permitURI.add("/ping");
	}
	
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
			.logout().disable()
			.authorizeExchange()
			.pathMatchers(HttpMethod.OPTIONS, "**").permitAll()
			.matchers(matches(permitURI.toArray(new String[permitURI.size()]))).permitAll()
			.matchers(matches("/**"))
			.authenticated().and().httpBasic().and()
			.build();


	}
	
	  @Bean
	    public MapReactiveUserDetailsService userDetailsService() {
	        UserDetails user = User.withDefaultPasswordEncoder()
	            .username("user")
	            .password("user")
	            .roles("USER")
	            .build();
	        return new MapReactiveUserDetailsService(user);
	    }
	
	private ServerWebExchangeMatcher matches(String ... routes) {
        return ServerWebExchangeMatchers.pathMatchers(routes);
    }

    private ServerWebExchangeMatcher notMatches(String ... routes) {
        return new NegatedServerWebExchangeMatcher(matches(routes));
    }
	
}