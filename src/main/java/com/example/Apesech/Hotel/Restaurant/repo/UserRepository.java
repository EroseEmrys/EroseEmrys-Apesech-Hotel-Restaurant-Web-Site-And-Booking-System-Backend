package com.example.Apesech.Hotel.Restaurant.repo;

import com.example.Apesech.Hotel.Restaurant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

}
