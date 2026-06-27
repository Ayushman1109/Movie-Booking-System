package com.ayushman.movie.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponse {
    private Long id;
    private ShowResponse show;
    private String movieName;
    private List<Integer> seatNumbers;
    private Long cost;
}
