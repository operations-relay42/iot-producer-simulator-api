package br.com.iot.producer.simulator.api.config.filter;

import br.com.iot.producer.simulator.api.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/** Webflux does not se the context path automatically, so we do that here for every request */
public class ContextPathFilter implements WebFilter {

    private static final Logger LOG = LoggerFactory.getLogger(ContextPathFilter.class);

    private final String context;

    public ContextPathFilter(String context) {
        this.context = context;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return Mono.fromCallable(() -> exchange)
                .filter(requestPath -> requestPath.getRequest().getURI().getPath().startsWith(context))
                .switchIfEmpty(Mono.error(NotFoundException::new))
                .flatMap(requestPath -> chain.filter(addContextPath(exchange)));
    }

    private ServerWebExchange addContextPath(ServerWebExchange exchange) {
        return exchange.mutate().request(exchange.getRequest().mutate().contextPath(context).build()).build();
    }
}
