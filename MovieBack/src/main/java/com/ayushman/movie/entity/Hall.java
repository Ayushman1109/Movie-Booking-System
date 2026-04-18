package com.ayushman.movie.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    private Integer totalSeats;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatre_id")
    @JsonIgnoreProperties("halls")
    private Theatre theatre;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("hall")
    private List<Show> shows;
}
