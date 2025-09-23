package com.m30.saphira.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Allow access to all endpoints without authentication (temporarily).
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http ) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest( ).permitAll()
                )
                // CSRF Disable (Cross-Site Request Forgery).
                .csrf(csrf -> csrf.disable());

        return http.build( );
    }
}
