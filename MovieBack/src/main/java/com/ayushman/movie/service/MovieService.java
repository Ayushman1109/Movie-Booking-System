package com.ayushman.movie.service;

import com.ayushman.movie.dto.request.MovieRequest;
import com.ayushman.movie.entity.Movie;
import com.ayushman.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public Movie createNewMovie(MovieRequest movieRequest){
        Movie movie = Movie.builder()
                .name(movieRequest.getName())
                .rating(movieRequest.getRating())
                .build();
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, MovieRequest movieRequest){
        Movie movie = movieRepository.findById(id).orElseThrow();
        movie.setName(movieRequest.getName());
        movie.setRating(movieRequest.getRating());
        return movieRepository.save(movie);
    }

    public void deleteMovie(@PathVariable Long id){
        Movie movie = movieRepository.findById(id).orElseThrow();
        movieRepository.delete(movie);
    }

}
