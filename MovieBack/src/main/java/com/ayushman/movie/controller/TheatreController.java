package com.ayushman.movie.controller;

import com.ayushman.movie.dto.request.TheatreRequest;
import com.ayushman.movie.entity.Theatre;
import com.ayushman.movie.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/theatre")
public class TheatreController {
    @Autowired
    private TheatreService theatreService;

    public Theatre createTheatre(TheatreRequest request) {
        return theatreService.createTheatre(request);
    }

    public List<Theatre> getAllTheatres() {
        return theatreService.getAllTheatres();
    }

    public Theatre updateTheatre(long id, TheatreRequest request) {
        return theatreService.updateTheatre(id, request);
    }

    public void deleteTheatre(long id) {
        theatreService.deleteTheatre(id);
    }

}
