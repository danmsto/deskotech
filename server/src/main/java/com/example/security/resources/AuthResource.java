package com.example.security.resources;

import com.example.security.domain.User;
import com.example.security.domain.dto.AuthSuccessDTO;
import com.example.security.domain.dto.ResultDTO;
import com.example.security.domain.dto.UserDTO;
import com.example.security.service.TokenService;
import com.example.security.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthResource {

  private final TokenService tokenService;
  private final UserService userService;

  public AuthResource(TokenService tokenService, UserService userService) {
    this.tokenService = tokenService;
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<AuthSuccessDTO> login(Authentication authentication) {
    String token = tokenService.generateToken(authentication);
    User user = userService.findByUsername(authentication.getName())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong"));
    AuthSuccessDTO authSuccess = new AuthSuccessDTO(token, user.toDto());
    return new ResponseEntity<>(authSuccess, HttpStatus.OK);
  }

  @PostMapping("/signup")
  public ResponseEntity<AuthSuccessDTO> signup(@RequestParam String username, @RequestParam String password, @RequestParam String firstName, @RequestParam String surname, @RequestParam String email, @RequestParam MultipartFile avatarFile) {
    AuthSuccessDTO authSuccess = userService.signup(username, password, firstName, surname, email, avatarFile);
    return new ResponseEntity<>(authSuccess, HttpStatus.OK);
  }

  @GetMapping("/verify/{verifyToken}")
  public ResponseEntity<UserDTO> verifyUser (@PathVariable("verifyToken") String verifyToken, JwtAuthenticationToken principal) {
    UserDTO verifySuccess = userService.verifyUser(verifyToken, principal.getName());
    return new ResponseEntity<>(verifySuccess, HttpStatus.OK);
  }

  @PostMapping("/verify/newtoken")
  public ResponseEntity<ResultDTO> resendToken(JwtAuthenticationToken principal) {
    String username = principal.getName();
    userService.resendEmailToken(username);
    return new ResponseEntity<>(new ResultDTO("New Token Sent"), HttpStatus.OK);
  }

}
