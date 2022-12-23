package com.example.security.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TokenServiceTest {

  private static JwtEncoder mockedJwtEncoder;
  private static JwtDecoder mockedJwtDecoder;
  private static TokenService serviceUnderTest;

  @BeforeEach
  void setup() {
    mockedJwtEncoder = mock(JwtEncoder.class);
    mockedJwtDecoder = mock(JwtDecoder.class);
    serviceUnderTest = new TokenService(mockedJwtEncoder, mockedJwtDecoder);
  }

  @Test
  void decodeToken_expiredToken() {
    String mockedToken = "Expired Token";
    Jwt mockedDecodedToken = mock(Jwt.class);
    when(mockedJwtDecoder.decode(mockedToken)).thenReturn(mockedDecodedToken);
    when(mockedDecodedToken.getExpiresAt()).thenReturn(Instant.now().minus(1, ChronoUnit.HOURS));
    assertThrows(ResponseStatusException.class, () -> {
      serviceUnderTest.decodeToken(mockedToken);
    });
  }
}