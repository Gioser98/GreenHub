package GreenHub;

import java.io.Serializable;
import java.util.Scanner;


public class PCreditCardStrategy implements PaymentStrategy, Serializable {
    private String cardNumber;
    private String expiryDate;
    private String cvv;

    public PCreditCardStrategy() {
        Scanner scanner = new Scanner(System.in);

        // Validazione del numero di carta di credito
        do {
            System.out.print("Inserisci il numero della carta (16 cifre): ");
            this.cardNumber = scanner.next();
        } while (!isValidCardNumber(this.cardNumber));

        // Validazione della data di scadenza
        do {
            System.out.print("Inserisci la data di scadenza (MM/AA): ");
            this.expiryDate = scanner.next();
        } while (!isValidExpiryDate(this.expiryDate));

        // Validazione del CVV
        do {
            System.out.print("Inserisci il CVV (3 cifre): ");
            this.cvv = scanner.next();
        } while (!isValidCvv(this.cvv));
    }

    @Override
    public void pay(double amount) {
        System.out.println("Elaborazione pagamento...");
        simulatePaymentDelay();  // Simula un'attesa per il pagamento
        System.out.println("Pagamento di Euro " + String.format("%.2f", amount) + " approvato con la carta " + maskCardNumber(cardNumber));
    }

    // Validazione del numero della carta (verifica che siano 16 cifre)
    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber.matches("\\d{16}");
    }

    // Validazione del formato della data di scadenza (MM/AA)
    private boolean isValidExpiryDate(String expiryDate) {
        return expiryDate.matches("(0[1-9]|1[0-2])/\\d{2}");
    }

    // Validazione del CVV (verifica che siano 3 cifre)
    private boolean isValidCvv(String cvv) {
        return cvv.matches("\\d{3}");
    }

    // Mascheramento del numero di carta, mostrando solo le ultime 4 cifre
    private String maskCardNumber(String cardNumber) {
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }

    // Simula un'attesa per il pagamento (es. 2 secondi)
    private void simulatePaymentDelay() {
        try {
            Thread.sleep(2000);  // Attesa di 2 secondi per simulare il processo di pagamento
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


}
