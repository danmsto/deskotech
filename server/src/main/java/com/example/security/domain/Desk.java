package com.example.security.domain;

import com.example.security.domain.dto.DeskDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Desk {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private Integer deskNumber;

  public Desk() {
  }

  public Desk(Integer deskNumber) {
    this.deskNumber = deskNumber;
  }

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


  public DeskDTO toDto() {
    DeskDTO deskDTO = new DeskDTO();
    deskDTO.setId(this.id);
    deskDTO.setDeskNumber(this.deskNumber);
    return deskDTO;
  }
}
