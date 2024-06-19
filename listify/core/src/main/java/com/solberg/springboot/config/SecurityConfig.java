package com.solberg.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
            .requestMatchers("/", "/login**", "/error**", "/oauth2/**").permitAll()
            .anyRequest().authenticated())
        .oauth2Login(oauth2Login -> oauth2Login
            .loginPage("/login")
            .successHandler(oAuth2LoginSuccessHandler()));
    return http.build();
  }

  @Bean
  public AuthenticationSuccessHandler oAuth2LoginSuccessHandler() {
    return new SimpleUrlAuthenticationSuccessHandler() {
      @Override
      public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
          Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User user = oauthToken.getPrincipal();
        String token = user.getAttribute("accessToken"); // You might need to adjust this based on your setup

        // Debugging to see what attributes are available
        user.getAttributes().forEach((k, v) -> System.out.println(k + ": " + v));

        if (token == null) {
          // Fallback: Use the SecurityContextHolder to retrieve the token
          Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
          if (currentAuth != null && currentAuth.getCredentials() != null) {
            token = currentAuth.getCredentials().toString();
          }
        }

        // Redirect to React app with token as query parameter
        String redirectUrl = "http://localhost:3000/oauth2/redirect?token=" + token;
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
      }
    };
  }
}
