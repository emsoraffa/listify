package com.solberg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtIssuerValidator;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.security.oauth2.core.OAuth2Error;
import com.solberg.springboot.CustomOAuth2UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@Configuration
public class SecurityConfig {

  private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

  private final OAuth2AuthorizedClientService authorizedClientService;

  @Autowired
  private CustomOAuth2UserService customOAuth2UserService;

  public SecurityConfig(OAuth2AuthorizedClientService authorizedClientService) {
    this.authorizedClientService = authorizedClientService;
  }

  @PostConstruct
  public void init() {
    logger.debug("SecurityConfig initialized");
  }

  @Bean
  WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring()
        .requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation("https://accounts.google.com");

    jwtDecoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(
        new JwtTimestampValidator(),
        new JwtIssuerValidator("https://accounts.google.com"),
        token -> {
          // Add custom validation or logging here if needed
          logger.info("JWT Validation - Claims: {}", token.getClaims());
          return OAuth2TokenValidatorResult.success();
        }));

    return jwtDecoder;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .cors(cors -> cors
            .configurationSource(request -> {
              CorsConfiguration configuration = new CorsConfiguration();
              configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Adjust this as necessary
              configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
              configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
              return configuration;
            }))
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder())))
        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
            .requestMatchers("/", "/login**", "/error**", "/oauth2/**").permitAll()
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .requestMatchers("/api/**").authenticated()
            .anyRequest().authenticated())
        .oauth2Login(oauth2Login -> oauth2Login
            .defaultSuccessUrl("/oauth2/success", true)
            .failureHandler(oAuth2LoginFailureHandler())
            .successHandler(oAuth2LoginSuccessHandler())
            .userInfoEndpoint()
            .userService(customOAuth2UserService))
        .csrf().disable()
        .headers().frameOptions().sameOrigin()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
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
            // For non-OIDC users, handle the access token
            OAuth2AuthorizedClient authorizedClient = authorizedClientService
                .loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());
            if (authorizedClient != null) {
              String accessToken = authorizedClient.getAccessToken().getTokenValue();
              logger.debug("OAuth2 Access Token: " + accessToken);
              redirectUrl += "?token=" + accessToken;
            } else {
              throw new ServletException("Unable to extract access token for non-OIDC user.");
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
          AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof OAuth2AuthenticationException) {
          OAuth2AuthenticationException oauthException = (OAuth2AuthenticationException) exception;
          OAuth2Error error = oauthException.getError();
          // Simplify the log message to directly include the exception
          logger.error("OAuth2 Authentication Error: Code=" + error.getErrorCode() +
              ", Description=" + error.getDescription(), oauthException);
        } else {
          // For non-OAuth2 exceptions, just log the exception stack trace
          logger.error("Authentication failure: ", exception);
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " + exception.getMessage());
      }
    };
  }

}
