package com.ayushman.movie.controller;

import com.ayushman.movie.dto.request.TicketRequest;
import com.ayushman.movie.entity.Ticket;
import com.ayushman.movie.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/tickets")
    public List<Ticket> viewTickets(){
        return bookingService.viewTickets();
    }

    @PostMapping("/book")
    public Ticket bookTicket(@RequestBody TicketRequest ticketRequest){
        return bookingService.bookTicket(ticketRequest);
    }

    @DeleteMapping("tickets/{id}")
    public void cancelTicket(@PathVariable Long id){
        bookingService.cancelTicket(id);
    }
}
