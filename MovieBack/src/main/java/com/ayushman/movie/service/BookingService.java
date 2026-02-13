package com.ayushman.movie.service;

import com.ayushman.movie.dto.request.TicketRequest;
import com.ayushman.movie.entity.Role;
import com.ayushman.movie.entity.Show;
import com.ayushman.movie.entity.Ticket;
import com.ayushman.movie.entity.User;
import com.ayushman.movie.repository.ShowRepository;
import com.ayushman.movie.repository.TicketRepository;
import com.ayushman.movie.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final ShowService showService;
    private final ShowRepository showRepository;

    public List<Ticket> viewTickets(User user){
        if(user.getRole().equals(Role.ADMIN)){
            return ticketRepository.findAll();
        }
        return ticketRepository.findByUserId(user.getId());
    }

    public Ticket viewTicketById(Long id, User user){
        Ticket ticket = ticketRepository.findById(id).orElseThrow();
        if(!ticket.getUser().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)){
            throw new IllegalArgumentException("You are not authorized to view this ticket");
        }
        return ticket;
    }

    public Ticket bookTicket(TicketRequest ticketRequest, User user){
        Show show = showService.getShowById(ticketRequest.getShowId());
        long cost = show.getPrice() * ticketRequest.getSeatNumbers().size();
        for(Integer seatNum : ticketRequest.getSeatNumbers()) {
            boolean check = showService.checkSeatAvailability
                    (ticketRequest.getShowId(), ticketRequest.getSeatNumbers());
            if (!check) {
                throw new IllegalArgumentException("One or more seats are not available");
            }
            show.getAvailSeats().set(seatNum, 1);
        }
        Ticket ticket = ticketRepository.save(
                Ticket.builder()
                .movieName(ticketRequest.getMovieName())
                .seatNumbers(ticketRequest.getSeatNumbers())
                .cost(cost)
                .user(user)
                .show(show)
                .build()
        );
        List<Ticket> userTickets = user.getTickets();
        userTickets.add(ticket);
        user.setTickets(userTickets);
        userRepository.save(user);
        showRepository.save(show);
        return ticket;
    }

    public void cancelTicket(@PathVariable Long id, User user){
        Ticket ticket = ticketRepository.findById(id).orElseThrow();
        if(!ticket.getUser().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)){
            throw new IllegalArgumentException("You are not authorized to cancel this ticket");
        }
        Show show = ticket.getShow();
        for(Integer seatNum : ticket.getSeatNumbers()){
            show.getAvailSeats().set(seatNum, 0);
        }
        List<Ticket> userTickets = user.getTickets();
        userTickets.remove(ticket);
        user.setTickets(userTickets);
        userRepository.save(user);
        showRepository.save(show);
        ticketRepository.deleteById(id);
    }

}
