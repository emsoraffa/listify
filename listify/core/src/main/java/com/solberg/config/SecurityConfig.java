package com.solberg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.solberg.springboot.CustomOAuth2UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Configuration
public class SecurityConfig {

  private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

  private final OAuth2AuthorizedClientService authorizedClientService;

  @Autowired
  private CustomOAuth2UserService customOAuth2UserService;

  public SecurityConfig(OAuth2AuthorizedClientService authorizedClientService) {
    this.authorizedClientService = authorizedClientService;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
            .requestMatchers("/", "/login**", "/error**", "/oauth2/**").permitAll()
            .anyRequest().authenticated())
        .oauth2Login(oauth2Login -> oauth2Login
            .loginPage("/login")
            .defaultSuccessUrl("/oauth2/success", true)
            .failureHandler(oAuth2LoginFailureHandler())
            .successHandler(oAuth2LoginSuccessHandler())
            .userInfoEndpoint()
            .userService(customOAuth2UserService))
        .cors()
        .and()
        .csrf().disable();
    return http.build();
  }

  @Bean
  public AuthenticationSuccessHandler oAuth2LoginSuccessHandler() {
    return new SimpleUrlAuthenticationSuccessHandler() {
      @Override
      public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
          Authentication authentication) throws IOException, ServletException {
        try {
          OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
          OAuth2User oauthUser = oauthToken.getPrincipal();

          String redirectUrl = "http://localhost:3000/oauth2/redirect";

          if (oauthUser instanceof OidcUser) {
            OidcUser oidcUser = (OidcUser) oauthUser;
            String idToken = oidcUser.getIdToken().getTokenValue();
            logger.debug("OIDC ID Token: " + idToken);
            redirectUrl += "?token=" + idToken;
          } else {
            OAuth2AuthorizedClient authorizedClient = authorizedClientService
                .loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());
            if (authorizedClient != null) {
              String accessToken = authorizedClient.getAccessToken().getTokenValue();
              logger.debug("OAuth2 Access Token: " + accessToken);
              redirectUrl += "?token=" + accessToken;
            }
          }

          oauthUser.getAttributes().forEach((k, v) -> logger.debug(k + ": " + v));
          getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        } catch (Exception e) {
          logger.error("Error during OAuth2 login success handling", e);
          throw e;
        }
      }
    };
  }

  @Bean
  public AuthenticationFailureHandler oAuth2LoginFailureHandler() {
    return new SimpleUrlAuthenticationFailureHandler() {
      @Override
      public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
          org.springframework.security.core.AuthenticationException exception)
          throws IOException, ServletException {
        logger.error("OAuth2 login failure", exception);
        super.onAuthenticationFailure(request, response, exception);
      }
    };
  }
}
