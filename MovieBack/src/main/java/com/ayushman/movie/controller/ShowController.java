package com.ayushman.movie.controller;

import com.ayushman.movie.dto.request.ShowRequest;
import com.ayushman.movie.entity.Show;
import com.ayushman.movie.service.ShowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/show")
@RequiredArgsConstructor
public class ShowController {
    private final ShowService showService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Show>> getAllShows(){ return ResponseEntity.ok(showService.getAllShows()); }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Show> getShowById(@PathVariable Long id){
        return ResponseEntity.ok(showService.getShowById(id));
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Show> createShow(@Valid @RequestBody ShowRequest showRequest){
        return ResponseEntity.ok(showService.createShow(showRequest));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Show> updateShowTiming(@PathVariable Long id, @Valid @RequestBody ShowRequest showRequest){
        return ResponseEntity.ok(showService.updateShowTiming(id, showRequest));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteShow(@PathVariable Long id){
        showService.deleteShow(id);
        return ResponseEntity.ok("Show deleted successfully");
    }

}
