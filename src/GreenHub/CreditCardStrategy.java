package GreenHub;

public class CreditCardStrategy implements PaymentStrategy {
    private String cardNumber;
    private String expiryDate;
    private String cvv;

    public CreditCardStrategy(String cardNumber, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public void pay(double amount) {
        // Logica di pagamento con carta di credito
        System.out.println("Pagamento di " + amount + " effettuato con carta di credito.");
    }
}
