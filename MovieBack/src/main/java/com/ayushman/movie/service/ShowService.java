package com.ayushman.movie.service;

import com.ayushman.movie.dto.request.ShowRequest;
import com.ayushman.movie.entity.Hall;
import com.ayushman.movie.entity.Movie;
import com.ayushman.movie.entity.Show;
import com.ayushman.movie.repository.HallRepository;
import com.ayushman.movie.repository.MovieRepository;
import com.ayushman.movie.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ShowService {

    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private HallRepository hallRepository;

    public List<Show> viewAllShows(){
        return showRepository.findAll();
    }

    public Show createShow(ShowRequest showRequest){
        Movie movie = movieRepository.findById(showRequest.getMovieId()).orElseThrow();
        Hall hall = hallRepository.findById(showRequest.getHallId()).orElseThrow();

        Integer totalSeats = hall.getTotalSeats();
        List<Long> availSeats = new ArrayList<>();
        for(int i = 0; i < totalSeats; i++){
            availSeats.add(0L);
        }

        Show show = Show.builder()
                .movie(movie)
                .hall(hall)
                .price(showRequest.getPrice())
                .startDate(showRequest.getStartDate())
                .startTime(showRequest.getStartTime())
                .endDate(showRequest.getEndDate())
                .endTime(showRequest.getEndTime())
                .seatsBooked(0)
                .availSeats(availSeats)
                .build();

        return showRepository.save(show);
    }

    public Show updateShow(Long id, ShowRequest showRequest){
        Show show = showRepository.findById(id).orElseThrow();
        Movie movie = movieRepository.findById(showRequest.getMovieId()).orElseThrow();
        Hall hall = hallRepository.findById(showRequest.getHallId()).orElseThrow();

        show.setMovie(movie);
        show.setHall(hall);
        show.setPrice(showRequest.getPrice());
        show.setStartDate(showRequest.getStartDate());
        show.setStartTime(showRequest.getStartTime());
        show.setEndDate(showRequest.getEndDate());
        show.setEndTime(showRequest.getEndTime());
        show.setSeatsBooked(showRequest.getSeatsBooked());
        show.setAvailSeats(showRequest.getAvailSeats());

        return showRepository.save(show);
    }

    public void deleteShow(Long id){
        Show show = showRepository.findById(id).orElseThrow();
        showRepository.delete(show);
    }

}
