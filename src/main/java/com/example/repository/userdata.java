package com.example.repository;

import com.example.model.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userdata extends JpaRepository<users, Long> {
    users findByUsername(String username);
    users findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}