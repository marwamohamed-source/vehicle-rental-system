/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advancedproject1;
/**
 *
 * @author hp
 */
public class Van extends Vehicle {
    
    private double cargoCapacity;
    private boolean hasAC;
    
    // van constructor 
    public Van(int vehicleId, String model, double dailyRate, int year, double cargoCapacity, boolean hasAC){
        super(vehicleId, model, dailyRate, year);
        setCargoCapacity(cargoCapacity);
        this.hasAC = hasAC;
    }
    
    // Getters 

    public double getCargoCapacity() {
        return cargoCapacity;
    }
    
    public boolean isHasAC() {
        return hasAC;
    }
    
    
    
    //Setters
    
    public void setCargoCapacity(double cargoCapacity) {
        if (cargoCapacity <= 0) {
            throw new IllegalArgumentException("Cargo capacity must be positive");
        }
        if (cargoCapacity > 50) {
            throw new IllegalArgumentException("Cargo capacity cannot exceed 50 cubic meters");
        }
        this.cargoCapacity = cargoCapacity;
    }
    
    public void setHasAC(boolean hasAC) {
        this.hasAC = hasAC;
    }
    
    
    
    @Override
    public double calculateRent(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Rental days must be positive");
        }
       double base = super.getDailyRate() * days;
       double totalFee=base;
        if (cargoCapacity > 10) {
            totalFee += 30.0 * days; // Large van surcharge
        } else if (cargoCapacity > 5) {
            totalFee += 15.0 * days; // Medium van surcharge
        }
        // Small vans (≤5 cubic meters) have no surcharge
        
        if (hasAC) {
            totalFee += 10.0 * days; // AC usage fee
        }
        double insurance = 25.0 * days; // insurance fee
        totalFee += insurance;
        
        return totalFee;
    }
     @Override
    public void displayInfo() {
    super.displayInfo();
    System.out.println("Cargo Capacity: " + cargoCapacity +" m³" + 
            ", AC: " + (hasAC ? "Yes" : "No"));
    }
    
}
 