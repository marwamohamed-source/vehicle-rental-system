package advancedproject1;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hp
 */
public class VehicleRentalSystem{
     // Collections to store all data
    private List<Vehicle> vehicles;
    private List<Customer> customers;
    private List<Booking> bookings;
    
    public VehicleRentalSystem() {
        this.vehicles = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.bookings = new ArrayList<>();
        
    }
    
    // * Get all vehicles in the system
 //* @return List of all vehicles

public List<Vehicle> getVehicles() {
    return vehicles;
}

/**
 * Get all customers in the system
 * @return List of all customers
 */
public List<Customer> getCustomers() {
    return customers;
}

/**
 * Get all bookings in the system
 * @return List of all bookings
 */
public List<Booking> getBookings() {
    return bookings;
}
    // ========== VEHICLE MANAGEMENT METHODS ==========
     /**
     * Add a new vehicle to the system
     */
    public boolean addVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        // Check if vehicle ID already exists
        for (Vehicle v : vehicles) {
            if (v.getVehicleId() == vehicle.getVehicleId()) {
                System.out.println("Vehicle with ID " + vehicle.getVehicleId() + " already exists!");
                return false;
            }
        }
         vehicles.add(vehicle);
        System.out.println("Vehicle added successfully: " + vehicle.getModel());
        return true;   }
    /**
     * Remove a vehicle from the system
     */
    public boolean removeVehicle(int vehicleId) {
        Vehicle vehicle = findVehicleById(vehicleId);
        if (vehicle == null) {
            System.out.println("Vehicle with ID " + vehicleId + " not found!");
            return false;
        }
        // Check if vehicle is currently rented
        if (!vehicle.isAvailable()) {
            System.out.println("Cannot remove vehicle " + vehicleId + " - it is currently rented!");
            return false;
        }
        vehicles.remove(vehicle);
        System.out.println("Vehicle removed successfully: " + vehicle.getModel());
        return true;
}
     /**
     * Find vehicle by ID
     */
    public Vehicle findVehicleById(int vehicleId) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getVehicleId() == vehicleId) {
                return vehicle;
            }
        }
        return null;
    }
    /**
     * display all vehicles
     */
      public void displayAllVehicles() {
        System.out.println("\n=== ALL VEHICLES ===");
        System.out.println("Total: " + vehicles.size() + " vehicles");
        
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles available.");
            return;
        }
        System.out.println("\n----------------------------------------");
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle v = vehicles.get(i);
            System.out.println("VEHICLE #" + (i + 1));
            System.out.println("ID: " + v.getVehicleId());
            System.out.println("Model: " + v.getModel());
             System.out.println("Price: $" + v.getDailyRate() + "/day");
            System.out.println("Year: " + v.getYear());
            System.out.println("Status: " + (v.isAvailable() ? "Available" : "Rented"));
            System.out.println("----------------------------------------");
        }
}
       // ========== CUSTOMER MANAGEMENT METHODS ==========
    /**
     * Add a new customer to the system
     */
    public boolean addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        
        // Check if customer ID already exists
        for (Customer c : customers) {
            if (c.getCustomerId().equals(customer.getCustomerId())) {
                System.out.println("Customer with ID " + customer.getCustomerId() + " already exists!");
                return false;
            }
        }
         customers.add(customer);
        System.out.println("Customer added successfully: " + customer.getName());
        return true;
    }
    /**
     * Find customer by ID
     */
    public Customer findCustomerById(String customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }
     public void displayAllCustomers() {
        System.out.println("\n=== ALL CUSTOMERS ===");
        System.out.println("Total: " + customers.size() + " customers");
        
        if (customers.isEmpty()) {
            System.out.println("No customers registered.");
            return;
        }
        
        System.out.println("\n----------------------------------------");
        for (Customer c : customers) {
            System.out.println("ID: " + c.getCustomerId());
            System.out.println("Name: " + c.getName());
            System.out.println("Email: " + c.getEmail());
            System.out.println("Phone: " + c.getPhone());
            System.out.println("----------------------------------------");
        }
    }
     // ========== BOOKING MANAGEMENT METHODS ==========
     
  /**
 * Remove a customer from the system
 * @param customerId The ID of the customer to remove
 * @return true if customer was successfully removed, false otherwise
 */
public boolean removeCustomer(String customerId) {
    // Find the customer
    Customer customerToRemove = null;
    for (Customer customer : customers) {
        if (customer.getCustomerId().equals(customerId)) {
            customerToRemove = customer;
            break;
        }
    }
    
    // Check if customer exists
    if (customerToRemove == null) {
        System.out.println("Customer with ID '" + customerId + "' not found!");
        return false;
    }
    
    // Check if customer has any active bookings
    for (Booking booking : bookings) {
        if (booking.getCustomer().getCustomerId().equals(customerId) && 
            booking.getStatus().equalsIgnoreCase("ACTIVE")) {
            System.out.println("Cannot remove customer '" + customerId + 
                             "' - they have active bookings!");
            System.out.println("Active Booking ID: " + booking.getBookingId());
            return false;
        }
    }
    
    // Remove the customer
    customers.remove(customerToRemove);
    System.out.println("Customer '" + customerToRemove.getName() + 
                      "' (ID: " + customerId + ") has been removed from the system.");
    return true;
}  

public void createBooking(String bookingId, Customer customer, Vehicle vehicle,Date startDate, Date endDate) {                          
        // Validate inputs
        if (customer == null || vehicle == null || startDate == null || endDate == null) {
            throw new IllegalArgumentException("All booking parameters must be non-null");
        }
        
        if (endDate.before(startDate)) {
            throw new IllegalArgumentException("End date csannot be before start date");
        }
        
        // Check if customer is verified
        if (!customer.isVerified()) {
            throw new IllegalStateException("Customer must be verified before renting");
        }
         // Check if vehicle is available
        if (!vehicle.isAvailable()) {
            throw new IllegalStateException("Vehicle is not available for rent");
        }
        
        // Create the booking
        Booking booking = new Booking(bookingId, customer, vehicle, startDate, endDate);
        
        // Add to bookings list
        bookings.add(booking);
        
        // Update vehicle status
        vehicle.setAvailable(false);
        
        // Add booking to customer's history
        customer.addRental(booking);
         System.out.println("\n=== BOOKING CREATED SUCCESSFULLY ===");
    }
    /**
     * Display all bookings
     */
    public void displayAllBookings() {
        System.out.println("\n=== ALL BOOKINGS (" + bookings.size() + ") ===");
        if (bookings.isEmpty()) {
            System.out.println("No bookings yet.");
            return;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Booking booking : bookings) {
            System.out.println("\nBooking ID: " + booking.getBookingId());
            System.out.println("Customer: " + booking.getCustomer().getName() + " (ID: " + booking.getCustomer().getCustomerId() + ")");
            System.out.println("Vehicle: " + booking.getVehicle().getModel() + " (ID: " + booking.getVehicle().getVehicleId() + ")");
            System.out.println("Dates: " + sdf.format(booking.getStartDate()) + " to " + sdf.format(booking.getEndDate()));
            System.out.println("Status: " + booking.getStatus());
            System.out.println("Total Price: $" + booking.getTotalPrice());
        }
    }
    
    /**
     * Sort vehicles by model using Comparable interface
     * This method uses Collections.sort() which relies on the compareTo() method
     * implemented in the Vehicle class through the Comparable interface
     */
    public void sortVehiclesByModel() {
        Collections.sort(vehicles);
        System.out.println("Vehicles sorted by model using Comparable interface.");
    }
}