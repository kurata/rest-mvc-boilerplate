package br.com.aqueteron.boilerplate.components.filters;

import br.com.aqueteron.boilerplate.components.ContextKeys;
import br.com.aqueteron.boilerplate.components.utilities.AuthenticationHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthenticatedUserFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        return webFilterChain.filter(serverWebExchange)
                .contextWrite(ctx -> ctx.put(ContextKeys.USER_ID, AuthenticationHelper.retrieveAuthenticatedUserId()));
    }
}
