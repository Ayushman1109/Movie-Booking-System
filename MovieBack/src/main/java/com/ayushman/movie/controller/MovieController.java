package com.ayushman.movie.controller;

import com.ayushman.movie.dto.request.MovieRequest;
import com.ayushman.movie.entity.Movie;
import com.ayushman.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<Movie> getAllMovies(){
        return movieService.getAllMovies();
    }

    @PostMapping("/create")
    public Movie createNewMovie(@RequestBody MovieRequest movieRequest){
        return movieService.createNewMovie(movieRequest);
    }

    @PutMapping("/update/{id}")
    public Movie updateMovie(@PathVariable Long id, @RequestBody MovieRequest movieRequest){
        return movieService.updateMovie(id, movieRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id){
        movieService.deleteMovie(id);
    }

}
