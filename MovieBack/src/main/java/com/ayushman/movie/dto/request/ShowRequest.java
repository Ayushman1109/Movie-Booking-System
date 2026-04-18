package com.ayushman.movie.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowRequest {
    @NotNull
    private Long movieId;
    @NotNull
    private Long hallId;
    @NotNull
    private Long price;
    @NotNull
    private LocalDateTime start;
    @NotNull
    private Integer intervalTime;
    private List<Integer> availSeats;

}
