package com.ayushman.movie.controller;

import com.ayushman.movie.dto.request.MovieRequest;
import com.ayushman.movie.entity.Movie;
import com.ayushman.movie.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Movie> getAllMovies(){
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Movie getMovieById(@PathVariable Long id){
        return movieService.getMovieById(id);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Movie> createNewMovie(@Valid @RequestBody MovieRequest movieRequest){
        return ResponseEntity.ok(movieService.createNewMovie(movieRequest));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @Valid @RequestBody MovieRequest movieRequest){
        return ResponseEntity.ok(movieService.updateMovie(id, movieRequest));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id){
        movieService.deleteMovie(id);
        return ResponseEntity.ok("Movie deleted successfully");
    }

}
