package com.ayushman.movie.service;

import com.ayushman.movie.dto.request.TicketRequest;
import com.ayushman.movie.entity.Ticket;
import com.ayushman.movie.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class BookingService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> viewTickets(){
        return null;
    }

    public Ticket bookTicket(TicketRequest ticketRequest){
        return null;
    }

    public void cancelTicket(@PathVariable Long id){

    }

}
