package com.ayushman.movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "shows")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_id")
    private Long id;

    @Version
    @Column(name = "version")
    private Long version;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonIgnoreProperties("shows")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id")
    @JsonIgnoreProperties({"shows", "hall"})
    private Hall hall;

    @Column(name = "price")
    private Long price;
    @Column(name = "start_time")
    private LocalDateTime start;
    @Column(name = "end_time")
    private LocalDateTime end;
    @ElementCollection
    private List<Integer> availSeats;
}
