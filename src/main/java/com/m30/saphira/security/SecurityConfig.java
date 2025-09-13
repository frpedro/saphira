package com.m30.saphira.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Permite acesso a TODAS as rotas sem autenticação temporáriamente
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http ) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Permite acesso a TODAS as rotas sem autenticação
                        .anyRequest( ).permitAll()
                )
                // Desativa o CSRF (Cross-Site Request Forgery).
                .csrf(csrf -> csrf.disable());

        return http.build( );
    }
}
