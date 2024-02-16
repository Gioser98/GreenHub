package GreenHub;

import java.util.*;

public class Map {

	private int id;
	private ArrayList<ChargingStation> chargingStations;
	
	// Getter&Setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<ChargingStation> getChargingStations() {
		return chargingStations;
	}
	public void setChargingStations(ArrayList<ChargingStation> chargingStations) {
		this.chargingStations = chargingStations;
	}
	
	// Contructors
	public Map() {
	    // Costruttore vuoto
	}
	public Map(int id, ArrayList<ChargingStation> chargingStations) {
	    this.id = id;
	    this.chargingStations = chargingStations;
	}

}