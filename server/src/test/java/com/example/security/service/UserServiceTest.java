package com.example.security.service;

import com.example.security.domain.User;
import com.example.security.domain.dto.UserDTO;
import com.example.security.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

  private static UserRepository mockedUserRepository;
  private static UserDTO mockedUserDTO;
  private static User mockedUser;
  private static PasswordEncoder mockedPasswordEncoder;
  private static TokenService mockedTokenService;
  private static AvatarService mockedAvatarService;
  private static JpaUserDetailsService mockedUserDetailsService;
  private static EmailService mockedEmailService;

  private static UserService classUnderTest;

  @BeforeEach
  void setup() {
    mockedUserRepository = mock(UserRepository.class);
    mockedUserDTO = mock(UserDTO.class);
    mockedUser = mock(User.class);
    mockedPasswordEncoder = mock(PasswordEncoder.class);
    mockedTokenService = mock(TokenService.class);
    mockedAvatarService = mock(AvatarService.class);
    mockedUserDetailsService = mock(JpaUserDetailsService.class);
    mockedEmailService = mock(EmailService.class);

    classUnderTest = new UserService(mockedUserRepository, mockedPasswordEncoder, mockedUserDetailsService, mockedTokenService, mockedAvatarService, mockedEmailService);
  }

  @Test
  void getAll() {
    List<User> userList = List.of(mockedUser);
    List<UserDTO> expected = List.of(mockedUserDTO);
    when(mockedUserRepository.findAll()).thenReturn(userList);
    when(mockedUser.toDto()).thenReturn(mockedUserDTO);
    List<UserDTO> actual = classUnderTest.getAll();
    assertEquals(expected, actual);
  }

  @Test
  void validateUserNoUsernameThrowsException() {
    User testUser = new User();
    testUser.setPassword("12345678");
    testUser.setEmail("test@user.com");
    testUser.setFirstName("test");
    testUser.setSurname("user");

    ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
      classUnderTest.validateNewUser(testUser);
    });
    assertEquals(UserService.MISSING_FIELDS_MESSAGE, e.getReason());
  }

  @Test
  void validateUserEmptyUsernameThrowsException() {
    User testUser = new User();
    testUser.setPassword("12345678");
    testUser.setEmail("test@user.com");
    testUser.setFirstName("test");
    testUser.setSurname("user");
    testUser.setUsername("");

    ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
      classUnderTest.validateNewUser(testUser);
    });
    assertEquals(UserService.MISSING_FIELDS_MESSAGE, e.getReason());
  }

  @Test
  void validateUserNoFirstnameThrowsException() {
    User testUser = new User();
    testUser.setPassword("12345678");
    testUser.setEmail("test@user.com");
    testUser.setSurname("user");
    testUser.setUsername("testUser");

    ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
      classUnderTest.validateNewUser(testUser);
    });
    assertEquals(UserService.MISSING_FIELDS_MESSAGE, e.getReason());
  }

  @Test
  void validateUserEmptyFirstnameThrowsException() {
    User testUser = new User();
    testUser.setPassword("12345678");
    testUser.setEmail("test@user.com");
    testUser.setSurname("user");
    testUser.setUsername("testUser");
    testUser.setFirstName("");
    ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
      classUnderTest.validateNewUser(testUser);
    });
    assertEquals(UserService.MISSING_FIELDS_MESSAGE, e.getReason());
  }

  @Test
  void validateUserNoSurnameThrowsException() {
    User testUser = new User();
    testUser.setPassword("12345678");
    testUser.setEmail("test@user.com");
    testUser.setUsername("testUser");
    testUser.setFirstName("test");
    ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
      classUnderTest.validateNewUser(testUser);
    });
    assertEquals(UserService.MISSING_FIELDS_MESSAGE, e.getReason());
  }

  @Test
  void validateUserEmptySurnameThrowsException() {
    User testUser = new User();
    testUser.setPassword("12345678");
    testUser.setEmail("test@user.com");
    testUser.setUsername("testUser");
    testUser.setFirstName("test");
    testUser.setSurname("");
    ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
      classUnderTest.validateNewUser(testUser);
    });
    assertEquals(UserService.MISSING_FIELDS_MESSAGE, e.getReason());
  }

  @Test
  void validateUserNoEmailThrowsException() {
    User testUser = new User();
    testUser.setPassword("12345678");
    testUser.setUsername("testUser");
    testUser.setFirstName("test");
    testUser.setSurname("user");
    ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
      classUnderTest.validateNewUser(testUser);
    });
    assertEquals(UserService.MISSING_FIELDS_MESSAGE, e.getReason());
  }

  @Test
  void validateUserEmptyEmailThrowsException() {
    User testUser = new User();
    testUser.setPassword("12345678");
    testUser.setUsername("testUser");
    testUser.setEmail("");
    testUser.setFirstName("test");
    testUser.setSurname("user");
    ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
      classUnderTest.validateNewUser(testUser);
    });
    assertEquals(UserService.MISSING_FIELDS_MESSAGE, e.getReason());
  }

  @Test
  void validateUserNoPasswordThrowsException() {
    User testUser = new User();
    testUser.setUsername("testUser");
    testUser.setEmail("test@user.com");
    testUser.setFirstName("test");
    testUser.setSurname("user");
    ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
      classUnderTest.validateNewUser(testUser);
    });
    assertEquals(UserService.MISSING_FIELDS_MESSAGE, e.getReason());
  }

  @Test
  void validateUserEmptyPasswordThrowsException() {
    User testUser = new User();
    testUser.setUsername("testUser");
    testUser.setEmail("test@user.com");
    testUser.setPassword("");
    testUser.setFirstName("test");
    testUser.setSurname("user");
    ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
      classUnderTest.validateNewUser(testUser);
    });
    assertEquals(UserService.MISSING_FIELDS_MESSAGE, e.getReason());
  }

  @Test
  void validateUserUsernameUnavailableThrowsException() {
    User testUser = new User();
    testUser.setUsername("testUser");
    testUser.setEmail("test@user.com");
    testUser.setPassword("12345678");
    testUser.setFirstName("test");
    testUser.setSurname("user");
    when(mockedUserRepository.existsUserByUsername(testUser.getUsername())).thenReturn(true);
    ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
      classUnderTest.validateNewUser(testUser);
    });
    assertEquals(UserService.USERNAME_UNAVAILABLE_MESSAGE, e.getReason());
  }

  @Test
  void validateUserPasswordTooShortThrowsException() {
    User testUser = new User();
    testUser.setUsername("testUser");
    testUser.setEmail("test@user.com");
    testUser.setPassword("1234567");
    testUser.setFirstName("test");
    testUser.setSurname("user");
    ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
      classUnderTest.validateNewUser(testUser);
    });
    assertEquals(UserService.PASSWORD_TOO_SHORT_MESSAGE, e.getReason());
  }

  @Test
  void validateUserEmailUnavailableThrowsException() {
    User testUser = new User();
    testUser.setUsername("testUser");
    testUser.setEmail("test@user.com");
    testUser.setPassword("12345678");
    testUser.setFirstName("test");
    testUser.setSurname("user");
    when(mockedUserRepository.existsUserByUsername(testUser.getUsername())).thenReturn(false);
    when(mockedUserRepository.existsByEmail(testUser.getEmail())).thenReturn(true);
    ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
      classUnderTest.validateNewUser(testUser);
    });
    assertEquals(UserService.EMAIL_UNAVAILABLE_MESSAGE, e.getReason());
  }

  @Test
  void validateUserIsValidDoesNotThrowException() {
    User testUser = new User();
    testUser.setUsername("testUser");
    testUser.setEmail("test@user.com");
    testUser.setPassword("12345678");
    testUser.setFirstName("test");
    testUser.setSurname("user");
    when(mockedUserRepository.existsUserByUsername(testUser.getUsername())).thenReturn(false);
    when(mockedUserRepository.existsByEmail(testUser.getEmail())).thenReturn(false);
    assertDoesNotThrow(() -> {
      classUnderTest.validateNewUser(testUser);
    });
  }

  @Test
  void verifyUser_CannotFindUserByUsername() {
    String mockedUsername = "Mocked Username";
    String mockedToken = "Mocked Token";
    String mockedVerificationId = "Mocked Verification Id";

    when(mockedTokenService.decodeToken(mockedToken)).thenReturn(mockedVerificationId);
    when(mockedUserRepository.findByVerificationId(mockedVerificationId)).thenReturn(Optional.of(mockedUser));

    assertThrows(ResponseStatusException.class, () -> {
      classUnderTest.verifyUser(mockedToken, mockedUsername);
    });
  }

  @Test
  void verifyUser_CannotFindUserByVerificationId() {
    String mockedUsername = "Mocked Username";
    String mockedToken = "Mocked Token";
    String mockedVerificationId = "Mocked Verification Id";

    when(mockedUserRepository.findUserByUsername(mockedUsername)).thenReturn(Optional.of(mockedUser));
    when(mockedTokenService.decodeToken(mockedToken)).thenReturn(mockedVerificationId);

    assertThrows(ResponseStatusException.class, () -> {
      classUnderTest.verifyUser(mockedToken, mockedUsername);
    });
  }

  @Test
  void verifyUser_FoundUsersAreNotTheSame() {
    String mockedUsername = "Mocked Username";
    String mockedToken = "Mocked Token";
    String mockedVerificationId = "Mocked Verification Id";
    User mockedOtherUser = mock(User.class);

    when(mockedUserRepository.findUserByUsername(mockedUsername)).thenReturn(Optional.of(mockedUser));
    when(mockedTokenService.decodeToken(mockedToken)).thenReturn(mockedVerificationId);
    when(mockedUserRepository.findByVerificationId(mockedVerificationId)).thenReturn(Optional.of(mockedOtherUser));

    assertThrows(ResponseStatusException.class, () -> {
      classUnderTest.verifyUser(mockedToken, mockedUsername);
    });
  }

  @Test
  void verifyUser_Correct() {
    String mockedUsername = "Mocked Username";
    String mockedToken = "Mocked Token";
    String mockedVerificationId = "Mocked Verification Id";

    when(mockedUserRepository.findUserByUsername(mockedUsername)).thenReturn(Optional.of(mockedUser));
    when(mockedTokenService.decodeToken(mockedToken)).thenReturn(mockedVerificationId);
    when(mockedUserRepository.findByVerificationId(mockedVerificationId)).thenReturn(Optional.of(mockedUser));
    when(mockedUserRepository.save(mockedUser)).thenReturn(mockedUser);

    assertDoesNotThrow(() -> {
      classUnderTest.verifyUser(mockedToken, mockedUsername);
    });
  }

  @Test
  void checkUserVerification_CannotFindUserByUsername() {
    String mockedUsername = "Mocked Username";

    when(classUnderTest.findByUsername(mockedUsername)).thenReturn(Optional.empty());

    assertThrows(ResponseStatusException.class, () -> {
      classUnderTest.checkUserVerification(mockedUsername);
    });
  }

  @Test
  void checkUserVerification_UserIsNotVerified() {
    String mockedUsername = "Mocked Username";

    when(mockedUserRepository.findUserByUsername(mockedUsername)).thenReturn(Optional.of(mockedUser));
    when(mockedUser.getVerified()).thenReturn(false);

    assertThrows(ResponseStatusException.class, () -> {
      classUnderTest.checkUserVerification(mockedUsername);
    });
  }
}