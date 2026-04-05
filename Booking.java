package advancedproject1;
import java.util.Date;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hp
 */
public class Booking {
    private String bookingId;
    private Customer customer;
    private Vehicle vehicle;
    private Date startDate;
    private Date endDate;
    private double totalPrice;
    private String status; // "PENDING", "ACTIVE", "COMPLETED", "CANCELLED"
    
    // Constructor 
    public Booking(String bookingId, Customer customer, Vehicle vehicle, 
                   Date startDate, Date endDate) {
        
        setBookingId(bookingId);
        setCustomer(customer);
        setVehicle(vehicle);
        setDates(startDate, endDate);
        this.status = "PENDING";
        calculateTotal();
    }
    
    //Getter 
     // Essential getters
    public String getBookingId() { return bookingId; }
    public Customer getCustomer() { return customer; }
    public Vehicle getVehicle() { return vehicle; }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    
    //  setter
    public void setBookingId(String bookingId) {
        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new IllegalArgumentException("Booking ID cannot be null or empty");
        }
        this.bookingId = bookingId.trim();
    }
    
    public void setCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        this.customer = customer;
    }
    
    public void setVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        if (!vehicle.isAvailable()) {
            throw new IllegalStateException("Vehicle is not available for booking");
        }
        this.vehicle = vehicle;
    }
    
    public void setDates(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        if (endDate.before(startDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public void setStatus(String status) {
        String[] validStatuses = {"PENDING", "ACTIVE", "COMPLETED", "CANCELLED"};
        for (String validStatus : validStatuses) {
            if (validStatus.equals(status)) {
                this.status = status;
                
                // Update vehicle availability
                if ("ACTIVE".equals(status)) {
                    vehicle.setAvailable(false);
                } else if ("COMPLETED".equals(status) || "CANCELLED".equals(status)) {
                    vehicle.setAvailable(true);
                }
                return;
            }
        }
        throw new IllegalArgumentException("Invalid status. Must be: PENDING, ACTIVE, COMPLETED, CANCELLED");
    }
    
    
    public void calculateTotal() {
        int days = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
        if (days < 1) days = 1; // Minimum 1 day charge
        totalPrice = vehicle.calculateRent(days);
    }
    
    public void displayBooking() {
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Customer: " + customer.getName());
        System.out.println("Vehicle: " + vehicle.getModel());
        System.out.println("Dates: " + startDate + " to " + endDate);
        System.out.println("Total: $" + totalPrice);
        System.out.println("Status: " + status);
    }

    
}
