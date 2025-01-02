package com.example.repository;

import com.example.model.requests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface requestsdata extends JpaRepository<requests, Long> {
    List<requests> findByDonorUsername(String donorUsername);
    List<requests> findByDonationId(Long donationId);
    List<requests> findByStatus(String status);
    List<requests> findByPatientName(String username);
}