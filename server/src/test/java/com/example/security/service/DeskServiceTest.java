package com.example.security.service;

import com.example.security.domain.Booking;
import com.example.security.domain.Desk;
import com.example.security.domain.dto.DeskDTO;
import com.example.security.repository.BookingRepository;
import com.example.security.repository.DeskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DeskServiceTest {
  private static DeskRepository mockedDeskRepository;
  private static BookingRepository mockedBookingRepository;
  private static DeskDTO mockedDeskDto;
  private static Desk mockedDesk;

  private static DeskService serviceUnderTest;

  @BeforeEach
  void setup() {
    mockedDeskRepository = mock(DeskRepository.class);
    mockedDesk = mock(Desk.class);
    mockedDeskDto = mock(DeskDTO.class);
    mockedBookingRepository = mock(BookingRepository.class);
    serviceUnderTest = new DeskService(mockedDeskRepository, mockedBookingRepository);
  }

  @Test
  void findAllNoDesks() {
    when(mockedDeskRepository.findAll()).thenReturn(Collections.emptyList());
    List<DeskDTO> expected = Collections.emptyList();
    List<DeskDTO> actual = serviceUnderTest.findAll();
    assertEquals(expected, actual);
  }

  @Test
  void findAllWithDesks() {
    List<Desk> testList = List.of(mockedDesk);
    List<DeskDTO> testListDto = List.of(mockedDeskDto);
    when(mockedDeskRepository.findAll()).thenReturn(testList);
    when(mockedDesk.toDto()).thenReturn(mockedDeskDto);
    List<DeskDTO> expected = testListDto;
    List<DeskDTO> actual = serviceUnderTest.findAll();
    assertEquals(expected, actual);
  }

  @Test
  void getDesksWithBookingForDateNoBookings() {
    List<Desk> testList = List.of(mockedDesk);
    when(mockedDeskRepository.findAll()).thenReturn(testList);
    when(mockedDesk.toDto()).thenReturn(mockedDeskDto);
    LocalDate testDate = LocalDate.now();
    when(mockedBookingRepository.findFirstByDateAndDesk(testDate, mockedDesk)).thenReturn(Optional.empty());
    List<DeskDTO> expected = List.of(mockedDeskDto);
    List<DeskDTO> actual = serviceUnderTest.getDesksWithBookingsForDate(testDate);
    assertEquals(expected, actual);
  }

  @Test
  void getDesksWithBookingForDateHasBooking() {
    List<Desk> testList = List.of(mockedDesk);
    when(mockedDeskRepository.findAll()).thenReturn(testList);
    when(mockedDesk.toDto()).thenReturn(mockedDeskDto);
    LocalDate testDate = LocalDate.now();
    Booking mockedBooking = mock(Booking.class);
    Optional<Booking> mockedOptional = Optional.of(mockedBooking);
    when(mockedBookingRepository.findFirstByDateAndDesk(testDate, mockedDesk)).thenReturn(Optional.of(mockedBooking));

    serviceUnderTest.getDesksWithBookingsForDate(testDate);

    verify(mockedDeskDto, times(1)).setBooking(mockedOptional.get().toDto());
  }
}