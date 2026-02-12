package com.ayushman.movie.controller;

import com.ayushman.movie.dto.request.TheatreRequest;
import com.ayushman.movie.entity.Theatre;
import com.ayushman.movie.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theatre")
public class TheatreController {
    @Autowired
    private TheatreService theatreService;

    @PostMapping("/create")
    public Theatre createTheatre(@RequestBody TheatreRequest request) {
        return theatreService.createTheatre(request);
    }

    public List<Theatre> getAllTheatres() {
        return theatreService.getAllTheatres();
    }

    @PutMapping("/update/{id}")
    public Theatre updateTheatre(@PathVariable Long id, @RequestBody TheatreRequest request) {
        return theatreService.updateTheatre(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteTheatre(@PathVariable long id) {
        theatreService.deleteTheatre(id);
    }

}
