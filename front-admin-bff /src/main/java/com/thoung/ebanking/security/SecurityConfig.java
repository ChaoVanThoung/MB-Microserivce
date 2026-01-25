package com.thoung.ebanking.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain webSecurity(ServerHttpSecurity http) {

        http.authorizeExchange(exchanges -> exchanges
        .anyExchange().authenticated());

        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
//        http.formLogin(ServerHttpSecurity.FormLoginSpec::disable);
        http.logout(Customizer.withDefaults());
        http.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);

        http.oauth2ResourceServer(auth2 ->  auth2
                .jwt(Customizer.withDefaults()));
        http.oauth2Login(Customizer.withDefaults());

        return http.build();
    }

}
