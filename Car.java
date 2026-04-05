/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advancedproject1;
/**
 *
 * @author hp
 */
public class Car extends Vehicle{
    private int numSeats;
    private String fuelType;
    private boolean hasGPS;
    
    public Car(int vehicleId, String model, double dailyRate, int year, int numSeats, String fuelType, boolean hasGPS){
        super(vehicleId, model, dailyRate, year);
        setNumSeats(numSeats);
        setFuelType(fuelType);
        this.hasGPS = hasGPS;
    }
    
    //Getters 
    
    public int getNumSeats() {
        return numSeats;
    }
    
    public String getFuelType() {
        return fuelType;
    }
    
    public boolean isHasGPS() {
        return hasGPS;
    }
    
    // Setters 
    
    public void setNumSeats(int numSeats) {
        if (numSeats < 1) {
            throw new IllegalArgumentException("Number of seats must be at least 1");
        }
        if (numSeats > 20) {
            throw new IllegalArgumentException("Number of seats cannot exceed 20");
        }
        this.numSeats = numSeats;
    }
    
    public void setFuelType(String fuelType) {
        if (fuelType == null || fuelType.trim().isEmpty()) {
            throw new IllegalArgumentException("Fuel type cannot be null or empty");
        }
        
        String[] validFuelTypes = {"Petrol", "Diesel", "Electric", "Hybrid", "CNG", "LPG"};
        fuelType = fuelType.trim();
        
        boolean isValid = false;
        for (String validType : validFuelTypes) {
            if (validType.equalsIgnoreCase(fuelType)) {
                this.fuelType = validType; // Store in standardized format
                isValid = true;
                break;
            }
        }
        
        if (!isValid) {
            throw new IllegalArgumentException(
                "Invalid fuel type: " + fuelType + 
                ". Valid types are: " + String.join(", ", validFuelTypes)
            );
        }
    }
    
    public void setHasGPS(boolean hasGPS) {
        this.hasGPS = hasGPS;
    }
    
      
    @Override
    public double calculateRent(int days) {
         if (days <= 0) {
            throw new IllegalArgumentException("Rental days must be positive");
        }
       double base = super.getDailyRate() * days;
        if (hasGPS) base += 50; // GPS surcharge
        double totalFee=base;
        
        double insurance = 20.0 * days;// insurance fee
        totalFee += insurance;
        return totalFee;
    }
    @Override
    public void displayInfo() {
    super.displayInfo();
    System.out.println("Seats: " + numSeats +
            ", Fuel: " + fuelType+
            ", GPS: " + (hasGPS ? "Yes" : "No")); 
    } 
}
