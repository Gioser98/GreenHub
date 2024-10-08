package GreenHub;

import java.io.Serializable;

public class Transaction implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private int id;
    private Time timestamp;
    private int type;
    private double amount;
    private Charge charge;
    private PaymentStrategy paymentStrategy;
    private User user; // Aggiunto
    private Vehicle vehicle; // Aggiunto

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
    public PaymentStrategy getPaymentStrategy() {
        return paymentStrategy;
    }
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }
    
    // Aggiunti i metodi setUser e setVehicle
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

    public Transaction(int id, Time timestamp, int type, double amount, Charge charge) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.amount = amount;
        this.charge = charge;
    }

    public Transaction(int id, Time timestamp, int type, double amount, Charge charge, PaymentStrategy paymentStrategy) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.amount = amount;
        this.charge = charge;
        this.paymentStrategy = paymentStrategy;
    }

    public Transaction(PaymentStrategy paymentStrategy){
        this.paymentStrategy = paymentStrategy;
    }

    // Methods
    public void processPayment(double amount) {
        if (paymentStrategy != null) {
            paymentStrategy.pay(amount);
        } else {
            System.out.println("Nessuna strategia di pagamento definita.");
        }
    }

    @Override
    public String toString() {
        return "Transaction{id=" + id + ", timestamp=" + timestamp + ", type=" + type + ", amount=" + amount + ", charge=" + charge + ", paymentStrategy=" + paymentStrategy + ", user=" + user + ", vehicle=" + vehicle + '}';
    }
}
