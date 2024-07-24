package com.solberg.dev;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Profile("dev") // Apply this configuration only when 'dev' profile is active
// TODO: rename dev mode to somethign more intuitive
public class DevSecurityConfig {

  @Autowired
  private MockJwtFilter mockJwtFilter;

  @Bean
  public SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(authorize -> authorize
            .anyRequest().permitAll()) // Permit all requests without authentication
        .addFilterBefore(mockJwtFilter, UsernamePasswordAuthenticationFilter.class) // Add your custom filter
        .csrf().disable()
        .headers().frameOptions().disable();
    return http.build();
  }

  // Optionally, you can define a bean to return a mock OIDC user if needed
  @Bean
  public OAuth2UserService<OAuth2UserRequest, OidcUser> mockOidcUserService() {
    return request -> {
      // Mock OIDC ID Token details
      Map<String, Object> claims = new HashMap<>();
      claims.put("sub", "1234567890");
      claims.put("name", "Developer");
      claims.put("email", "dev@example.com");

      OidcIdToken idToken = new OidcIdToken("tokenValue", null, null, claims);
      OidcUserInfo userInfo = new OidcUserInfo(claims);

      return new DefaultOidcUser(
          Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
          idToken, userInfo, "email");
    };
  }
}
