package com.solberg.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SecurityConfig {

  private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
      OAuth2AuthorizedClientService authorizedClientService) throws Exception {
    http
        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
            .requestMatchers("/", "/login**", "/error**", "/oauth2/**").permitAll()
            .anyRequest().authenticated())
        .oauth2Login(oauth2Login -> oauth2Login
            .loginPage("/login")
            .successHandler(oAuth2LoginSuccessHandler()))
        .cors()
        .and()
        .csrf();
    return http.build();
  }

  @Bean
  public AuthenticationSuccessHandler oAuth2LoginSuccessHandler() {
    return new SimpleUrlAuthenticationSuccessHandler() {
      @Override
      public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
          Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        Object principal = oauthToken.getPrincipal();

        // Log the class type of principal for debugging
        logger.debug("Principal class: " + principal.getClass().getName());

        if (principal instanceof OidcUser) {
          OidcUser oidcUser = (OidcUser) principal;
          String idToken = oidcUser.getIdToken().getTokenValue();

          // Debugging to see what attributes are available
          oidcUser.getAttributes().forEach((k, v) -> logger.debug(k + ": " + v));

          // Log the token for debugging purposes
          logger.debug("ID Token: " + idToken);

          // Construct the redirect URL
          String redirectUrl = "http://localhost:3000/oauth2/redirect?token=" + idToken;
          getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        } else {
          // Log error or handle other types of principal if needed
          logger.error("Principal is not of type OidcUser. Principal: " + principal);
          throw new IllegalStateException("Principal is not of type OidcUser");
        }
      }
    };
  }
}
