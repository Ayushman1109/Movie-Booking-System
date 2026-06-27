package com.ayushman.movie.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.ayushman.movie.dto.request.MovieRequest;
import com.ayushman.movie.dto.response.MovieResponse;
import com.ayushman.movie.entity.Movie;
import com.ayushman.movie.exception.ResourceNotFoundException;
import com.ayushman.movie.mapper.DtoMapper;
import com.ayushman.movie.repository.MovieRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<MovieResponse> getAllMovies(){
        return movieRepository.findAll().stream()
                .map(DtoMapper::toMovieResponse)
                .collect(Collectors.toList());
    }

    public MovieResponse getMovieById(Long id){ 
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
        return DtoMapper.toMovieResponse(movie);
    }

    public MovieResponse createNewMovie(MovieRequest movieRequest){
        Movie movie = Movie.builder()
                .name(movieRequest.getName())
                .language(movieRequest.getLanguage())
                .durationInMinutes(movieRequest.getDurationInMinutes())
                .rating(movieRequest.getRating())
                .posterUrl(movieRequest.getPosterUrl())
                .build();
        return DtoMapper.toMovieResponse(movieRepository.save(movie));
    }

    public MovieResponse updateMovie(Long id, MovieRequest movieRequest){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
        movie.setName(movieRequest.getName());
        movie.setLanguage(movieRequest.getLanguage());
        movie.setDurationInMinutes(movieRequest.getDurationInMinutes());
        movie.setRating(movieRequest.getRating());
        movie.setPosterUrl(movieRequest.getPosterUrl());
        return DtoMapper.toMovieResponse(movieRepository.save(movie));
    }

    public void deleteMovie(@PathVariable Long id){
        movieRepository.deleteById(id);
    }

}
