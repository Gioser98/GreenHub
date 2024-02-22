package GreenHub;

import java.io.Serializable;

public class Reward implements Serializable {
	
	private static final long serialVersionUID = 1L;
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
	public Reward() {
	    // Costruttore vuoto
	}
	public Reward(String name, String description, int greenPointsCost, int remainingQuantity) {
	    this.name = name;
	    this.description = description;
	    this.greenPointsCost = greenPointsCost;
	    this.remainingQuantity = remainingQuantity;
	}
	
	// Methods
	
	/**
	 * Restituisce la ricompensa in un formato leggibile.
	 */
	public String toString() {
		return name + ": " + description + ". Costo in GP: " + greenPointsCost + ". Quantità rimanente: " + remainingQuantity;
	}
}