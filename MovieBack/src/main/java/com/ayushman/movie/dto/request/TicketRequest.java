package com.ayushman.movie.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {
    @NotNull
    private Long showId;
    private String movieName;
    private List<Integer> seatNumbers;
}
