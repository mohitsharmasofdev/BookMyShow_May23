package com.backendlld.bookmyshowmay25.repository;

import com.backendlld.bookmyshowmay25.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
} 