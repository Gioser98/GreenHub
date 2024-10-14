package GreenHub;

import java.io.Serializable;

public class Location implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int latitude;
	private int longitude;
	
	// Getter&Setter
	public int getLatitude() {
		return latitude;
	}
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	public int getLongitude() {
		return longitude;
	}
	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	
	

	// Constructors
	public Location(int latitude, int longitude) {
	    this.latitude = latitude;
	    this.longitude = longitude;
	}
	
	public Location() {
		//
	}
	
	// Methods	
	public int distance(Location loc1, Location loc2) {
		return (int) Math.sqrt(Math.pow((loc1.latitude - loc2.latitude), 2) + Math.pow((loc1.longitude - loc2.longitude), 2));
	}
	
	public int distance(Location loc1) {
		return (int) Math.sqrt(Math.pow((loc1.latitude - latitude), 2) + Math.pow((loc1.longitude - longitude), 2));
	}
	
	public String toString() {
		return "(" + latitude + "," + longitude + ")";
	}
}

