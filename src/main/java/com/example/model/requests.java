package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
public class requests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long donationId;
    private String donorUsername;
    private String donorName; // Add this field to store donor's name
    private String patientName;
    private String mobileNo;
    private String organName;
    private LocalDateTime requestDate;
    private String status;

    // Default constructor
    public requests() {
        this.requestDate = LocalDateTime.now();
        this.status = "PENDING";
    }

    // Constructor with parameters
    public requests(Long donationId, String donorUsername, String donorName, String patientName, 
                   String mobileNo, String organName) {
        this.donationId = donationId;
        this.donorUsername = donorUsername;
        this.donorName = donorName; // Initialize donorName
        this.patientName = patientName;
        this.mobileNo = mobileNo;
        this.organName = organName;
        this.requestDate = LocalDateTime.now();
        this.status = "PENDING";
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getDonationId() { return donationId; }
    public void setDonationId(Long donationId) { this.donationId = donationId; }

    public String getDonorUsername() { return donorUsername; }
    public void setDonorUsername(String donorUsername) { this.donorUsername = donorUsername; }

    public String getDonorName() { return donorName; } // Getter for donorName
    public void setDonorName(String donorName) { this.donorName = donorName; } // Setter for donorName

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

    public String getOrganName() { return organName; }
    public void setOrganName(String organName) { this.organName = organName; }

    public LocalDateTime getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDateTime requestDate) { this.requestDate = requestDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
