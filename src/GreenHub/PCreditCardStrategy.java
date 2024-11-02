package GreenHub;

import java.io.Serializable;
import java.util.Scanner;

public class PCreditCardStrategy implements PaymentStrategy, Serializable {
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private final PaymentService paymentService = new PaymentService();

    public void initializePayment() {
        Scanner scanner = new Scanner(System.in);

        // Validazione del numero della carta di credito
        do {
            System.out.print("Inserisci il numero della carta (16 cifre): ");
            this.cardNumber = scanner.next();
        } while (!paymentService.isValidCardNumber(this.cardNumber));

        // Validazione della data di scadenza
        do {
            System.out.print("Inserisci la data di scadenza (MM/AA): ");
            this.expiryDate = scanner.next();
        } while (!paymentService.isValidExpiryDate(this.expiryDate));

        // Validazione del CVV
        do {
            System.out.print("Inserisci il CVV (3 cifre): ");
            this.cvv = scanner.next();
        } while (!paymentService.isValidCvv(this.cvv));
    }

    @Override
    public void pay(double amount) {
        System.out.println("Elaborazione pagamento...");
        paymentService.simulatePaymentDelay();
        System.out.println("Pagamento di Euro " + String.format("%.2f", amount) + " approvato con la carta " + paymentService.maskCardNumber(cardNumber));
    }
}
