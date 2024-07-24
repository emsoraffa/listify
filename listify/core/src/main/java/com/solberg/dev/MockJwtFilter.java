package com.solberg.dev;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

@Component
public class MockJwtFilter extends GenericFilterBean {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    Jwt jwt = createJwt();
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        jwt, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(request, response);
  }

  private Jwt createJwt() {
    // Mock the JWT token here with necessary claims
    return Jwt.withTokenValue("fake-jwt-token-for-dev-mode")
        .header("alg", "none")
        .claim("sub", "1234567890")
        .claim("email", "dev@example.com")
        .build();
  }
}
