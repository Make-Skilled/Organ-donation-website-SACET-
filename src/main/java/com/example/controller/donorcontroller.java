package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.model.donors;
import com.example.model.donations;
import com.example.model.requests;
import com.example.repository.donordata;
import com.example.repository.donationsdata;
import com.example.repository.requestsdata;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/donor")
public class donorcontroller {

    @Autowired
    private donordata donorRepo;
    
    @Autowired
    private donationsdata donationRepo;
    
    @Autowired
    private requestsdata requestRepo;

    // API endpoint to update request status
    @PutMapping("/api/requests/{requestId}/status")
    @ResponseBody
    public ResponseEntity<?> updateRequestStatus(
            @PathVariable Long requestId,
            @RequestBody Map<String, String> statusUpdate,
            HttpSession session) {
        
        // Get the logged-in donor
        donors donor = (donors) session.getAttribute("donor");
        if (donor == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }

        try {
            // Find the request
            requests request = requestRepo.findById(requestId)
                    .orElseThrow(() -> new RuntimeException("Request not found"));

            // Verify that the logged-in donor is the owner of this request
            if (!request.getDonorUsername().equals(donor.getUsername())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized to update this request");
            }

            // Update the status
            request.setStatus(statusUpdate.get("status").toUpperCase());
            requestRepo.save(request);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating request: " + e.getMessage());
        }
    }
}