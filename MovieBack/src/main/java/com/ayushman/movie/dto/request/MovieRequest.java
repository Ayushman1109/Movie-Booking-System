package com.ayushman.movie.dto.request;

import com.ayushman.movie.entity.Show;
import lombok.Data;

import java.util.List;

@Data
public class MovieRequest {
    private String name;
    private String language;
    private int durationInMinutes;
    private int rating;
    private List<Show> shows;
}
