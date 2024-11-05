package GreenHub;

public interface PaymentStrategy {
	void initializePayment();
	void pay(double amount);   
}