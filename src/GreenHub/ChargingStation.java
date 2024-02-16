package GreenHub;

public class ChargingStation {

	private int id;
	private Location location;
	private int timeTable;
	private int availableRate;
	private boolean maintenance;
	
	// Getters&Setters
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

	// Constructors
	public ChargingStation() {
	    // Costruttore vuoto
	}
	public ChargingStation(int id, Location location, int timeTable, int availableRate, boolean maintenance) {
	    this.id = id;
	    this.location = location;
	    this.timeTable = timeTable;
	    this.availableRate = availableRate;
	    this.maintenance = maintenance;
	}

}