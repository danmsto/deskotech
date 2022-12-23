package com.example.security.service;

import com.example.security.domain.Booking;
import com.example.security.domain.Desk;
import com.example.security.domain.dto.DeskDTO;
import com.example.security.repository.BookingRepository;
import com.example.security.repository.DeskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DeskService {

    private final DeskRepository deskRepository;
    private final BookingRepository bookingRepository;

    public DeskService(DeskRepository deskRepository, BookingRepository bookingRepository) {
        this.deskRepository = deskRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<DeskDTO> findAll() {
        List<Desk> allDesks = deskRepository.findAll();
        List<DeskDTO> deskDTOList = allDesks.stream().map(Desk::toDto).toList();
        return deskDTOList;
    }

    public List<DeskDTO> getDesksWithBookingsForDate(LocalDate date) {
        return deskRepository.findAll().stream().map(desk -> {
            DeskDTO deskDTO = desk.toDto();
            Optional<Booking> optionalBooking = bookingRepository.findFirstByDateAndDesk(date, desk);
            optionalBooking.ifPresent(booking -> deskDTO.setBooking(booking.toDto()));
            return deskDTO;
        }).toList();
    }
}
