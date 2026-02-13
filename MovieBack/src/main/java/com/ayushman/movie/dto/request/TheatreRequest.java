package com.ayushman.movie.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheatreRequest {
    @NotBlank(message = "Theatre name cannot be blank")
    private String name;
    @NotBlank(message = "Theatre address cannot be blank")
    private String address;
}
