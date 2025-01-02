package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "donations")
public class donations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String donorUsername;
    private String organType;
    private String donorName;
    private int donorAge;
    private String bloodGroup;
    private String gender;
    private String image;

    // Default constructor
    public donations() {}

    // Constructor with parameters
    public donations(String donorUsername, String organType, String donorName, 
                    int donorAge, String bloodGroup, String gender, String image) {
        this.donorUsername = donorUsername;
        this.organType = organType;
        this.donorName = donorName;
        this.donorAge = donorAge;
        this.bloodGroup = bloodGroup;
        this.gender = gender;
        this.image = image;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getDonorUsername() { return donorUsername; }
    public void setDonorUsername(String donorUsername) { this.donorUsername = donorUsername; }
    
    public String getOrganType() { return organType; }
    public void setOrganType(String organType) { this.organType = organType; }
    
    public String getDonorName() { return donorName; }
    public void setDonorName(String donorName) { this.donorName = donorName; }
    
    public int getDonorAge() { return donorAge; }
    public void setDonorAge(int donorAge) { this.donorAge = donorAge; }
    
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}