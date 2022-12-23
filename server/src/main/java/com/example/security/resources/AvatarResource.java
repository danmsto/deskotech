package com.example.security.resources;

import com.example.security.service.AvatarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/avatar")
public class AvatarResource {

  private final AvatarService avatarService;

  public AvatarResource(AvatarService avatarService) {
    this.avatarService = avatarService;
  }


  @GetMapping("/{id}")
  public ResponseEntity<byte[]> getAvatar(@PathVariable("id") Integer id) {
    byte[] imageData = avatarService.getAvatar(id);
    return new ResponseEntity<>(imageData, HttpStatus.OK);
  }

}
