package com.ayushman.movie.service;

import com.ayushman.movie.entity.Hall;
import com.ayushman.movie.entity.Show;
import com.ayushman.movie.entity.Theatre;
import com.ayushman.movie.repository.HallRepository;
import com.ayushman.movie.repository.TheatreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HallService {
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private TheatreRepository theatreRepository;

    public List<Hall> getAllHalls() {
        return hallRepository.findAll();
    }

    public Hall getHallById(long id) {
        return hallRepository.findById(id).orElseThrow();
    }

    public Hall updateHall(long id, Integer totalSeats) {
        Hall hall = hallRepository.findById(id).orElseThrow();
        hall.setTotalSeats(totalSeats);
        return hallRepository.save(hall);
    }

    public List<Hall> addHallsToTheatre(long theatreId, List<Integer> totalSeatsList) {
        Theatre theatre = theatreRepository.findById(theatreId).orElseThrow();
        List<Hall> halls = theatre.getHalls();
        List<Show> shows = new ArrayList<>();
        for (Integer totalSeats : totalSeatsList) {
            halls.add(addHallToTheatre(theatreId, totalSeats));
        }
        return halls;
    }

    public Hall addHallToTheatre(long theatreId, Integer totalSeats) {
        Theatre theatre = theatreRepository.findById(theatreId).orElseThrow();
        List<Hall> halls = theatre.getHalls();
        List<Show> shows = new ArrayList<>();
        Hall hall = hallRepository.save(
                Hall.builder()
                        .totalSeats(totalSeats)
                        .theatre(theatre)
                        .shows(shows)
                        .build()
        );
        halls.add(hall);
        theatre.setHalls(halls);
        theatreRepository.save(theatre);
        return hall;
    }

    public Hall deleteHallFromTheatre(long theatreId, long hallId) {
        Theatre theatre = theatreRepository.findById(theatreId).orElseThrow();
        List<Hall> halls = theatre.getHalls();
        Hall hall = hallRepository.findById(hallId).orElseThrow();
        if (!halls.contains(hall)) {
            throw new IllegalArgumentException("Hall does not belong to the specified theatre");
        }
        halls.remove(hall);
        theatre.setHalls(halls);
        theatreRepository.save(theatre);
        hallRepository.deleteById(hallId);
        return hall;
    }

    public boolean checkTimeAvailability(long hallId, LocalDateTime start, LocalDateTime end) {
        Hall hall = hallRepository.findById(hallId).orElseThrow();
        List<Show> shows = hall.getShows();
        for (Show show : shows) {
            if (show.getStart().isBefore(end) && show.getEnd().isAfter(start)) {
                return false;
            }
        }
        return true;
    }

}
