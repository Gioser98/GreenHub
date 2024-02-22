package GreenHub;

import java.io.Serializable;
import java.util.*;

public class Map implements Serializable {
	
	private static final long serialVersionUID = 1L;
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