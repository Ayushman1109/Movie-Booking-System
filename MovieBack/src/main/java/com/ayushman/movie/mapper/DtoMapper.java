package com.ayushman.movie.mapper;

import com.ayushman.movie.dto.response.*;
import com.ayushman.movie.entity.*;

public class DtoMapper {

    public static MovieResponse toMovieResponse(Movie movie) {
        if (movie == null) return null;
        return MovieResponse.builder()
                .id(movie.getId())
                .name(movie.getName())
                .language(movie.getLanguage())
                .durationInMinutes(movie.getDurationInMinutes())
                .rating(movie.getRating())
                .posterUrl(movie.getPosterUrl())
                .build();
    }

    public static TheatreResponse toTheatreResponse(Theatre theatre) {
        if (theatre == null) return null;
        return TheatreResponse.builder()
                .id(theatre.getId())
                .name(theatre.getName())
                .address(theatre.getAddress())
                .build();
    }

    public static HallResponse toHallResponse(Hall hall) {
        if (hall == null) return null;
        return HallResponse.builder()
                .id(hall.getId())
                .totalSeats(hall.getTotalSeats())
                .theatre(toTheatreResponse(hall.getTheatre()))
                .build();
    }

    public static ShowResponse toShowResponse(Show show) {
        if (show == null) return null;
        return ShowResponse.builder()
                .id(show.getId())
                .movie(toMovieResponse(show.getMovie()))
                .hall(toHallResponse(show.getHall()))
                .price(show.getPrice())
                .start(show.getStart())
                .end(show.getEnd())
                .availSeats(show.getAvailSeats())
                .build();
    }

    public static TicketResponse toTicketResponse(Ticket ticket) {
        if (ticket == null) return null;
        return TicketResponse.builder()
                .id(ticket.getId())
                .show(toShowResponse(ticket.getShow()))
                .movieName(ticket.getMovieName())
                .seatNumbers(ticket.getSeatNumbers())
                .cost(ticket.getCost())
                .build();
    }
}
