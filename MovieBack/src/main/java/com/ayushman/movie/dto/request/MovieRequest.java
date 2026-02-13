package com.ayushman.movie.dto.request;

import com.ayushman.movie.entity.Show;
import jakarta.validation.constraints.NotBlank;
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
public class MovieRequest {
    @NotBlank(message = "Movie name cannot be blank")
    private String name;
    @NotBlank(message = "Movie language cannot be blank")
    private String language;
    @NotNull
    private Integer durationInMinutes;
    private Integer rating;
    private List<Show> shows;
}
