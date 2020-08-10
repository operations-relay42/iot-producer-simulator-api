package br.com.iot.producer.simulator.api.config.security;

import br.com.iot.producer.simulator.api.config.filter.ContextPathFilter;
import br.com.iot.producer.simulator.api.exception.UnauthenticatedException;
import br.com.iot.producer.simulator.api.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

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
                .pathMatchers("/health").permitAll()
                .anyExchange().hasAuthority("SCOPE_admin")
                .and()
                .oauth2ResourceServer()
                .accessDeniedHandler((exchange, denied) -> Mono.error(new UnauthorizedException(denied)))
                .authenticationEntryPoint((exchange, e) -> Mono.error(new UnauthenticatedException(e)))
                .jwt()
                .and().and()
                .build();


    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder(@Value("${spring.security.oauth2.resourceserver.jwk.issuer-uri}") String issuerUri) {
        return ReactiveJwtDecoders.fromOidcIssuerLocation(issuerUri);
    }
}