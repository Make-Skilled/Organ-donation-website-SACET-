package com.example.repository;

import com.example.model.donors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface donordata extends JpaRepository<donors, Long> {
    List<donors> findByBloodType(String bloodType);
    donors findByUsername(String username);
    donors findByEmail(String email);
}