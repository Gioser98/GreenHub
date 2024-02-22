package GreenHub;

import java.io.Serializable;

public class Location implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private float latitude;
	private float longitude;
	
	// Getter&Setter
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	// Constructors
	public Location() {
	    // Costruttore vuoto
	}
	public Location(float latitude, float longitude) {
	    this.latitude = latitude;
	    this.longitude = longitude;
	}
	
	// Methods	
	public int distance(Location loc1, Location loc2) {
		return (int) Math.sqrt(Math.pow((loc1.latitude - loc2.latitude), 2) + Math.pow((loc1.longitude - loc2.longitude), 2));
	}
	
	public int distance(Location loc1) {
		return (int) Math.sqrt(Math.pow((loc1.latitude - latitude), 2) + Math.pow((loc1.longitude - longitude), 2));
	}
}