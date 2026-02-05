package com.ayushman.movie.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class ShowRequest {

    private long movieId;
    private long hallId;
    private long price;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private long seatsBooked = 0;
    private List<Long> availSeats;

}
