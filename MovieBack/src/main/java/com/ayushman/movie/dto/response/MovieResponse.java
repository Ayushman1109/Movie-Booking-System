package com.ayushman.movie.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponse {
    private Long id;
    private String name;
    private String language;
    private Integer durationInMinutes;
    private Double rating;
    private String posterUrl;
}
