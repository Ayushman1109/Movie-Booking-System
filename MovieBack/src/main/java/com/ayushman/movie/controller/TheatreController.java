package com.ayushman.movie.controller;

import com.ayushman.movie.dto.request.TheatreRequest;
import com.ayushman.movie.dto.response.TheatreResponse;
import com.ayushman.movie.service.TheatreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theatre")
@RequiredArgsConstructor
public class TheatreController {
    private final TheatreService theatreService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TheatreResponse> createTheatre(@Valid @RequestBody TheatreRequest request) {
        return ResponseEntity.status(201).body(theatreService.createTheatre(request));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TheatreResponse>> getAllTheatres() {
        return ResponseEntity.ok(theatreService.getAllTheatres());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TheatreResponse> getTheatreById(@PathVariable Long id) {
        return ResponseEntity.ok(theatreService.getTheatreById(id));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TheatreResponse> updateTheatre(@PathVariable Long id, @Valid @RequestBody TheatreRequest request) {
        return ResponseEntity.ok(theatreService.updateTheatre(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteTheatre(@PathVariable long id) {
        theatreService.deleteTheatre(id);
        return ResponseEntity.ok("Theatre deleted successfully");
    }

}
