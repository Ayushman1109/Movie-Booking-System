package com.ayushman.movie.service;

import com.ayushman.movie.dto.request.TheatreRequest;
import com.ayushman.movie.dto.response.TheatreResponse;
import com.ayushman.movie.entity.Theatre;
import com.ayushman.movie.exception.ResourceNotFoundException;
import com.ayushman.movie.mapper.DtoMapper;
import com.ayushman.movie.repository.TheatreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TheatreService {
    private final TheatreRepository theatreRepository;

    public TheatreResponse createTheatre(TheatreRequest request) {
        Theatre theatre = Theatre.builder()
                .name(request.getName())
                .address(request.getAddress())
                .halls(new ArrayList<>())
                .build();
        return DtoMapper.toTheatreResponse(theatreRepository.save(theatre));
    }

    public List<TheatreResponse> getAllTheatres() {
        return theatreRepository.findAll().stream()
                .map(DtoMapper::toTheatreResponse)
                .collect(Collectors.toList());
    }

    public TheatreResponse updateTheatre(long id, TheatreRequest request) {
        Theatre theatre = theatreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theatre not found with id: " + id));
        theatre.setName(request.getName());
        theatre.setAddress(request.getAddress());
        return DtoMapper.toTheatreResponse(theatreRepository.save(theatre));
    }

    public void deleteTheatre(long id) {
        theatreRepository.deleteById(id);
    }

    public TheatreResponse getTheatreById(long id) {
        return DtoMapper.toTheatreResponse(theatreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theatre not found with id: " + id)));
    }
}
