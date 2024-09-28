package GreenHub;

import java.io.Serializable;
import java.util.ArrayList;

public class Reservation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private User user;
	private Vehicle vehicle;
	private ChargingStation chargingStation;
	private Time startTime;
	private Time endTime;
	
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
	
	// Constructors
	public Reservation() {
	    // Costruttore vuoto
	}
	
	// Methods
	public String toString() {
		return "Prenotazione di " + user + " con il veicolo " + vehicle + "presso al stazione di ricarica "+ chargingStation + " inizia alle " + startTime + " e finisce alle " + endTime;
	}
	
	public static void reserveSlot(User currentUser, Vehicle currentVehicle, ChargingStation currentCS,
			int startingSlot, int endingSlot, boolean slotAvailable, ArrayList<Reservation> reservationList) {
		Reservation newReservation;
		if (slotAvailable) {
			for (int j = startingSlot; j < endingSlot; j++) {
				currentCS.setTimeTable(currentUser.getUsername(), j);
			}
			System.out.println("Slot prenotati!");
			newReservation = new Reservation();
			newReservation.setUser(currentUser);
			newReservation.setVehicle(currentVehicle);
			newReservation.setChargingStation(currentCS);
			newReservation.setStartTime(new Time(startingSlot * 30 / 60, endingSlot * 30 % 60));
			int maxID = 0;
			for (Reservation r : reservationList) {
				if (r.getId() > maxID) {
					maxID = r.getId();
				}
			}
			newReservation.setId(maxID + 1);
			reservationList.add(newReservation);
			
		} else {
			System.out.println("Slot non disponibili!");
		}
	}

}