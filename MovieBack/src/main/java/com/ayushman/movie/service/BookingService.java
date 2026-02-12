package com.ayushman.movie.service;

import com.ayushman.movie.dto.request.TicketRequest;
import com.ayushman.movie.entity.Show;
import com.ayushman.movie.entity.Ticket;
import com.ayushman.movie.entity.User;
import com.ayushman.movie.repository.ShowRepository;
import com.ayushman.movie.repository.TicketRepository;
import com.ayushman.movie.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@Transactional
public class BookingService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShowService showService;
    @Autowired
    private ShowRepository showRepository;

    public List<Ticket> viewTickets(){
        return null;
    }

    public Ticket bookTicket(TicketRequest ticketRequest){
        Show show = showService.viewShowById(ticketRequest.getShowId());
        User user = userRepository.findById(ticketRequest.getUserId()).orElseThrow();
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
        List<Long> userTickets = user.getTicketIds();
        userTickets.add(ticket.getId());
        user.setTicketIds(userTickets);
        userRepository.save(user);
        showRepository.save(show);
        return ticket;
    }

    public void cancelTicket(@PathVariable Long id){
        ticketRepository.deleteById(id);
    }

}
