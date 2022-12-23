package com.example.security.repository;

import com.example.security.domain.Booking;
import com.example.security.domain.Desk;
import com.example.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
  List<Booking> findByDate(LocalDate date);
  Optional<Booking> findFirstByDateAndDesk(LocalDate date, Desk desk);
  boolean existsByDeskAndDate(Desk desk, LocalDate date);
  boolean existsByUserAndDate(User user, LocalDate date);
}
