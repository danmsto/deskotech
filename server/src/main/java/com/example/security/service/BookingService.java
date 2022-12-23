package com.example.security.service;

import com.example.security.domain.Booking;
import com.example.security.domain.Desk;
import com.example.security.domain.User;
import com.example.security.domain.dto.AdminBookingRequestDTO;
import com.example.security.domain.dto.BookingDTO;
import com.example.security.domain.dto.BookingRequestDTO;
import com.example.security.repository.BookingRepository;
import com.example.security.repository.DeskRepository;
import com.example.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class BookingService {

  public static final String DESK_NOT_FOUND = "Desk not found";
  public static final String USER_NOT_FOUND = "User not found";
  public static final String DESK_UNAVAILABLE = "The desk has already been booked on the selected date";
  public static final String USER_HAS_BOOKING = "User already has a booking on the selected date";
  public static final String BOOKING_BELONGS_TO_OTHER_USER = "This is not your booking to delete";
  public static final String BOOKING_NOT_FOUND = "Booking not found";
  public static final String CANT_BOOK_IN_PAST = "Can't book in past";

  private final BookingRepository bookingRepository;
  private final DeskRepository deskRepository;
  private final UserRepository userRepository;

  public BookingService(BookingRepository bookingRepository, DeskRepository deskRepository, UserRepository userRepository) {
    this.bookingRepository = bookingRepository;
    this.deskRepository = deskRepository;
    this.userRepository = userRepository;
  }

  public List<BookingDTO> findByDate(LocalDate date) {
    List<Booking> bookingList = bookingRepository.findByDate(date);
    List<BookingDTO> bookingDTOList = bookingList.stream().map(Booking::toDto).toList();
    return bookingDTOList;
  }


  public Booking create(BookingRequestDTO bookingRequestDTO, String username) {
    Desk desk = deskRepository.findById(bookingRequestDTO.getDeskId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, DESK_NOT_FOUND));
    User user = userRepository.findUserByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));
    LocalDate date = LocalDate.of(bookingRequestDTO.getYear(), bookingRequestDTO.getMonth(), bookingRequestDTO.getDay());
    if (bookingRepository.existsByDeskAndDate(desk, date)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, DESK_UNAVAILABLE);
    }
    if (bookingRepository.existsByUserAndDate(user, date)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_HAS_BOOKING);
    }
    if (date.isBefore(LocalDate.now())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CANT_BOOK_IN_PAST);
    }
    Booking booking = new Booking();
    booking.setDesk(desk);
    booking.setDate(date);
    booking.setUser(user);
    return bookingRepository.save(booking);
  }

  public Booking adminCreate(AdminBookingRequestDTO adminBookingRequestDTO){
    Desk desk = deskRepository.findById(adminBookingRequestDTO.getDeskId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, DESK_UNAVAILABLE));
    User user = userRepository.findById(adminBookingRequestDTO.getUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));
    LocalDate date = LocalDate.of(adminBookingRequestDTO.getYear(), adminBookingRequestDTO.getMonth(), adminBookingRequestDTO.getDay());
    if (bookingRepository.existsByDeskAndDate(desk, date)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, DESK_UNAVAILABLE);
    }
    if (!user.isAdmin() && bookingRepository.existsByUserAndDate(user, date)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_HAS_BOOKING);
    }
    Booking booking = new Booking();
    booking.setDesk(desk);
    booking.setDate(date);
    booking.setUser(user);
    return bookingRepository.save(booking);
  }

  public void delete(Integer id, String username) {
    Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, BOOKING_NOT_FOUND));
    User user = userRepository.findUserByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));
    if (booking.getUser().getId() != user.getId()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BOOKING_BELONGS_TO_OTHER_USER);
    }
    bookingRepository.delete(booking);
  }

  public void adminDelete(Integer id) {
    Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, BOOKING_NOT_FOUND));
    bookingRepository.delete(booking);
  }

}
