package com.example.bloggingapp.security;

import com.example.bloggingapp.security.auth_tokens.AuthTokenAuthenticationFilter;
import com.example.bloggingapp.security.jwt.JwtAuthenticationFilter;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthTokenAuthenticationFilter authTokenAuthenticationFilter;

    public AppSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                             AuthTokenAuthenticationFilter authTokenAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authTokenAuthenticationFilter = authTokenAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {
        return http.csrf().disable().cors().disable().authorizeHttpRequests(auth -> {
           auth.antMatchers(HttpMethod.GET, "/users").permitAll()
                   .antMatchers(HttpMethod.POST, "/users/login").permitAll()
                   .antMatchers(HttpMethod.POST, "/users").permitAll()
                   .antMatchers(HttpMethod.GET, "/articles").permitAll()
                   .anyRequest().authenticated();
        }).addFilterBefore(authTokenAuthenticationFilter, AnonymousAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, AuthTokenAuthenticationFilter.class).build();
    }
}
