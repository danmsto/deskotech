package com.example.security.domain.dto;

public class DeskDTO {


  private Integer id;

  private Integer deskNumber;

  private BookingDTO booking;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getDeskNumber() {
    return deskNumber;
  }

  public void setDeskNumber(Integer deskNumber) {
    this.deskNumber = deskNumber;
  }

  public BookingDTO getBooking() {
    return booking;
  }

  public void setBooking(BookingDTO booking) {
    this.booking = booking;
  }
}
