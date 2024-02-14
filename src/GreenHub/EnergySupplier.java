package GreenHub;

import java.util.ArrayList;

public class EnergySupplier {

	private String name;
	private ArrayList<ChargingStation> chargingStations;
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

}