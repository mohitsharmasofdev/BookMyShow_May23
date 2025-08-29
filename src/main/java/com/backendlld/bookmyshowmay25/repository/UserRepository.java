package com.backendlld.bookmyshowmay25.repository;

import com.backendlld.bookmyshowmay25.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
