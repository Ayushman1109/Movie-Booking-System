package com.ayushman.movie.entity;

import java.util.List;

public class Show {
    private long id;
    private long movieId;
    private long hallId;
    private long price;
    private long seatsBooked = 0;
    private List<Long> availSeats;
}
