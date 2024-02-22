package GreenHub;

import java.io.Serializable;

public class Vehicle implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String maker;
	private String model;
	private int engineType; // 0 Electric - 1 Hybrid - 2 Combustion
	private ChargingRate supportedRate;
	private int capacity;
	private Location location;
	
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
	public int getEngineType() {
		return engineType;
	}
	public void setEngineType(int engineType) {
		this.engineType = engineType;
	}
	public ChargingRate getSupportedRate() {
		return supportedRate;
	}
	public void setSupportedRate(ChargingRate supportedRate) {
		this.supportedRate = supportedRate;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
	// Constructors
	public Vehicle() {
	    // Costruttore vuoto
	}
	public Vehicle(int id, String maker, String model, int engineType, ChargingRate supportedRate, int capacity, Location location) {
	    this.id = id;
	    this.maker = maker;
	    this.model = model;
	    this.engineType = engineType;
	    this.supportedRate = supportedRate;
	    this.capacity = capacity;
	    this.location = location;
	}
	
	// Methods
	public String toString() {
		return maker + " " + model ;
	}

}