package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "donors")
public class donors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String email;
    private String password;
    private String bloodType;
    private String contact;
    
    // Default constructor
    public donors() {}
    
    // Constructor with parameters
    public donors(String username, String email, String password, String bloodType, String contact) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.bloodType = bloodType;
        this.contact = contact;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
}
