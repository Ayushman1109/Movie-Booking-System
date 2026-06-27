package com.ayushman.movie.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.ayushman.movie.dto.request.TicketRequest;
import com.ayushman.movie.dto.response.TicketResponse;
import com.ayushman.movie.entity.Role;
import com.ayushman.movie.entity.Show;
import com.ayushman.movie.entity.Ticket;
import com.ayushman.movie.entity.User;
import com.ayushman.movie.exception.InvalidRequestException;
import com.ayushman.movie.exception.ResourceNotFoundException;
import com.ayushman.movie.exception.UnauthorizedActionException;
import com.ayushman.movie.mapper.DtoMapper;
import com.ayushman.movie.repository.ShowRepository;
import com.ayushman.movie.repository.TicketRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingService {

    private final TicketRepository ticketRepository;
    private final ShowService showService;
    private final ShowRepository showRepository;

    public List<TicketResponse> viewTickets(User user){
        if(user.getRole().equals(Role.ADMIN)){
            return ticketRepository.findAll().stream()
                    .map(DtoMapper::toTicketResponse)
                    .collect(Collectors.toList());
        }
        return ticketRepository.findByUserId(user.getId()).stream()
                .map(DtoMapper::toTicketResponse)
                .collect(Collectors.toList());
    }

    public TicketResponse viewTicketById(Long id, User user){
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));
        if(!ticket.getUser().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)){
            throw new UnauthorizedActionException("You are not authorized to view this ticket");
        }
        return DtoMapper.toTicketResponse(ticket);
    }

    public TicketResponse bookTicket(TicketRequest ticketRequest, User user){
        Show show = showRepository.findById(ticketRequest.getShowId())
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: " + ticketRequest.getShowId()));
        String movieName = show.getMovie().getName();
        long cost = show.getPrice() * ticketRequest.getSeatNumbers().size();
        boolean check = showService.checkSeatAvailability(
                ticketRequest.getShowId(), ticketRequest.getSeatNumbers());
        if (!check) {
            throw new InvalidRequestException("One or more seats are not available");
        }
        for(Integer seatNum : ticketRequest.getSeatNumbers()) {
            show.getAvailSeats().set(seatNum, 1);
        }
        Ticket ticket = ticketRepository.save(
                Ticket.builder()
                .movieName(movieName)
                .seatNumbers(ticketRequest.getSeatNumbers())
                .cost(cost)
                .user(user)
                .show(show)
                .build()
        );
        showRepository.save(show);
        return DtoMapper.toTicketResponse(ticket);
    }

    public void cancelTicket(Long id, User user){
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));
        if(!ticket.getUser().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)){
            throw new UnauthorizedActionException("You are not authorized to cancel this ticket");
        }
        Show show = ticket.getShow();
        for(Integer seatNum : ticket.getSeatNumbers()){
            show.getAvailSeats().set(seatNum, 0);
        }
        showRepository.save(show);
        ticketRepository.deleteById(id);
    }

}
