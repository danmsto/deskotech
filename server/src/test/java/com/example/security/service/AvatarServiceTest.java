package com.example.security.service;

import com.example.security.repository.AvatarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class AvatarServiceTest {

  private static AvatarRepository mockedAvatarRepository;

  private static AvatarService classUnderTest;

  @BeforeEach
  void setup() {
    mockedAvatarRepository = mock(AvatarRepository.class);

    classUnderTest = new AvatarService(mockedAvatarRepository);
  }


  @Test
  void validateAvatarNullImageThrowsException() {
    MockMultipartFile testAvatarFile = new MockMultipartFile("avatarFile", "avatarFile", MediaType.IMAGE_PNG_VALUE, "".getBytes());
    ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
      classUnderTest.validateNewAvatar(testAvatarFile);
    });
  }

  @Test
  void validateAvatarNotAnImageThrowsException() {
    MockMultipartFile testAvatarFile = new MockMultipartFile("avatarFile", "avatarFile", MediaType.TEXT_PLAIN_VALUE, "Not an image".getBytes());
    ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
      classUnderTest.validateNewAvatar(testAvatarFile);
    });
  }

  @Test
  void validateAvatarGoodCall() throws IOException {
    File testFile = new File("src/main/resources/testImages/Subject.png");
    MockMultipartFile testAvatarFile = new MockMultipartFile("avatarFile", "avatarFile", MediaType.IMAGE_PNG_VALUE, Files.readAllBytes(testFile.toPath()));
    assertDoesNotThrow(() -> {
      classUnderTest.validateNewAvatar(testAvatarFile);
    });
  }
}
