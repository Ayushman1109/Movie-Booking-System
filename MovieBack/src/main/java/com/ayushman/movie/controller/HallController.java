package com.ayushman.movie.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayushman.movie.entity.Hall;
import com.ayushman.movie.service.HallService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/hall")
@RequiredArgsConstructor
public class HallController {

    private final HallService hallService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Hall>> getAllHalls() {
        return ResponseEntity.ok(hallService.getAllHalls());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Hall> getHallById(@PathVariable long id) {
        return ResponseEntity.ok(hallService.getHallById(id));
    }

    @PostMapping("/create/{theatreId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Hall> addHallToTheatre(
            @PathVariable long theatreId,
            @RequestParam Integer totalSeats) {
        return ResponseEntity.status(201).body(hallService.addHallToTheatre(theatreId, totalSeats));
    }

    @PostMapping("/createBulk/{theatreId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Hall>> addHallsToTheatre(
            @PathVariable long theatreId,
            @RequestBody List<Integer> totalSeatsList) {
        return ResponseEntity.status(201).body(hallService.addHallsToTheatre(theatreId, totalSeatsList));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Hall> updateHall(
            @PathVariable long id,
            @RequestParam Integer totalSeats) {
        return ResponseEntity.ok(hallService.updateHall(id, totalSeats));
    }

    @DeleteMapping("/delete/{hallId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Hall> deleteHall(
            @PathVariable long hallId,
            @RequestParam long theatreId) {
        return ResponseEntity.ok(hallService.deleteHallFromTheatre(theatreId, hallId));
    }
}
