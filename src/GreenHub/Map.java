package GreenHub;

import java.util.*;

public class Map {

	private int id;
	private Collection<ArrayList<ChargingStation>> chargingStations;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Collection<ArrayList<ChargingStation>> getChargingStations() {
		return chargingStations;
	}
	public void setChargingStations(Collection<ArrayList<ChargingStation>> chargingStations) {
		this.chargingStations = chargingStations;
	}

}