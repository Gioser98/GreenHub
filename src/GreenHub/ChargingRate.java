package GreenHub;

public class ChargingRate {

	private int id;
	private int power;
	private float price;

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
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	// Constructors
	public ChargingRate() {
	    // Costruttore vuoto
	}
	public ChargingRate(int id, int power, float price) {
	    this.id = id;
	    this.power = power;
	    this.price = price;
	}
}