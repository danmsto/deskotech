package com.example.security.service;

import com.example.security.domain.Avatar;
import com.example.security.repository.AvatarRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

import static com.example.security.utils.ImageUtil.compressImage;
import static com.example.security.utils.ImageUtil.decompressImage;

@Service
@Transactional
public class AvatarService {

  private final AvatarRepository avatarRepository;

  public AvatarService(AvatarRepository avatarRepository) {
    this.avatarRepository = avatarRepository;
  }


  public void validateNewAvatar(MultipartFile avatarFile) {
    InputStream stream;
    try {
      stream = avatarFile.getInputStream();
      if (ImageIO.read(stream) == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not an image.");
      }
    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No image found.");
    }
  }

  public Avatar saveAvatar(MultipartFile avatarFile) {
    validateNewAvatar(avatarFile);
    byte[] compressedImage;
    try {
      compressedImage = compressImage(avatarFile.getBytes());
    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image compression failed.");
    }
    Avatar avatar = new Avatar();
    avatar.setImageData(compressedImage);
    return avatarRepository.save(avatar);
  }

  public byte[] getAvatar(Integer id) {
    Avatar avatar = avatarRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avatar not found"));
    return decompressImage(avatar.getImageData());
  }

}
