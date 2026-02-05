package com.ayushman.movie.dto.request;

import com.ayushman.movie.entity.Show;
import lombok.Data;

@Data
public class TicketRequest {
    private Show show;
    private String movieName;
    private long seatNumber;
    private long cost;
}
