package com.backendlld.bookmyshowmay25.repository;

import com.backendlld.bookmyshowmay25.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
} 