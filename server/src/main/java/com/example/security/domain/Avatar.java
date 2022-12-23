package com.example.security.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "avatars")
public class Avatar {

  @Id
  @GeneratedValue
  private Integer id;

  @Lob
  @Column(columnDefinition = "MEDIUMBLOB")
  private byte[] imageData;

  public Avatar(Integer id, byte[] imageData) {
    this.id = id;
    this.imageData = imageData;
  }

  public Avatar() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public byte[] getImageData() {
    return imageData;
  }

  public void setImageData(byte[] imageData) {
    this.imageData = imageData;
  }
}
