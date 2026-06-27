package com.ayushman.movie.service;

import com.ayushman.movie.dto.request.ShowRequest;
import com.ayushman.movie.dto.response.ShowResponse;
import com.ayushman.movie.entity.Hall;
import com.ayushman.movie.entity.Movie;
import com.ayushman.movie.entity.Show;
import com.ayushman.movie.exception.InvalidRequestException;
import com.ayushman.movie.exception.ResourceNotFoundException;
import com.ayushman.movie.mapper.DtoMapper;
import com.ayushman.movie.repository.HallRepository;
import com.ayushman.movie.repository.MovieRepository;
import com.ayushman.movie.repository.ShowRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;
    private final HallService hallService;

    public List<ShowResponse> getAllShows(){
        return showRepository.findAll().stream()
                .map(DtoMapper::toShowResponse)
                .collect(Collectors.toList());
    }

    public ShowResponse getShowById(Long id){
        return DtoMapper.toShowResponse(showRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: " + id)));
    }

    public boolean checkSeatAvailability(Long showId, List<Integer> seatNumbers){
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: " + showId));
        List<Integer> availSeats = show.getAvailSeats();
        for(Integer seatNum : seatNumbers){
            if(availSeats.get(seatNum) == 1){
                return false;
            }
        }
        return true;
    }

    public ShowResponse createShow(ShowRequest showRequest){
        Movie movie = movieRepository.findById(showRequest.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + showRequest.getMovieId()));
        Hall hall = hallRepository.findById(showRequest.getHallId())
                .orElseThrow(() -> new ResourceNotFoundException("Hall not found with id: " + showRequest.getHallId()));
        LocalDateTime end = showRequest.getStart().plusMinutes(movie.getDurationInMinutes() + showRequest.getIntervalTime());
        boolean timeAvailability = hallService.checkTimeAvailability(
                showRequest.getHallId(), showRequest.getStart(), end
        );
        if(!timeAvailability){
            throw new InvalidRequestException("Hall is not available at the given time");
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
                .end(end)
                .availSeats(availSeats)
                .build();

        return DtoMapper.toShowResponse(showRepository.save(show));
    }

    public ShowResponse updateShowTiming(Long id, ShowRequest showRequest){
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: " + id));
        Movie movie = movieRepository.findById(showRequest.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + showRequest.getMovieId()));
        LocalDateTime end = showRequest.getStart().plusMinutes(movie.getDurationInMinutes() + showRequest.getIntervalTime());
        boolean timeAvailability = hallService.checkTimeAvailability(
                show.getHall().getId(), showRequest.getStart(), end
        );
        if(!timeAvailability){
            throw new InvalidRequestException("Hall is not available at the given time");
        }
        show.setStart(showRequest.getStart());
        show.setEnd(end);
        return DtoMapper.toShowResponse(showRepository.save(show));
    }

    public void deleteShow(Long id){
        showRepository.deleteById(id);
    }

}
