package com.backendlld.bookmyshowmay25.repository;

import com.backendlld.bookmyshowmay25.model.ShowSeat;
import com.backendlld.bookmyshowmay25.model.ShowSeatStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    List<ShowSeat> findByShowIdAndStatus(Long showId, ShowSeatStatus status);
    List<ShowSeat> findByShowIdAndSeatIdIn(Long showId, List<Long> seatIds);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ss FROM ShowSeat ss WHERE ss.id IN :ids AND ss.status = :status")
    List<ShowSeat> findAllByIdAndStatus(@Param("ids") List<Long> ids, @Param("status") ShowSeatStatus status);
}

// select ... for update;