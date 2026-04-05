/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advancedproject1;
/**
 *
 * @author hp
 */
public class Bike extends Vehicle {
    
    private int engineCC;
    private boolean isElectric;
    
    
    // Bike constructor
    public Bike(int vehicleId, String model, double dailyRate, int year, int engineCC, boolean isElectric){
        super(vehicleId, model, dailyRate, year);
        this.isElectric = isElectric; // Set electric status first
        setEngineCC(engineCC); // Then validate engineCC based on electric status
    }
    
    // Getters
    public int getEngineCC() {
        return engineCC;
    }
    
    public boolean isElectric() {
        return isElectric;
    }
    
    
    // Setters 
    public void setEngineCC(int engineCC) {
        if (engineCC < 0) {
            throw new IllegalArgumentException("Engine CC cannot be negative");
        }
        if (engineCC > 2000) {
            throw new IllegalArgumentException("Engine CC cannot exceed 2000cc for bikes");
        }
        // Check if non-electric bike has valid engine
        if (!isElectric && engineCC == 0) {
            throw new IllegalArgumentException("Non-electric bikes must have an engine CC > 0");
        }
        this.engineCC = engineCC;
    }
    
    public void setElectric(boolean electric) {
        // If setting to electric and engineCC is 0, that's fine
        // But if electric is false and engineCC is 0, that's an issue
        if (!electric && engineCC == 0) {
            throw new IllegalArgumentException("Non-electric bikes must have an engine CC > 0");
        }
        this.isElectric = electric;
    }
    
    
    @Override
    public double calculateRent(int days) {
        double base = super.getDailyRate() * days;
        if (days <= 0) {
            throw new IllegalArgumentException("Rental days must be positive");
        }
        double totalFee = base;
        if (engineCC > 0) {
            if (engineCC <= 125) {
                totalFee += 5.0; // Small engine
            } else if (engineCC <= 500) {
                totalFee += 15.0; // Medium engine
            } else {
                totalFee += 25.0; // Large engine
            }
        }
        if (isElectric) {
            totalFee += 20.0; // Charging setup fee
        }
        double insurance = 10.0 * days; // insurance fee
        totalFee += insurance;
        
        return totalFee;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Engine: " + (engineCC > 0 ? engineCC + "cc" : "N/A") +
                ", Electric: " + (isElectric ? "Yes" : "No")); 
    }
}
