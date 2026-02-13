package com.ayushman.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ayushman.movie.entity.Ticket;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

    List<Ticket> findByUserId(Long id);
}
