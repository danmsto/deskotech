package com.example.security.resources;

import com.example.security.domain.dto.DeskDTO;
import com.example.security.service.DeskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/desks")
public class DeskResource {

  private final DeskService deskService;

  public DeskResource(DeskService deskService) {
    this.deskService = deskService;
  }

  @GetMapping
  public ResponseEntity<List<DeskDTO>> allDesks() {
    return new ResponseEntity<>(deskService.findAll(), HttpStatus.OK);
  }

  @GetMapping("/with-bookings")
  public ResponseEntity<List<DeskDTO>> getDesksWithBookingsForDate(Integer year, Integer month, Integer day) {
    List<DeskDTO> desks = deskService.getDesksWithBookingsForDate(LocalDate.of(year, month, day));
    return new ResponseEntity<>(desks, HttpStatus.OK);
  }
}
