package com.example.security.domain;

import com.example.security.domain.dto.UserDTO;
import com.example.security.domain.dto.UserRoles;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue
  private Integer id;
  private String username;
  private String password;
  private String roles;
  private String firstName;
  private String surname;
  private String email;
  @OneToOne
  private Avatar avatar;
  private String verificationId;
  private Boolean isVerified;

  public User(String username, String password, String roles) {
    this.username = username;
    this.password = password;
    this.roles = roles;
  }

  public User() {
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRoles() {
    return roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }

  public boolean isAdmin() {
    return this.roles.contains(UserRoles.ROLE_ADMIN.toString());
  }

  public UserDTO toDto() {
    UserDTO userDTO = new UserDTO();
    userDTO.setId(this.id);
    userDTO.setUsername(this.username);
    userDTO.setFirstName(this.firstName);
    userDTO.setSurname(this.surname);
    userDTO.setEmail(this.email);
    userDTO.setAdmin(this.isAdmin());
    userDTO.setVerified(this.isVerified);
    if (this.avatar != null) {
      userDTO.setAvatarId(this.avatar.getId());
    }
    return userDTO;
  }

  public Avatar getAvatar() {
    return avatar;
  }

  public void setAvatar(Avatar avatar) {
    this.avatar = avatar;
  }

  public String getVerificationId() {
    return verificationId;
  }

  public void setVerificationId(String verificationId) {
    this.verificationId = verificationId;
  }

  public Boolean getVerified() {
    return isVerified;
  }

  public void setVerified(Boolean verified) {
    isVerified = verified;
  }
}
