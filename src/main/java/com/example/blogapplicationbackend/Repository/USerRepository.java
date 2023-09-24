package com.example.blogapplicationbackend.Repository;

import com.example.blogapplicationbackend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface USerRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
