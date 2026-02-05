package com.ayushman.movie.dto.request;

import com.ayushman.movie.entity.Hall;
import com.ayushman.movie.entity.Movie;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class ShowRequest {

    private Long movieId;
    private Long hallId;
    private long price;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private Integer seatsBooked;
    private List<Long> availSeats;

}
