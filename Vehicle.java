/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advancedproject1;
/**
 *
 * @author hp
 */
// vehicales abstract parent class 
    public abstract class Vehicle implements SortableByModel, Comparable<Vehicle> {
    protected  int vehicleId;
    protected  String model;
    protected  int year;
    protected  double dailyRate;
    protected  boolean isAvailable;
    
    // Constructor
     public Vehicle(int vehicleId, String model, double dailyRate, int year) {
        this.vehicleId = vehicleId;
        setModel(model);
        setYear(year);
        setDailyRate(dailyRate);
        this.isAvailable = true;
    }
     
     //Getters
    public double getDailyRate(){
        return dailyRate;
    }
    public int getVehicleId() {
        return vehicleId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
    
    public int getYear(){
        return year;
    }
    
     @Override
    public String getModel() {
        return model;
    }

   // Setters with exceptions
    public void setModel(String model) {
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Model cannot be null or empty");
        }
        this.model = model.trim();
    }
    
    public void setYear(int year) {
        int currentYear = java.time.Year.now().getValue();
        if (year < 1900 || year > currentYear + 1) {
            throw new IllegalArgumentException("Year must be between 1900 and " + (currentYear + 1));
        }
        this.year = year;
    }
    
    public void setDailyRate(double dailyRate) {
        if (dailyRate < 0) {
            throw new IllegalArgumentException("Daily rate cannot be negative");
        }
        this.dailyRate = dailyRate;
    }
    
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }
    
    public abstract double calculateRent(int days);
    
    // Implement Comparable interface to sort by model
    @Override
    public int compareTo(Vehicle other) {
        return this.model.compareToIgnoreCase(other.model);
    }
    
    public void displayInfo() {
        System.out.println("ID: " + vehicleId + 
                         ", Model: " + model + 
                         ", Year: " + year + 
                         ", Daily Rate: $" + dailyRate + 
                         ", Available: " + (isAvailable ? "Yes" : "No"));
    }
    }
