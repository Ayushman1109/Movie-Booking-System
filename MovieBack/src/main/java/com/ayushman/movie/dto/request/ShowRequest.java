package com.ayushman.movie.dto.request;

import com.ayushman.movie.entity.Hall;
import com.ayushman.movie.entity.Movie;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class ShowRequest {

    private Long movieId;
    private Long hallId;
    private long price;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalTime endTime;
    private Integer seatsBooked;
    private List<Integer> availSeats;

}
