package GreenHub;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Charge implements Serializable {

	private static final long serialVersionUID = 1L;
	private static int nextId = 1; // Contatore statico per l'ID delle Charge
	private int id;
	private User user;
	private Vehicle vehicle;
	private ChargingStation chargingStation;
	private Time startTime;
	private Time endTime;
	private ChargingRate chargingRate;
	private double cost;

	// Getter & Setter
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getId() {
		return id;
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

	public ChargingRate getChargingRate() {
		return chargingRate;
	}
	public void setChargingRate(ChargingRate chargingRate) {
		this.chargingRate = chargingRate;
	}

	// Constructors
	public Charge() {
		this.id = nextId++;
	}

	public Charge(User user, Vehicle vehicle, ChargingStation chargingStation, Time startTime, Time endTime, ChargingRate chargingRate) {
		this(); // Chiama il costruttore vuoto per assegnare l'ID
		this.user = user;
		this.vehicle = vehicle;
		this.chargingStation = chargingStation;
		this.startTime = startTime;
		this.endTime = endTime;
		this.chargingRate = chargingRate;
	}

	public Charge(ChargingStation chargingStation, Vehicle vehicle, LocalDateTime startTime) {
		this(); // Chiama il costruttore vuoto per assegnare l'ID
		this.chargingStation = chargingStation;
		this.vehicle = vehicle;
		this.startTime = new Time(startTime.getHour(), startTime.getMinute());
	}

	// Methods
	@Override
	public String toString() {
		return "Charge{id=" + id + ", user=" + user + ", vehicle=" + vehicle + ", chargingStation=" + chargingStation + ", startTime=" + startTime + ", endTime=" + endTime + ", chargingRate=" + chargingRate + ", cost=" + cost + '}';
	}
}