package br.com.aqueteron.boilerplate.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebMvcSecurityConfiguration {

    @Bean
    public DefaultSecurityFilterChain webHttpSecurity(final HttpSecurity http) throws Exception {
        return http.build();
    }

}
