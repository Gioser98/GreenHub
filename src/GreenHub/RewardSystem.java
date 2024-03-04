package GreenHub;

import java.io.Serializable;

public class RewardSystem implements Serializable {

	private static final long serialVersionUID = 1L;
	private float rechargeFactor;
	private float rentFactor;
	
	// Getter&Setter
	public float getRechargeFactor() {
		return rechargeFactor;
	}
	public void setRechargeFactor(float rechargeFactor) {
		this.rechargeFactor = rechargeFactor;
	}
	public float getRentFactor() {
		return rentFactor;
	}
	public void setRentFactor(float rentFactor) {
		this.rentFactor = rentFactor;
	}
	
	// Constructors
	public RewardSystem() {
	}
	
	public void chargePointsIncrease (int value) {
		float increase = 1 + value/10;
		this.rechargeFactor = this.rechargeFactor * increase;
	}

}