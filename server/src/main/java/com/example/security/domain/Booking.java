package com.example.security.domain;

import com.example.security.domain.dto.BookingDTO;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private LocalDate date;

  @ManyToOne
  private User user;

  @ManyToOne
  private Desk desk;

  public Booking() {
  }

  public Booking(LocalDate date, User user, Desk desk) {
    this.date = date;
    this.user = user;
    this.desk = desk;
  }

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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Desk getDesk() {
    return desk;
  }

  public void setDesk(Desk desk) {
    this.desk = desk;
  }

  public BookingDTO toDto() {
    BookingDTO bookingDTO = new BookingDTO();
    bookingDTO.setId(this.id);
    bookingDTO.setDate(this.date);
    bookingDTO.setUser(this.user.toDto());
    bookingDTO.setDeskId(this.desk.getId());
    return bookingDTO;
  }
}
