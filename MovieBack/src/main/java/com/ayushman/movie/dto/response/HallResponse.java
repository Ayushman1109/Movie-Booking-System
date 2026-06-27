package com.ayushman.movie.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HallResponse {
    private Long id;
    private Integer totalSeats;
    private TheatreResponse theatre;
}
