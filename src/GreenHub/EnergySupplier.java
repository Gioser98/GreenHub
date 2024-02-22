package GreenHub;

import java.io.Serializable;
import java.util.ArrayList;

public class EnergySupplier implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<ChargingStation> chargingStations;
	
	// Getters&Setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<ChargingStation> getChargingStations() {
		return chargingStations;
	}
	public void setChargingStations(ArrayList<ChargingStation> chargingStations) {
		this.chargingStations = chargingStations;
	}
	
	// Constructors
	public EnergySupplier() {
	    // Costruttore vuoto
	}
	public EnergySupplier(String name) {
	    this.name = name;
        this.chargingStations = new ArrayList<>();
	}
	
	// Methods
	public String toString() {
		return name;
	}
	
	// Aggiungi una colonnina alla lista di colonnine in possesso del fornitore
    public void addChargingStation(ChargingStation chargingStation) {
        chargingStations.add(chargingStation);
    }

}