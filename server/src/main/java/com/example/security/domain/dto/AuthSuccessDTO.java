package com.example.security.domain.dto;

public class AuthSuccessDTO {

  private String token;

  private UserDTO user;

  public AuthSuccessDTO(String token, UserDTO user) {
    this.token = token;
    this.user = user;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public UserDTO getUser() {
    return user;
  }

  public void setUser(UserDTO user) {
    this.user = user;
  }
}
