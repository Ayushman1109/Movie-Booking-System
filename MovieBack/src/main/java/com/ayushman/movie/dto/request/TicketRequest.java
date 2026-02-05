package com.ayushman.movie.dto.request;

import lombok.Data;

@Data
public class TicketRequest {
    private long showId;
    private long seatNumber;
}
