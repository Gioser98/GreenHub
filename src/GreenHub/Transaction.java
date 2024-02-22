package GreenHub;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Transaction implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private LocalDateTime timestamp;
	private int type;
	private float amount;
	private Charge charge;
	
	// Getter&Setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
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
	public Transaction(int id, LocalDateTime timestamp, int type, float amount, Charge charge) {
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