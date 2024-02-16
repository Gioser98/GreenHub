package GreenHub;

public class RewardSystem {

	private int rechargeFactor;
	private int rentFactor;
	
	// Getter&Setter
	public int getRechargeFactor() {
		return rechargeFactor;
	}
	public void setRechargeFactor(int rechargeFactor) {
		this.rechargeFactor = rechargeFactor;
	}
	public int getRentFactor() {
		return rentFactor;
	}
	public void setRentFactor(int rentFactor) {
		this.rentFactor = rentFactor;
	}
	
	// Constructors
	public RewardSystem() {
	    // Costruttore vuoto
	}
	public RewardSystem(int rechargeFactor,int rentFactor) {
	    this.rechargeFactor = rechargeFactor;
	    this.rentFactor = rentFactor;
	}

}