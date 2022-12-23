package com.example.security.domain.dto;

public class BookingRequestDTO {

  private int year;
  private int month;
  private int day;

  private int deskId;

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getDay() {
    return day;
  }

  public void setDay(int day) {
    this.day = day;
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public int getDeskId() {
    return deskId;
  }

  public void setDeskId(int deskId) {
    this.deskId = deskId;
  }
}
