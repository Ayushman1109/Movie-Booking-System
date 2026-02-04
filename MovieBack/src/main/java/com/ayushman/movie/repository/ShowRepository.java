package com.ayushman.movie.repository;

import com.ayushman.movie.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show,Long> {

}
