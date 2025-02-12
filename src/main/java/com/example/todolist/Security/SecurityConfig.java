package com.example.todolist.Security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Spring Security
    // Spring MVC (HTML(CSS,JS), Model)
    

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, JwtUtils jwtUtils) {
        JwtAuthenticationFilter jwtAuthenticationWebFilter = new JwtAuthenticationFilter(jwtUtils);
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .addFilterAt(jwtAuthenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/auth/**").permitAll()
                        .pathMatchers("/api/register").permitAll()
                        .pathMatchers("/api/user/profile/**").permitAll()
                        .pathMatchers("/api/news").permitAll()
                        .pathMatchers("/api/users").hasRole("ADMIN")
                        .pathMatchers("/ws/**").authenticated()
                        .pathMatchers("/home").authenticated()
                        .anyExchange().authenticated()
                )
                .build();
    }
}
