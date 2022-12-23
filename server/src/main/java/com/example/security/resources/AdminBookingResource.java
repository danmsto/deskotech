package com.example.security.resources;

import com.example.security.domain.Booking;
import com.example.security.domain.dto.AdminBookingRequestDTO;
import com.example.security.domain.dto.BookingDTO;
import com.example.security.domain.dto.ResultDTO;
import com.example.security.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/bookings")

public class AdminBookingResource {

    private final BookingService bookingService;

    public AdminBookingResource(BookingService bookingService){this.bookingService = bookingService;}

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody AdminBookingRequestDTO adminBookingRequestDTO){
        Booking booking = bookingService.adminCreate(adminBookingRequestDTO);
        return new ResponseEntity<>(booking.toDto(), HttpStatus.CREATED);
    }
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultDTO> adminDelete(@PathVariable("id") Integer bookingId){
        bookingService.adminDelete(bookingId);
        return new ResponseEntity<>(new ResultDTO("success"), HttpStatus.OK);
    }

}
