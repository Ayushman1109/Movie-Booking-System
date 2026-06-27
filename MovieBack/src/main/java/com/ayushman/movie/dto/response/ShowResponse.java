package com.ayushman.movie.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowResponse {
    private Long id;
    private MovieResponse movie;
    private HallResponse hall;
    private Long price;
    private LocalDateTime start;
    private LocalDateTime end;
    private List<Integer> availSeats;
}
