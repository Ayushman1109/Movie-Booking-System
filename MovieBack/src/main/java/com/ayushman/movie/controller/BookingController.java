package com.ayushman.movie.controller;

import com.ayushman.movie.dto.request.TicketRequest;
import com.ayushman.movie.entity.Ticket;
import com.ayushman.movie.entity.User;
import com.ayushman.movie.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Ticket>> viewTickets(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(bookingService.viewTickets(user));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Ticket> viewTicketById(@PathVariable Long id, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(bookingService.viewTicketById(id, user));
    }

    @PostMapping("/book")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Ticket> bookTicket(@Valid @RequestBody TicketRequest ticketRequest,
                                             @AuthenticationPrincipal User user){
        return ResponseEntity.ok(bookingService.bookTicket(ticketRequest, user));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> cancelTicket(@PathVariable Long id, @AuthenticationPrincipal User user){
        bookingService.cancelTicket(id, user);
        return ResponseEntity.ok("Ticket cancelled successfully");
    }
}
