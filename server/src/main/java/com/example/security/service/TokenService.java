package com.example.security.service;

import com.example.security.domain.SecurityUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class TokenService {

  private final JwtEncoder jwtEncoder;
  private final JwtDecoder jwtDecoder;
  private static final String VERIFICATION_KEY = "verificationId";

  public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
    this.jwtEncoder = jwtEncoder;
    this.jwtDecoder = jwtDecoder;
  }

  public String generateToken(Authentication authentication) {
    return genericTokenGenerator(authentication.getAuthorities(), authentication.getName());
  }

  public String generateToken(SecurityUser securityUser) {
    return genericTokenGenerator(securityUser.getAuthorities(), securityUser.getUsername());
  }

  private String genericTokenGenerator(Collection<? extends GrantedAuthority> authorities, String username) {
    Instant now = Instant.now();
    String scope = authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));
    JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plus(2, ChronoUnit.HOURS))
            .subject(username)
            .claim("scope", scope)
            .build();
    return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }

  public String generateEmailVerificationToken(String verificationId) {
    Instant now = Instant.now();
    JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plus(2, ChronoUnit.HOURS))
            .claim(VERIFICATION_KEY, verificationId)
            .build();
    return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }

  public String decodeToken (String token) {
    Jwt decodedToken = jwtDecoder.decode(token);
    if (decodedToken.getExpiresAt().isBefore(Instant.now())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token expired");
    }
    return decodedToken.getClaimAsString(VERIFICATION_KEY);
  }
}
