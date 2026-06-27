package com.ayushman.movie.controller;

import com.ayushman.movie.dto.request.MovieRequest;
import com.ayushman.movie.dto.response.MovieResponse;
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
    public List<MovieResponse> getAllMovies(){
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public MovieResponse getMovieById(@PathVariable Long id){
        return movieService.getMovieById(id);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MovieResponse> createNewMovie(@Valid @RequestBody MovieRequest movieRequest){
        return ResponseEntity.status(201).body(movieService.createNewMovie(movieRequest));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable Long id, @Valid @RequestBody MovieRequest movieRequest){
        return ResponseEntity.ok(movieService.updateMovie(id, movieRequest));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id){
        movieService.deleteMovie(id);
        return ResponseEntity.ok("Movie deleted successfully");
    }

}
