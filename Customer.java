package advancedproject1;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hp
 */
public class Customer {
    private String customerId;
    private String name;
    private String email;
    private String phone;
    private List<Booking> rentalHistory;  
    private boolean isVerified;

    // Constructor
    public Customer(String customerId, String name, String email, String phone) {
        setCustomerId(customerId);
        setName(name);
        setEmail(email);
        setPhone(phone);
        this.rentalHistory = new ArrayList<>();
        this.isVerified = false;  // Needs verification before first rental
    }
    
    
    // Getters 
    
     public String getCustomerId() {
        return customerId;
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public List<Booking> getRentalHistory() {
        return new ArrayList<>(rentalHistory);  // Return copy for encapsulation
    }
    
    public boolean isVerified() {
        return isVerified;
    }

    
    //Setters 
    public void setCustomerId(String customerId) {
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty");
        }
        this.customerId = customerId.trim();
    }
    
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name.trim();
    }
    
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email.trim().toLowerCase();
    }
    
    public void setPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be null or empty");
        }
        String digitsOnly = phone.replaceAll("\\D", "");
            
            if (digitsOnly.length() < 10) {
                throw new IllegalArgumentException("Phone number must be at least 10 digits");
            }
            this.phone = digitsOnly;
    }
    
    public void setVerified(boolean verified) {
        this.isVerified = verified;
    }
    
    
    
    //Methods 
     public void addRental(Booking booking) {
            if (booking == null) {
                throw new IllegalArgumentException("Booking cannot be null");
            }
            rentalHistory.add(booking);
        }
     
     
    public boolean canRentVehicle() {
        // Basic check: customer must be verified
        return isVerified;
    }
    
    public int getTotalRentals() {
        return rentalHistory.size();
    }
    
    // Display basic info
    public void displayInfo() {
        System.out.println("Customer ID: " + customerId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Verified: " + (isVerified ? "Yes" : "No"));
        System.out.println("Total Rentals: " + getTotalRentals());
    }
   
    }
