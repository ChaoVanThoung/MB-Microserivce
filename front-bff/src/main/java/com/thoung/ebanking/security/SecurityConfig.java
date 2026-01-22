package com.thoung.ebanking.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;

import java.net.URI;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${app.frontend-gateway.url:http://localhost:3000}")
    private String frontendUrl;

    @Bean
    public SecurityWebFilterChain webSecurity(ServerHttpSecurity http) {

        http.authorizeExchange(exchanges -> exchanges
                .pathMatchers("/account/public/**",
                        "/",
                        "/_next/**",
                        "/favicon.ico",
                        "/images/**",
                        "/RTR-LOGO.png",
                        "/robots.txt"
                        ).permitAll()

        .anyExchange().authenticated());

        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        http.formLogin(ServerHttpSecurity.FormLoginSpec::disable);
        http.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);

        http.oauth2ResourceServer(auth2 ->  auth2
                .jwt(Customizer.withDefaults()));
        http.oauth2Login(auth2 -> auth2
                .authenticationSuccessHandler(
                new RedirectServerAuthenticationSuccessHandler(frontendUrl + "/report")
        ));

        RedirectServerLogoutSuccessHandler logoutSuccessHandler = new RedirectServerLogoutSuccessHandler();
        logoutSuccessHandler.setLogoutSuccessUrl(URI.create(frontendUrl + "/"));

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessHandler(
                        logoutSuccessHandler
                ));

        return http.build();
    }



}
