package GreenHub;

import java.io.Serializable;
import java.time.*;

public class Charge implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private User user;
	private Vehicle vehicle;
	private ChargingStation chargingStation;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private int energy;
	private ChargingRate ChargingRate;
	
	// Getter&Setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public ChargingStation getChargingStation() {
		return chargingStation;
	}
	public void setChargingStation(ChargingStation chargingStation) {
		this.chargingStation = chargingStation;
	}
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	public int getEnergy() {
		return energy;
	}
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	public ChargingRate getChargingRate() {
		return ChargingRate;
	}
	public void setChargingRate(ChargingRate chargingRate) {
		ChargingRate = chargingRate;
	}
	
	// Constructors
	public Charge() {
		    // Costruttore vuoto
	}
	
	public Charge(int id, User user, Vehicle vehicle, ChargingStation chargingStation, LocalDateTime startTime, 
			LocalDateTime endTime, int energy, ChargingRate ChargingRate) {
		
		this.id = id;
		this.user = user;
		this.vehicle = vehicle;
		this.chargingStation = chargingStation;
		this.startTime = startTime;
		this.endTime = endTime;
		this.energy = energy;
		this.ChargingRate = ChargingRate;
	}
	
	// Methods
	public String toString() {
		return "totti";
	}
}