package GreenHub;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private int greenPointsBalance;
	//private int type; // 0 EV - 2 NoVehicle
	private String name;
	private String surname;
	private Location location;
	private Vehicle personalVehicle;
	private List<Reservation> reservations = new ArrayList<>();

	// Getter&Setter
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if(username == null || username.isEmpty()) {
			throw new IllegalArgumentException("Username cannot be null or empty");
		}
		this.username = username;
	}

	public int getGreenPointsBalance() {
		return greenPointsBalance;
	}

	public void setGreenPointsBalance(int greenPointsBalance) {
		if(greenPointsBalance < 0) {
			throw new IllegalArgumentException("Green points balance cannot be negative");
		}
		this.greenPointsBalance = greenPointsBalance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Vehicle getPersonalVehicle() {
		return personalVehicle;
	}

	public void setPersonalVehicle(Vehicle personalVehicle) {
		this.personalVehicle = personalVehicle;
	}

	public void increaseGPBalance(int points) {
		this.greenPointsBalance += points;
	}

	public void decreaseGPBalance(int points) {
		this.greenPointsBalance -= points;
	}

	public List<Reservation> getReservations() {
		if (reservations == null) {
			reservations = new ArrayList<>();  // Inizializza la lista se è null
		}
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	// Constructors
	public User(String username, int greenPointsBalance,  String name, String surname, Location location) {
		this.username = username;
		this.greenPointsBalance = greenPointsBalance;
		//this.type = type;
		this.name = name;
		this.surname = surname;
		this.location = location;
		this.reservations = new ArrayList<>();  // Inizializza la lista di prenotazioni
	}

	// Methods
	@Override
	public String toString() {
		return username + ". Bilancio GP: " + greenPointsBalance;
	}

	public static void printAll(ArrayList<User> userList) {
		int i = 1;
		for (User u : userList) {
			System.out.println(i + ") " + u);
			i++;
		}
	}
}