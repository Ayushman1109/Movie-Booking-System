package com.ayushman.movie.service;

import com.ayushman.movie.dto.request.TheatreRequest;
import com.ayushman.movie.entity.Theatre;
import com.ayushman.movie.repository.TheatreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TheatreService {
    private final TheatreRepository theatreRepository;

    public Theatre createTheatre(TheatreRequest request) {
        Theatre theatre = Theatre.builder()
                .name(request.getName())
                .address(request.getAddress())
                .halls(new ArrayList<>())
                .build();
        return theatreRepository.save(theatre);
    }

    public List<Theatre> getAllTheatres() {
        return theatreRepository.findAll();
    }

    public Theatre updateTheatre(long id, TheatreRequest request) {
        Theatre theatre = theatreRepository.findById(id).orElseThrow();
        theatre.setName(request.getName());
        theatre.setAddress(request.getAddress());
        return theatreRepository.save(theatre);
    }

    public void deleteTheatre(long id) {
        theatreRepository.deleteById(id);
    }

    public Theatre getTheatreById(long id) {
        return theatreRepository.findById(id).orElseThrow();
    }
}
