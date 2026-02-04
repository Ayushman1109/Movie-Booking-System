package com.ayushman.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ayushman.movie.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

}
