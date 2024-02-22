package GreenHub;

import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String username;
	private int greenPointsBalance;
	private int type; // 0 EV - 1 ICE - 2 NoVehicle
	private String name;
	private String surname;
	private Location location;
	
	// Getter&Setter
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getGreenPointsBalance() {
		return greenPointsBalance;
	}
	public void setGreenPointsBalance(int greenPointsBalance) {
		this.greenPointsBalance = greenPointsBalance;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	
	// Constructors
	public User() {
	    // Costruttore vuoto
	}
	public User(String username, int greenPointsBalance, int type, String name, String surname, Location location) {
	    this.username = username;
	    this.greenPointsBalance = greenPointsBalance;
	    this.type = type;
	    this.name = name;
	    this.surname = surname;
	    this.location = location;
	}
	
	// Methods
	public String toString() {
		return username + ". Bilancio GP: " + greenPointsBalance;
	}

}