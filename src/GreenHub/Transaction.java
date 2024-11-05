package GreenHub;

import java.io.Serializable;

public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private Time timestamp;
	private double amount;
	private Charge charge;
	private PaymentStrategy paymentStrategy;
	private User user;
	private Vehicle vehicle;

	// Getter & Setter
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
	public PaymentStrategy getPaymentStrategy() {
		return paymentStrategy;
	}
	public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
		this.paymentStrategy = paymentStrategy;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	// Costruttori
	public Transaction() {
		// Costruttore vuoto
	}

	public Transaction(int id, Time timestamp, double amount, Charge charge, PaymentStrategy paymentStrategy, User user, Vehicle vehicle) {
		this.id = id;
		this.timestamp = timestamp;
		this.amount = amount;
		this.charge = charge;
		this.paymentStrategy = paymentStrategy;
		this.user = user;
		this.vehicle = vehicle;
	}

	// Methods
	public boolean processPayment() {
		if (paymentStrategy != null) {
			paymentStrategy.pay(amount);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "Transaction{id=" + id + ", timestamp=" + timestamp + ", amount=" + amount + ", charge=" + charge + ", paymentStrategy=" + paymentStrategy + ", user=" + user + ", vehicle=" + vehicle + '}';
	}
}