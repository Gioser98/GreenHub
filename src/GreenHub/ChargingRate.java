package GreenHub;

import java.io.Serializable;
import java.util.ArrayList;

public class ChargingRate implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private int power;
	private double price;

	// Getter&Setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	// Constructors
	public ChargingRate() {
	    // Costruttore vuoto
	}
	public ChargingRate(int id, int power, double price) {
	    this.id = id;
	    this.power = power;
	    this.price = price;
	}
	
	// Methods
	
	public String toString() {
		return power + " kW a " + price + " €/kW (ID:" + id + ")";
	}
	
	public static void printAll (ArrayList<ChargingRate> chargingRateList) {
		int i = 1;
		for (ChargingRate cr : chargingRateList) {
			System.out.println(i + ") " + cr);
			i++;
		}
	}
}