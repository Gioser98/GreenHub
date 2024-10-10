package GreenHub;

import java.util.Scanner;

public class PCreditCardStrategy implements PaymentStrategy {
    private String cardNumber;
    private String expiryDate;
    private String cvv;

    public PCreditCardStrategy() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci il numero della carta: ");
        this.cardNumber = scanner.next();
        System.out.print("Inserisci la data di scadenza (MM/AA): ");
        this.expiryDate = scanner.next();
        System.out.print("Inserisci il CVV: ");
        this.cvv = scanner.next();
    }

    @Override
    public void pay(double amount) {
        System.out.println("Pagando " + amount + " con la carta di credito " + cardNumber);
        // Logica per eseguire il pagamento con la carta di credito
    }
}

