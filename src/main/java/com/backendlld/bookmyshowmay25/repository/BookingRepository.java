package com.backendlld.bookmyshowmay25.repository;

import com.backendlld.bookmyshowmay25.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
