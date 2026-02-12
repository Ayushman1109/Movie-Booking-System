package com.ayushman.movie.controller;

import com.ayushman.movie.dto.request.ShowRequest;
import com.ayushman.movie.entity.Show;
import com.ayushman.movie.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/show")
public class ShowController {
    @Autowired
    private ShowService showService;

    @GetMapping
    public List<Show> getAllShows(){ return showService.getAllShows(); }

    @GetMapping("/{id}")
    public Show getShowById(@PathVariable Long id){
        return showService.getShowById(id);
    }

    @PostMapping("/create")
    public Show createShow(@RequestBody ShowRequest showRequest){
        return showService.createShow(showRequest);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteShow(@PathVariable Long id){ showService.deleteShow(id); }

}
