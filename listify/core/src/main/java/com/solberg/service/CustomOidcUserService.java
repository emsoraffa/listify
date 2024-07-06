package com.solberg.service;

import com.solberg.models.User;
import com.solberg.persistence.DataHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class CustomOidcUserService extends OidcUserService {
  private static final Logger logger = LoggerFactory.getLogger(CustomOidcUserService.class);

  @Autowired
  private DataHandler dataHandler;

  @Override
  public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
    OidcUser oidcUser = super.loadUser(userRequest);

    // Extract user information
    //
    String username = oidcUser.getClaim("name");
    String email = oidcUser.getClaim("email");

    logger.debug("Processing OIDC user: " + username);

    // process user
    dataHandler.registerUser(new User(username, email));

    return oidcUser;
  }
}
