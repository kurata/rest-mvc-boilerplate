package br.com.aqueteron.boilerplate.components.utilities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
public class AuthenticationHelper {

    private AuthenticationHelper() {
        super();
    }

    public static Mono<UUID> retrieveAuthenticatedUserId() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .cast(Jwt.class)
                .map(jwt -> {
                    String userId = jwt.getClaim("userId");
                    log.debug("JWT user id {}", userId);
                    return userId;
                })
                .cast(String.class)
                .map(UUID::fromString);
    }
}
