package com.example.security.domain.dto;

import java.time.LocalDate;

public class BookingDTO {
  private Integer id;
  private LocalDate date;

  private Integer deskId;
  private UserDTO user;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public UserDTO getUser() {
    return user;
  }

  public void setUser(UserDTO user) {
    this.user = user;
  }

  public Integer getDeskId() {
    return deskId;
  }

  public void setDeskId(Integer deskId) {
    this.deskId = deskId;
  }
}
