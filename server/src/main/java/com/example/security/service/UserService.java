package com.example.security.service;

import com.example.security.domain.SecurityUser;
import com.example.security.domain.User;
import com.example.security.domain.dto.AuthSuccessDTO;
import com.example.security.domain.dto.UserDTO;
import com.example.security.domain.dto.UserRoles;
import com.example.security.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.security.utils.StringUtils.isNullOrEmpty;

@Service
@Transactional
public class UserService {

    public static final String MISSING_FIELDS_MESSAGE = "Missing username, password, firstName, surname or email";
    public static final String USERNAME_UNAVAILABLE_MESSAGE = "Username not available";
    public static final String PASSWORD_TOO_SHORT_MESSAGE = "Password must be at least 8 characters";
    public static final String EMAIL_UNAVAILABLE_MESSAGE = "Email not available";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JpaUserDetailsService userDetailsService;
    private final TokenService tokenService;
    private final AvatarService avatarService;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JpaUserDetailsService userDetailsService, TokenService tokenService, AvatarService avatarService, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
        this.avatarService = avatarService;
        this.emailService = emailService;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public void validateNewUser(User newUser) {
        if (isNullOrEmpty(newUser.getUsername()) ||
                isNullOrEmpty(newUser.getPassword()) ||
                isNullOrEmpty(newUser.getFirstName()) ||
                isNullOrEmpty(newUser.getEmail()) ||
                isNullOrEmpty(newUser.getSurname())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MISSING_FIELDS_MESSAGE);
        }
        if (newUser.getPassword().length() < 8) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PASSWORD_TOO_SHORT_MESSAGE);
        }
        if (userRepository.existsUserByUsername(newUser.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USERNAME_UNAVAILABLE_MESSAGE);
        }
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, EMAIL_UNAVAILABLE_MESSAGE);
        }
    }

    public UserDTO verifyUser (String token, String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        String verificationId = tokenService.decodeToken(token);
        User tokenUser = userRepository.findByVerificationId(verificationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        if (tokenUser == user) {
            user.setVerified(true);
            return userRepository.save(user).toDto();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect token, Resend code and try again");
        }
    }

    public void checkUserVerification(String username) {
        if (userRepository.findUserByUsername(username).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if (!userRepository.findUserByUsername(username).get().getVerified()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not verified");
        }
    }

    public AuthSuccessDTO signup(String username, String password, String firstName, String surname, String email, MultipartFile avatarFile){
        User mappedUser = new User();
        mappedUser.setUsername(username);
        mappedUser.setPassword(password);
        mappedUser.setFirstName(firstName);
        mappedUser.setSurname(surname);
        mappedUser.setEmail(email);
        validateNewUser(mappedUser);
        mappedUser.setAvatar(avatarService.saveAvatar(avatarFile));
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        mappedUser.setRoles(UserRoles.ROLE_USER.toString());
        mappedUser.setVerified(false);
        mappedUser.setVerificationId(String.valueOf(UUID.randomUUID()));
        User savedUser = userRepository.save(mappedUser);
        SecurityUser securityUser = (SecurityUser) userDetailsService.loadUserByUsername(savedUser.getUsername());
        String token = tokenService.generateToken(securityUser);
        emailService.sendVerificationEmail(savedUser.getEmail(), savedUser.getVerificationId());
        return new AuthSuccessDTO(token, securityUser.getUser().toDto());
    }

    public List<UserDTO> getAll() {
        List<User> userList = userRepository.findAll();
        List<UserDTO> userDTOList = userList.stream().map(User::toDto).toList();
        return userDTOList;
    }

    public void resendEmailToken(String username) {
        User foundUser = userRepository.findUserByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        foundUser.setVerificationId(String.valueOf(UUID.randomUUID()));
        User savedUser = userRepository.save(foundUser);
        emailService.sendVerificationEmail(savedUser.getEmail(), savedUser.getVerificationId());
    }
}
