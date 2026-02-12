package com.ayushman.movie.controller;

import com.ayushman.movie.dto.request.TicketRequest;
import com.ayushman.movie.entity.Ticket;
import com.ayushman.movie.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/tickets")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping()
    public List<Ticket> viewTickets(){
        return bookingService.viewTickets();
    }

    @GetMapping("/{id}")
    public Ticket viewTicketById(@PathVariable Long id){
        return bookingService.viewTicketById(id);
    }

    @PostMapping("/book")
    public Ticket bookTicket(@RequestBody TicketRequest ticketRequest){
        return bookingService.bookTicket(ticketRequest);
    }

    @DeleteMapping("/delete/{id}")
    public void cancelTicket(@PathVariable Long id){
        bookingService.cancelTicket(id);
    }
}
