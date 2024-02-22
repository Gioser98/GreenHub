package GreenHub;

import java.io.Serializable;
import java.time.*;

public class Reservation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private User user;
	private Vehicle vehicle;
	private ChargingStation chargingStation;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	
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
	
	// Constructors
	public Reservation() {
	    // Costruttore vuoto
	}
	public Reservation(int id, User user, Vehicle vehicle, ChargingStation chargingStation, LocalDateTime startTime, LocalDateTime endTime) {
	    this.id = id;
	    this.user = user;
	    this.vehicle = vehicle;
	    this.chargingStation = chargingStation;
	    this.startTime = startTime;
	    this.endTime = endTime;
	}
	
	// Methods
	public String toString() {
		return "Prenotazione di " + user + " con il veicolo " + vehicle + "presso al stazione di ricarica "+ chargingStation + " inizia alle " + startTime + " e finisce alle " + endTime;
	}

}