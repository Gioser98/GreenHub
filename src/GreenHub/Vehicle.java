package GreenHub;

import java.io.Serializable;
import java.util.ArrayList;

public class Vehicle implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String maker;
	private String model;
	private int type; // 0 Electric - 1 Hybrid - 2 Combustion
	private int capacity;
	private ChargingRate supportedRate;
	private Location location;
	private User owner;
	
	
	// Getter&Setter
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
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
		
	public int getCapacity() {
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
	
	
	// Constructors
	public Vehicle() {
	    // Costruttore vuoto
	}
	public Vehicle(int id, String maker, String model, int type, ChargingRate supportedRate, Location location) {
	    this.id = id;
	    this.maker = maker;
	    this.model = model;
	    this.type = type;
	    this.supportedRate = supportedRate;
	    this.location = location;
	}
	
	// Methods
	public String toString() {
		return maker + " " + model ;
	}
	
	public static void printAll(ArrayList<Vehicle> vehicleList) {
		int i = 1;
		for (Vehicle v : vehicleList) {
			System.out.println(i + ") " + v);
			i++;
		}
	}
	
	

}