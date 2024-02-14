package GreenHub;

import java.util.*;

public class ChargingStation {

	Collection<Charge> delivers;
	private int id;
	private Location location;
	private int timeTable;
	private int availableRate;
	private boolean maintenance;
	public Collection<Charge> getDelivers() {
		return delivers;
	}
	public void setDelivers(Collection<Charge> delivers) {
		this.delivers = delivers;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public int getTimeTable() {
		return timeTable;
	}
	public void setTimeTable(int timeTable) {
		this.timeTable = timeTable;
	}
	public int getAvailableRate() {
		return availableRate;
	}
	public void setAvailableRate(int availableRate) {
		this.availableRate = availableRate;
	}
	public boolean isMaintenance() {
		return maintenance;
	}
	public void setMaintenance(boolean maintenance) {
		this.maintenance = maintenance;
	}

}