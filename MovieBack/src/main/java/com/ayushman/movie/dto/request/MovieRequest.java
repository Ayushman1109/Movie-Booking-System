package com.ayushman.movie.dto.request;

import java.util.List;

import com.ayushman.movie.entity.Show;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {
    @NotBlank(message = "Movie name cannot be blank")
    private String name;
    @NotBlank(message = "Movie language cannot be blank")
    private String language;
    @NotNull
    private Integer durationInMinutes;
    private Double rating;
    private String posterUrl;
    private List<Show> shows;
}
