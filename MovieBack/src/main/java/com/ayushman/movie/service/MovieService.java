package com.ayushman.movie.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.ayushman.movie.dto.request.MovieRequest;
import com.ayushman.movie.entity.Movie;
import com.ayushman.movie.repository.MovieRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id){ return movieRepository.findById(id).orElseThrow(); }

    public Movie createNewMovie(MovieRequest movieRequest){
        Movie movie = Movie.builder()
                .name(movieRequest.getName())
                .language(movieRequest.getLanguage())
                .durationInMinutes(movieRequest.getDurationInMinutes())
                .rating(movieRequest.getRating())
                .posterUrl(movieRequest.getPosterUrl())
                .build();
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, MovieRequest movieRequest){
        Movie movie = movieRepository.findById(id).orElseThrow();
        movie.setName(movieRequest.getName());
        movie.setLanguage(movieRequest.getLanguage());
        movie.setDurationInMinutes(movieRequest.getDurationInMinutes());
        movie.setRating(movieRequest.getRating());
        movie.setPosterUrl(movieRequest.getPosterUrl());
        return movieRepository.save(movie);
    }

    public void deleteMovie(@PathVariable Long id){
        movieRepository.deleteById(id);
    }

}
