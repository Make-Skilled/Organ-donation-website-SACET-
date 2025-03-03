package com.example.controller;

import com.example.model.donations;
import com.example.model.donors;
import com.example.model.requests;
import com.example.model.users;
import com.example.repository.donationsdata;
import com.example.repository.donordata;
import com.example.repository.requestsdata;
import com.example.repository.userdata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class usercontroller {

    @Autowired
    private userdata userRepo;

    @Autowired
    private donordata donorRepo;

    @Autowired
    private donationsdata donationRepo;

    @Autowired
    private requestsdata requestsRepo;

    // Main and Registration/Login Routes
    @GetMapping("/")
    public String mainpage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; // Returns the registration page
    }

    @PostMapping("/register")
    public String registerUser(users user, Model model) {
        userRepo.save(user);
        model.addAttribute("status", "Registration successful");
        return "redirect:/userlogin";
    }

    @GetMapping("/userlogin")
    public String showlogin() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, HttpSession session,
                            RedirectAttributes redirectAttributes) {
        users user = userRepo.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user); // Store user in session
            return "redirect:/user/dashboard";
        }
        redirectAttributes.addFlashAttribute("error", "Invalid username or password");
        return "redirect:/userlogin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // Donor Registration/Login
    @GetMapping("/donor/register")
    public String showDonorRegistrationForm() {
        return "donorregister";
    }

    @PostMapping("/donor/register")
    public String registerDonor(donors donor, Model model) {
        donorRepo.save(donor);
        model.addAttribute("status", "Registration successful");
        return "redirect:/donor/login";
    }

    @GetMapping("/donor/login")
    public String showDonorLoginForm() {
        return "donorlogin";
    }

    @GetMapping("/donor/dashboard")
    public String ddash(HttpSession session, Model model) {
        // Retrieve the logged-in donor
        donors donor = (donors) session.getAttribute("donor");
        if (donor == null) {
            return "redirect:/donor/login"; // Redirect to login if not authenticated
        }

        // Fetch all requests where the donor's username matches
        List<requests> donorRequests = requestsRepo.findByDonorUsername(donor.getUsername());

        // Add the requests to the model
        model.addAttribute("requests", donorRequests);
        model.addAttribute("donorname", donor.getUsername());

        return "donordashboard"; // Return the donor dashboard view
    }


    @PostMapping("/donor/login")
    public String loginDonor(@RequestParam String username, @RequestParam String password, HttpSession session,
                             RedirectAttributes redirectAttributes) {
        donors donor = donorRepo.findByUsername(username);
        if (donor != null && donor.getPassword().equals(password)) {
            session.setAttribute("donor", donor); // Store donor in session
            return "redirect:/donor/dashboard";
        }
        redirectAttributes.addFlashAttribute("error", "Invalid donor username or password");
        return "redirect:/donor/login";
    }

    @GetMapping("/adddonation")
    public String donationadd() {
        return "adddonation";
    }
    


    // Donor Dashboard to Add Donations
    @PostMapping("/addingdonation")
    public String addDonation(@RequestParam String organType,
                              @RequestParam String donorName,
                              @RequestParam int donorAge,
                              @RequestParam String bloodGroup,
                              @RequestParam String gender,
                              @RequestParam String image,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {

        try {
            // Get the logged-in donor
            donors donor = (donors) session.getAttribute("donor");
            if (donor == null) {
                return "redirect:/donor/login";
            }

            // Create new donation object
            donations donation = new donations(
                donor.getUsername(),  // donorUsername from session
                organType,
                donorName,
                donorAge,
                bloodGroup,
                gender,
                image
            );

            // Save the donation
            donationRepo.save(donation);

            redirectAttributes.addFlashAttribute("message", "Donation added successfully!");
            return "redirect:/donor/dashboard";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to add donation: " + e.getMessage());
            return "redirect:/donor/donations/add";
        }
    }

    @GetMapping("/mydonation")
    public String mydonation(HttpSession session, Model model) {
        donors donor = (donors) session.getAttribute("donor");
        if (donor == null) {
            return "redirect:/donor/login"; // Redirect to login if not authenticated
        }
        
        // Fetch donations by the current donor's username
        List<donations> allDonations = donationRepo.findByDonorUsername(donor.getUsername());
        
        model.addAttribute("donations", allDonations);

        return "mydonations";  // This will return the mydonations.html view
    }


    // Display User Dashboard with Available Donations
    @GetMapping("/user/dashboard")
    public String userDashboard(HttpSession session, Model model) {
        // Fetch the logged-in user's username from session
        users user = (users) session.getAttribute("user");
        if (user == null) {
            return "redirect:/userlogin"; // Redirect to login if not authenticated
        }

        // Fetch all donations
        List<donations> allDonations = donationRepo.findAll();

        // Add username and donations to the model
        model.addAttribute("username", user.getUsername());
        model.addAttribute("donations", allDonations);

        return "dashboard"; // Return the user dashboard view
    }
    

    // Handle User Request for Donation
    @PostMapping("/request_donation/{id}")
    public String requestDonation(@PathVariable("id") Long donationId, HttpSession session,
                                RedirectAttributes redirectAttributes) {
        // Get the logged-in user
        users user = (users) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // Redirect to login if not authenticated
        }

        try {
            // Retrieve the donation
            donations donation = donationRepo.findById(donationId)
                    .orElseThrow(() -> new RuntimeException("Donation not found"));

                    System.out.println((donation));

            // Create a new request object
            requests request = new requests(
                    donationId, // Donation ID
                    donation.getDonorUsername(),
                    donation.getDonorName(), // Donor's username
                    user.getUsername(), // Patient's name from the form
                    user.getContact(), // Mobile number from the form
                    donation.getOrganType() // Requested organ type
            );

            // Save the request to the database
            requestsRepo.save(request);

            // Add success message
            redirectAttributes.addFlashAttribute("message", "Donation request submitted successfully!");
        } catch (Exception e) {
            // Add error message
            redirectAttributes.addFlashAttribute("error", "Failed to request donation: " + e.getMessage());
        }

        return "redirect:/user/dashboard"; // Redirect back to the dashboard
    }

    @GetMapping("/myrequests")
    public String myRequests(HttpSession session, Model model) {
        // Get the logged-in user
        users user = (users) session.getAttribute("user");
        if (user == null) {
            return "redirect:/userlogin"; // Redirect to login if not authenticated
        }

        // Fetch all requests associated with the logged-in user's username
        List<requests> userRequests = requestsRepo.findByPatientName(user.getUsername());

        // Add the requests to the model
        model.addAttribute("requests", userRequests);

        return "myrequests"; // Return the myrequests.html view
    }

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
            requests request = requestsRepo.findById(requestId)
                    .orElseThrow(() -> new RuntimeException("Request not found"));

            // Verify that the logged-in donor is the owner of this request
            if (!request.getDonorUsername().equals(donor.getUsername())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized to update this request");
            }

            // Update the status
            request.setStatus(statusUpdate.get("status").toUpperCase());
            requestsRepo.save(request);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating request: " + e.getMessage());
        }
    }

    // Logout for Donor
    @GetMapping("/donor/logout")
    public String donorLogout(HttpSession session) {
        // Invalidate the donor session
        session.invalidate();
        // Redirect to the donor login page
        return "redirect:/donor/login";
    }

    @GetMapping("/user/logout")
    public String userlogout(HttpSession session) {

        session.invalidate();
        return "redirect:/userlogin";
    }
}
