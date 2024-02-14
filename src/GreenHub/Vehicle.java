package GreenHub;

public class Vehicle {

	private int id;
	private String maker;
	private String model;
	private int engineType;
	private ChargingRate supportedRate;
	private int capacity;
	private Location location;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getEngineType() {
		return engineType;
	}
	public void setEngineType(int engineType) {
		this.engineType = engineType;
	}
	public ChargingRate getSupportedRate() {
		return supportedRate;
	}
	public void setSupportedRate(ChargingRate supportedRate) {
		this.supportedRate = supportedRate;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}

}