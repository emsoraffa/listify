package com.solberg.springboot;

import com.solberg.models.User;
import com.solberg.persistence.DataHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  @Autowired
  DataHandler datahandler;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest);

    // Extract user information
    String username = oauth2User.getAttribute("name");
    String email = oauth2User.getAttribute("email");

    // process user
    datahandler.registerUser(new User(username, email));

    return oauth2User;
  }

}
