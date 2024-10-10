package GreenHub;

import java.util.Scanner;

public class PPayPalStrategy implements PaymentStrategy {
    private String email;
    private String password;

    public PPayPalStrategy() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci l'email di PayPal: ");
        this.email = scanner.next();
        System.out.print("Inserisci la password di PayPal: ");
        this.password = scanner.next();
    }

    @Override
    public void pay(double amount) {
        System.out.println("Pagando " + amount + " tramite PayPal con l'email " + email);
        // Logica per eseguire il pagamento con PayPal
    }
}

