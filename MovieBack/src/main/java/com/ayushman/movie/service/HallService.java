package com.ayushman.movie.service;

import com.ayushman.movie.dto.response.HallResponse;
import com.ayushman.movie.entity.Hall;
import com.ayushman.movie.entity.Show;
import com.ayushman.movie.entity.Theatre;
import com.ayushman.movie.mapper.DtoMapper;
import com.ayushman.movie.repository.HallRepository;
import com.ayushman.movie.repository.TheatreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HallService {
    private final HallRepository hallRepository;
    private final TheatreRepository theatreRepository;

    public List<HallResponse> getAllHalls() {
        return hallRepository.findAll().stream()
                .map(DtoMapper::toHallResponse)
                .collect(Collectors.toList());
    }

    public HallResponse getHallById(long id) {
        return DtoMapper.toHallResponse(hallRepository.findById(id).orElseThrow());
    }

    public HallResponse updateHall(long id, Integer totalSeats) {
        Hall hall = hallRepository.findById(id).orElseThrow();
        hall.setTotalSeats(totalSeats);
        return DtoMapper.toHallResponse(hallRepository.save(hall));
    }

    public List<HallResponse> addHallsToTheatre(long theatreId, List<Integer> totalSeatsList) {
        Theatre theatre = theatreRepository.findById(theatreId).orElseThrow();
        List<Hall> halls = theatre.getHalls();
        List<HallResponse> addedHalls = new ArrayList<>();
        List<Show> shows = new ArrayList<>();
        for (Integer totalSeats : totalSeatsList) {
            Hall hall = hallRepository.save(
                    Hall.builder()
                            .totalSeats(totalSeats)
                            .theatre(theatre)
                            .shows(shows)
                            .build()
            );
            halls.add(hall);
            addedHalls.add(DtoMapper.toHallResponse(hall));
        }
        theatre.setHalls(halls);
        theatreRepository.save(theatre);
        return addedHalls;
    }

    public HallResponse addHallToTheatre(long theatreId, Integer totalSeats) {
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
        return DtoMapper.toHallResponse(hall);
    }

    public HallResponse deleteHallFromTheatre(long theatreId, long hallId) {
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
        return DtoMapper.toHallResponse(hall);
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
