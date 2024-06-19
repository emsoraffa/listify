package com.solberg.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
            .requestMatchers("/", "/login**", "/error**").permitAll()
            .anyRequest().authenticated())
        .oauth2Login(oauth2Login -> oauth2Login
            .loginPage("/login"));
    return http.build();
  }
}
