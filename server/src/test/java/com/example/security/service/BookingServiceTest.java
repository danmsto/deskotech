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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BookingServiceTest {

  private static BookingRepository mockedBookRepository;
  private static DeskRepository mockedDeskRepository;
  private static BookingService serviceUnderTest;
  private static Booking mockedBooking;
  private static BookingDTO mockedBookingDto;
  private static BookingRequestDTO mockedBookingRequestDTO;
  private static LocalDate testFutureDate;
  private static LocalDate testPastDate;
  private static Desk mockedDesk;

  private static UserRepository mockedUserRepository;

  private static User mockedUser;

  @BeforeEach
  void setup() {
    mockedBookRepository = mock(BookingRepository.class);
    mockedBooking = mock(Booking.class);
    mockedBookingDto = mock(BookingDTO.class);
    mockedDeskRepository = mock(DeskRepository.class);
    testFutureDate = LocalDate.of(2040, 12, 12);
    testPastDate = LocalDate.of(2022,12,20);
    mockedBookingRequestDTO = mock(BookingRequestDTO.class);
    mockedUserRepository = mock(UserRepository.class);
    mockedDesk = mock(Desk.class);
    serviceUnderTest = new BookingService(mockedBookRepository, mockedDeskRepository, mockedUserRepository);
    mockedUser = mock(User.class);
  }

  @Test
  void findByDateNoBookings() {
    when(mockedBookRepository.findByDate(testFutureDate)).thenReturn(Collections.emptyList());

    List<BookingDTO> expected = Collections.emptyList();
    List<BookingDTO> actual = serviceUnderTest.findByDate(testFutureDate);
    assertEquals(expected, actual);
  }

  @Test
  void findByDateWithBookings() {
    List<Booking> bookingList = List.of(mockedBooking);
    List<BookingDTO> bookingDTOList = List.of(mockedBookingDto);
    when(mockedBookRepository.findByDate(testFutureDate)).thenReturn(bookingList);
    when(mockedBooking.toDto()).thenReturn(mockedBookingDto);
    List<BookingDTO> expected = bookingDTOList;
    List<BookingDTO> actual = serviceUnderTest.findByDate(testFutureDate);
    assertEquals(expected, actual);
  }

  @Test
  void createDeskIsAlreadyBookedOnDate() {
    int deskId = 666;
    String username = "kian";
    when(mockedBookingRequestDTO.getDeskId()).thenReturn(deskId);
    when(mockedDeskRepository.findById(deskId)).thenReturn(Optional.of(mockedDesk));
    when(mockedUserRepository.findUserByUsername(username)).thenReturn(Optional.of(mockedUser));
    when(mockedBookingRequestDTO.getYear()).thenReturn(testFutureDate.getYear());
    when(mockedBookingRequestDTO.getMonth()).thenReturn(testFutureDate.getMonthValue());
    when(mockedBookingRequestDTO.getDay()).thenReturn(testFutureDate.getDayOfMonth());
    when(mockedBookRepository.existsByDeskAndDate(mockedDesk, testFutureDate)).thenReturn(true);
    ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
      serviceUnderTest.create(mockedBookingRequestDTO, username);
    });
    assertEquals(BookingService.DESK_UNAVAILABLE, e.getReason());
  }


  @Test
  void createDeskIsNotAvailableOnPastDate() {
    int deskId = 666;
    String username = "kian";
    when(mockedBookingRequestDTO.getDeskId()).thenReturn(deskId);
    when(mockedDeskRepository.findById(deskId)).thenReturn(Optional.of(mockedDesk));
    when(mockedUserRepository.findUserByUsername(username)).thenReturn(Optional.of(mockedUser));
    when(mockedBookingRequestDTO.getYear()).thenReturn(testPastDate.getYear());
    when(mockedBookingRequestDTO.getMonth()).thenReturn(testPastDate.getMonthValue());
    when(mockedBookingRequestDTO.getDay()).thenReturn(testPastDate.getDayOfMonth());
    when(mockedBookRepository.existsByDeskAndDate(mockedDesk, testPastDate)).thenReturn(false);
    when(mockedBookRepository.existsByUserAndDate(mockedUser, testPastDate)).thenReturn(false);
    assertThrows(ResponseStatusException.class, () -> {
      serviceUnderTest.create(mockedBookingRequestDTO, username);
    });
  }

  @Test
  void createUserAlreadyHasBookingOnDate() {
    int deskId = 666;
    String username = "kian";
    when(mockedBookingRequestDTO.getDeskId()).thenReturn(deskId);
    when(mockedDeskRepository.findById(deskId)).thenReturn(Optional.of(mockedDesk));
    when(mockedUserRepository.findUserByUsername(username)).thenReturn(Optional.of(mockedUser));
    when(mockedBookingRequestDTO.getYear()).thenReturn(testFutureDate.getYear());
    when(mockedBookingRequestDTO.getMonth()).thenReturn(testFutureDate.getMonthValue());
    when(mockedBookingRequestDTO.getDay()).thenReturn(testFutureDate.getDayOfMonth());
    when(mockedBookRepository.existsByDeskAndDate(mockedDesk, testFutureDate)).thenReturn(false);
    when(mockedBookRepository.existsByUserAndDate(mockedUser, testFutureDate)).thenReturn(true);
    ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
      serviceUnderTest.create(mockedBookingRequestDTO, username);
    });
    assertEquals(BookingService.USER_HAS_BOOKING, e.getReason());
  }

  @Test
  void createDeskIsAvailableOnDate() {
    int deskId = 666;
    String username = "kian";
    when(mockedBookingRequestDTO.getDeskId()).thenReturn(deskId);
    when(mockedDeskRepository.findById(deskId)).thenReturn(Optional.of(mockedDesk));
    when(mockedUserRepository.findUserByUsername(username)).thenReturn(Optional.of(mockedUser));
    when(mockedBookingRequestDTO.getYear()).thenReturn(testFutureDate.getYear());
    when(mockedBookingRequestDTO.getMonth()).thenReturn(testFutureDate.getMonthValue());
    when(mockedBookingRequestDTO.getDay()).thenReturn(testFutureDate.getDayOfMonth());
    when(mockedBookRepository.existsByDeskAndDate(mockedDesk, testFutureDate)).thenReturn(false);
    when(mockedBookRepository.existsByUserAndDate(mockedUser, testFutureDate)).thenReturn(false);
    serviceUnderTest.create(mockedBookingRequestDTO, username);
    verify(mockedBookRepository, times(1)).save(any(Booking.class));
  }

  @Test
  void deleteBooking() {
    int bookingId = 888;
    String username = "Kian";
    when(mockedBookRepository.findById(bookingId)).thenReturn(Optional.of(mockedBooking));
    when(mockedUserRepository.findUserByUsername(username)).thenReturn(Optional.of(mockedUser));
    when(mockedBooking.getUser()).thenReturn(mockedUser);
    serviceUnderTest.delete(bookingId, username);
    verify(mockedBookRepository, times(1)).delete(mockedBooking);
  }

  @Test
  void deleteBookingNotUsers() {
    int bookingId = 888;
    int bookingUserId = 666;
    int loggedInUserId = 111;
    String username = "Kian";
    User mockedBookingUser = mock(User.class);
    when(mockedBookingUser.getId()).thenReturn(bookingUserId);
    when(mockedUser.getId()).thenReturn(loggedInUserId);
    when(mockedBookRepository.findById(bookingId)).thenReturn(Optional.of(mockedBooking));
    when(mockedBooking.getUser()).thenReturn(mockedBookingUser);
    when(mockedUserRepository.findUserByUsername(username)).thenReturn(Optional.of(mockedUser));
    assertThrows(ResponseStatusException.class, () -> {
      serviceUnderTest.delete(bookingId, username);
    });
  }

  @Test
  void adminCreateDeskIsAvailableOnDate() {
    int deskId = 666;
    int userId = 3;
    AdminBookingRequestDTO adminBookingRequestDTO = new AdminBookingRequestDTO();
    adminBookingRequestDTO.setDeskId(deskId);
    adminBookingRequestDTO.setYear(testFutureDate.getYear());
    adminBookingRequestDTO.setMonth(testFutureDate.getMonthValue());
    adminBookingRequestDTO.setDay(testFutureDate.getDayOfMonth());
    adminBookingRequestDTO.setUserId(userId);
    when(mockedDeskRepository.findById(deskId)).thenReturn(Optional.of(mockedDesk));
    when(mockedUserRepository.findById(userId)).thenReturn(Optional.of(mockedUser));
    when(mockedBookRepository.existsByDeskAndDate(mockedDesk, testFutureDate)).thenReturn(false);
    when(mockedBookRepository.existsByUserAndDate(mockedUser, testFutureDate)).thenReturn(false);
    serviceUnderTest.adminCreate(adminBookingRequestDTO);
    verify(mockedBookRepository, times(1)).save(any(Booking.class));
  }

  @Test
  void adminCreateDeskIsNotAvailableOnDate() {
    int deskId = 666;
    int userId = 3;
    AdminBookingRequestDTO adminBookingRequestDTO = new AdminBookingRequestDTO();
    adminBookingRequestDTO.setDeskId(deskId);
    adminBookingRequestDTO.setYear(testFutureDate.getYear());
    adminBookingRequestDTO.setMonth(testFutureDate.getMonthValue());
    adminBookingRequestDTO.setDay(testFutureDate.getDayOfMonth());
    adminBookingRequestDTO.setUserId(userId);
    when(mockedDeskRepository.findById(deskId)).thenReturn(Optional.of(mockedDesk));
    when(mockedUserRepository.findById(userId)).thenReturn(Optional.of(mockedUser));
    when(mockedBookRepository.existsByDeskAndDate(mockedDesk, testFutureDate)).thenReturn(true);
    assertThrows(ResponseStatusException.class, () -> {
      serviceUnderTest.adminCreate(adminBookingRequestDTO);
    });
  }

  @Test
  void adminUserAlreadyBookedOnDate() {
    int deskId = 2;
    int userId = 1;
    AdminBookingRequestDTO adminBookingRequestDTO = new AdminBookingRequestDTO();
    adminBookingRequestDTO.setDeskId(deskId);
    adminBookingRequestDTO.setYear(testFutureDate.getYear());
    adminBookingRequestDTO.setMonth(testFutureDate.getMonthValue());
    adminBookingRequestDTO.setDay(testFutureDate.getDayOfMonth());
    adminBookingRequestDTO.setUserId(userId);
    when(mockedDeskRepository.findById(deskId)).thenReturn(Optional.of(mockedDesk));
    when(mockedUserRepository.findById(userId)).thenReturn(Optional.of(mockedUser));
    when(mockedBookRepository.existsByDeskAndDate(mockedDesk, testFutureDate)).thenReturn(false);
    when(mockedBookRepository.existsByUserAndDate(mockedUser, testFutureDate)).thenReturn(true);
    assertThrows(ResponseStatusException.class, () -> {
      serviceUnderTest.adminCreate(adminBookingRequestDTO);
    });
  }


  @Test
  void adminDeleteBooking() {
    int bookingId = 888;
    when(mockedBookRepository.findById(bookingId)).thenReturn(Optional.of(mockedBooking));
    when(mockedBooking.getUser()).thenReturn(mockedUser);
    serviceUnderTest.adminDelete(bookingId);
    verify(mockedBookRepository, times(1)).delete(mockedBooking);
  }

}
