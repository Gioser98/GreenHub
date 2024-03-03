package GreenHub;

import java.io.Serializable;

public class Transaction implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private Time timestamp;
	private int type;
	private double amount;
	private Charge charge;
	
	// Getter&Setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Time getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Time timestamp) {
		this.timestamp = timestamp;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Charge getCharge() {
		return charge;
	}
	public void setCharge(Charge charge) {
		this.charge = charge;
	}
	
	// Constructors
	public Transaction() {
	    // Costruttore vuoto
	}
	
	public Transaction(int id, Time timestamp, int type, float amount, Charge charge) {
	    this.id = id;
	    this.timestamp = timestamp;
	    this.type = type;
	    this.amount = amount;
	    this.charge = charge;
	}
	
	// Methods
	public String toString() {
		return "totti";
	}

}