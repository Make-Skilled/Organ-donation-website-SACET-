package com.example.repository;

import com.example.model.donations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface donationsdata extends JpaRepository<donations, Long> {
    List<donations> findByDonorUsername(String donorUsername);
    List<donations> findByOrganType(String organType);
    List<donations> findByBloodGroup(String bloodGroup);
}
