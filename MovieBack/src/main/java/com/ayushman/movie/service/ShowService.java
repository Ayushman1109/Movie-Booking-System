package com.ayushman.movie.service;

import com.ayushman.movie.dto.request.ShowRequest;
import com.ayushman.movie.entity.Hall;
import com.ayushman.movie.entity.Movie;
import com.ayushman.movie.entity.Show;
import com.ayushman.movie.repository.HallRepository;
import com.ayushman.movie.repository.MovieRepository;
import com.ayushman.movie.repository.ShowRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ShowService {

    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private HallService hallService;

    public List<Show> viewAllShows(){
        return showRepository.findAll();
    }

    public Show viewShowById(Long id){
        return showRepository.findById(id).orElseThrow();
    }

    public boolean checkSeatAvailability(Long showId, List<Integer> seatNumbers){
        Show show = showRepository.findById(showId).orElseThrow();
        List<Integer> availSeats = show.getAvailSeats();
        for(Integer seatNum : seatNumbers){
            if(availSeats.get(seatNum) == 1){
                return false;
            }
        }
        return true;
    }

    public Show createShow(ShowRequest showRequest){
        Movie movie = movieRepository.findById(showRequest.getMovieId()).orElseThrow();
        Hall hall = hallRepository.findById(showRequest.getHallId()).orElseThrow();
        boolean timeAvailability = hallService.checkTimeAvailability(
                showRequest.getHallId(), showRequest.getStart(), showRequest.getEnd()
        );
        if(!timeAvailability){
            throw new IllegalArgumentException("Hall is not available at the given time");
        }
        Integer totalSeats = hall.getTotalSeats();
        List<Integer> availSeats = new ArrayList<>();
        for(int i = 0; i < totalSeats; i++){
            availSeats.add(0);
        }
        Show show = Show.builder()
                .movie(movie)
                .hall(hall)
                .price(showRequest.getPrice())
                .start(showRequest.getStart())
                .end(showRequest.getEnd())
                .seatsBooked(0)
                .availSeats(availSeats)
                .build();

        return showRepository.save(show);
    }

    public void deleteShow(Long id){
        showRepository.deleteById(id);
    }

}
