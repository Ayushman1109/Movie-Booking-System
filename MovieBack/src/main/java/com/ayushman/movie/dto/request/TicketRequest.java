package com.ayushman.movie.dto.request;

import com.ayushman.movie.entity.Show;
import lombok.Data;

import java.util.List;

@Data
public class TicketRequest {
    private Long showId;
    private String movieName;
    private List<Integer> seatNumbers;
}
