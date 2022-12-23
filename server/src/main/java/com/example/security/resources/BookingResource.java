package com.example.security.resources;

import com.example.security.domain.Booking;
import com.example.security.domain.dto.BookingDTO;
import com.example.security.domain.dto.BookingRequestDTO;
import com.example.security.domain.dto.ResultDTO;
import com.example.security.service.BookingService;
import com.example.security.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingResource {

  private final BookingService bookingService;
  private final UserService userService;


  public BookingResource(BookingService bookingService, UserService userService) {
    this.bookingService = bookingService;
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<BookingDTO>> bookingsByDate(Integer year, Integer month, Integer day) {
    LocalDate date = LocalDate.of(year, month, day);
    return new ResponseEntity<>(bookingService.findByDate(date), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingRequestDTO bookingRequestDTO, JwtAuthenticationToken principal) {
    userService.checkUserVerification(principal.getName());
    Booking booking = bookingService.create(bookingRequestDTO, principal.getName());
    return new ResponseEntity<>(booking.toDto(), HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResultDTO> deleteBooking(JwtAuthenticationToken principal, @PathVariable("id") Integer bookingId) {
    String loggedInUsername = principal.getName();
    bookingService.delete(bookingId, loggedInUsername);
    return new ResponseEntity<>(new ResultDTO("success"), HttpStatus.OK);
  }
}