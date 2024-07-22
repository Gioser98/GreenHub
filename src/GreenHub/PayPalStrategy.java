package GreenHub;

public class PayPalPayment implements PaymentStrategy {
    private String email;
    private String password;

    public PayPalPayment(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public void pay(double amount) {
        // Logica di pagamento con PayPal
        System.out.println("Pagamento di " + amount + " effettuato con PayPal.");
    }
}
