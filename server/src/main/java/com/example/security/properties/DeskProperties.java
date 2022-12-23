package com.example.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "desk")
public class DeskProperties {

  private int minDeskNumber;
  private int maxDeskNumber;

  public int getMinDeskNumber() {
    return minDeskNumber;
  }

  public void setMinDeskNumber(int minDeskNumber) {
    this.minDeskNumber = minDeskNumber;
  }

  public int getMaxDeskNumber() {
    return maxDeskNumber;
  }

  public void setMaxDeskNumber(int maxDeskNumber) {
    this.maxDeskNumber = maxDeskNumber;
  }
}
