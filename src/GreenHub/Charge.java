package GreenHub;

import java.io.Serializable;
import java.time.LocalTime;

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
	
	// Methods
	public String toString() {
		return "totti";
	}
	
	public static void recharge(User currentUser, Vehicle currentVehicle, ChargingStation currentCS,
			LocalTime currentTime, Charge newCharge, Time startTime) {
		newCharge.setChargingStation(currentCS);
		newCharge.setVehicle(currentVehicle);
		newCharge.setChargingRate(currentVehicle.getSupportedRate());
		newCharge.setUser(currentUser);
		newCharge.setEnergy(currentVehicle.getCapacity());
		newCharge.setId(0);

		startTime.setHour(currentTime.getHour());
		startTime.setMinute(currentTime.getMinute());
		float timeToCharge = currentVehicle.getCapacity() / currentVehicle.getSupportedRate().getPower();
		int hour = (int) timeToCharge;
		int minute = (int) (timeToCharge - hour * 60);
		int endHour = startTime.getHour() + hour;
		int endMinute = startTime.getMinute() + minute;
		if (endMinute > 59) {
			endHour = endHour + 1;
			endMinute = endMinute - 60;
		}
		newCharge.setStartTime(startTime);
		newCharge.setEndTime(new Time(endHour, endMinute));
	}
}