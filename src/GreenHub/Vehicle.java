package GreenHub;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Vehicle implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private int id;
    private String maker;
    private String model;
	
    private double capacity; // Capacity in kWh
    private ChargingRate supportedRate;
    private Location location;
    private User owner;
    private double batteryLevel; // Change to double for decimal values

    // Getter & Setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getMaker() {
        return maker;
    }
    public void setMaker(String maker) {
        this.maker = maker;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public double getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public ChargingRate getSupportedRate() {
        return supportedRate;
    }
    public void setSupportedRate(ChargingRate supportedRate) {
        this.supportedRate = supportedRate;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
    public double getBatteryLevel() {
        return batteryLevel;
    }
    public void setBatteryLevel(double batteryLevel) {
        // Ensure the battery level is within bounds
        if (batteryLevel < 0) {
            this.batteryLevel = 0;
        } else if (batteryLevel > capacity) {
            this.batteryLevel = capacity;
        } else {
            this.batteryLevel = batteryLevel;
        }
    }
    
    // Constructors
    public Vehicle() {
        // Default constructor
    }
    
    public Vehicle(int id, String maker, String model, ChargingRate supportedRate, Location location, int capacity) {
        this.id = id;
        this.maker = maker;
        this.model = model;
        this.supportedRate = supportedRate;
        this.location = location;
        this.capacity = capacity;
        this.batteryLevel = generateRandomBatteryLevel(); // Set random battery level
    }
    
    // Method to generate a random battery level based on capacity
    public double generateRandomBatteryLevel() {
        Random random = new Random();
        return random.nextDouble() * capacity; // Generates a value between 0 and capacity
    }
    
    // Convert battery level to percentage
    public double getBatteryPercentage() {
        return (batteryLevel / capacity) * 100; // Return percentage
    }
    
    // Method to recharge the battery
    public void rechargeBattery(double amount) {
        if (amount < 0) {
            System.out.println("L'importo di ricarica non può essere negativo.");
            return;
        }
        setBatteryLevel(batteryLevel + amount); // Update battery level
        System.out.println("Batteria ricaricata di " + amount + " kWh. Livello attuale: " + batteryLevel + " kWh (" + getBatteryPercentage() + "%).");
    }
    
    // Methods
    public String toString() {
        return maker + " " + model + " (Battery Level: " + getBatteryPercentage() + "%)"; // Use percentage
    }
    
    public static void printAll(ArrayList<Vehicle> vehicleList) {
        int i = 1;
        for (Vehicle v : vehicleList) {
            System.out.println(i + ") " + v);
            i++;
        }
    }
}
