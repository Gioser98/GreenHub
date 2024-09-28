package GreenHub;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Charge implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private User user;
	private Vehicle vehicle;
	private ChargingStation chargingStation;
	private Time startTime;
	private Time endTime;
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
	public Time getStartTime() {
		return startTime;
	}
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	public Time getEndTime() {
		return endTime;
	}
	public void setEndTime(Time endTime) {
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
	
	public Charge(int id, User user, Vehicle vehicle, ChargingStation chargingStation, Time startTime, 
			Time endTime, int energy, ChargingRate ChargingRate) {
		
		this.id = id;
		this.user = user;
		this.vehicle = vehicle;
		this.chargingStation = chargingStation;
		this.startTime = startTime;
		this.endTime = endTime;
		this.energy = energy;
		this.ChargingRate = ChargingRate;
	}
	/* 
	public Charge(ChargingStation chargingStation, Vehicle vehicle, Time startTime) {
		this.chargingStation = chargingStation;
		this.vehicle = vehicle;
		this.startTime = startTime;
	}
	*/
	
	public Charge(ChargingStation chargingStation, Vehicle vehicle, LocalDateTime startTime) {
    this.chargingStation = chargingStation;
    this.vehicle = vehicle;
    this.startTime = new Time(startTime.getHour(), startTime.getMinute());  // Conversione da LocalDateTime a Time
}

	
	
	// Methods
	public String toString() {
		return "totti";
	}
}