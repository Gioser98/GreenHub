package GreenHub;

public class RewardList {

	private String name;
	private String description;
	private int greenPointsCost;
	private int remainingQuantity;
	
	// Getter&Setter
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getGreenPointsCost() {
		return greenPointsCost;
	}
	public void setGreenPointsCost(int greenPointsCost) {
		this.greenPointsCost = greenPointsCost;
	}
	public int getRemainingQuantity() {
		return remainingQuantity;
	}
	public void setRemainingQuantity(int remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}
	
	// Constructors
	public RewardList() {
	    // Costruttore vuoto
	}
	public RewardList(String name, String description, int greenPointsCost, int remainingQuantity) {
	    this.name = name;
	    this.description = description;
	    this.greenPointsCost = greenPointsCost;
	    this.remainingQuantity = remainingQuantity;
	}

}