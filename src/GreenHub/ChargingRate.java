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
	@Override
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

	// Metodo per creare un ChargingRate basato su una stringa
	public static ChargingRate fromString(String rateInfo) {
		// Supponiamo che rateInfo sia una stringa nel formato "ID,Power,Price"
		String[] parts = rateInfo.split(",");
		if (parts.length != 3) {
			throw new IllegalArgumentException("Formato non valido per ChargingRate");
		}
		int id = Integer.parseInt(parts[0].trim());
		int power = Integer.parseInt(parts[1].trim());
		double price = Double.parseDouble(parts[2].trim());
		return new ChargingRate(id, power, price);
	}
	
	public static void printAll1(ArrayList<ChargingRate> chargingRateList) {
        if (chargingRateList.isEmpty()) {
            System.out.println("Nessun ChargingRate disponibile.");
            return;
        }
        int i = 1;
        for (ChargingRate cr : chargingRateList) {
            System.out.println(i + ") ID: " + cr.getId() + ", Power: " + cr.getPower() + " kW, Price: " + cr.getPrice() + " €/kW");
            i++;
        }
    }
}
