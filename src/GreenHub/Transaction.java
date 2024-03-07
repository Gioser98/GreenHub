package GreenHub;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

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
	
	static void payment(User currentUser, Vehicle currentVehicle, LocalTime currentTime,
			Charge newCharge, Transaction newTransaction, ArrayList<Transaction> transactionList, RewardSystem currentRewardSystem) {
		Scanner in = new Scanner(System.in);
		double chargeAmount = currentVehicle.getCapacity() * currentVehicle.getSupportedRate().getPrice();
		System.out.println("Il totale è " + chargeAmount + "€. Come vuoi pagare?");
		System.out.println("Inserisci 0 per contanti");
		System.out.println("Inserisci 1 per carta di credito");
		System.out.println("Inserisci 2 per carta di debito");
		System.out.print("Scelta: ");

		newTransaction.setType(in.nextInt());
		newTransaction.setCharge(newCharge);
		newTransaction.setAmount(chargeAmount);
		newTransaction.setTimestamp(new Time(currentTime.getHour(), currentTime.getMinute()));
		int maxID = 0;
		for (Transaction t : transactionList) {
			if (t.getId() > maxID) {
				maxID = t.getId();
			}
		}
		newTransaction.setId(maxID + 1);
		transactionList.add(newTransaction);

		int newPoints = (int) (chargeAmount * currentRewardSystem.getRechargeFactor());
		currentUser.increaseGPBalance(newPoints);
		System.out.println("Ricarica effettuata! Con questa ricarica hai guadagnato " + newPoints + " punti.");
	}

}