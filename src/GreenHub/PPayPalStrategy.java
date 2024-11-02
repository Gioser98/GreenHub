package GreenHub;

import java.io.Serializable;
import java.util.Scanner;

public class PPayPalStrategy implements PaymentStrategy, Serializable {
    private String email;
    private String password;
    private final PaymentService paymentService = new PaymentService();

    public void initializePayment() {
        Scanner scanner = new Scanner(System.in);

        // Validazione dell'email per PayPal
        do {
            System.out.print("Inserisci l'email di PayPal: ");
            this.email = scanner.next();
        } while (!paymentService.isValidEmail(this.email));

         // Acquisizione della password con controllo della lunghezza
         do {
            System.out.print("Inserisci la password di PayPal: ");
            this.password = scanner.next();
            if (!paymentService.isValidPassword(this.password)) {
                System.out.println("Errore: la password deve essere di almeno 8 caratteri.");
            }
        } while (!paymentService.isValidPassword(this.password));
        
    }

    @Override
    public void pay(double amount) {
        System.out.println("Elaborazione pagamento tramite PayPal...");
        paymentService.simulatePaymentDelay();
        System.out.println("Pagamento di Euro " + String.format("%.2f", amount) + " approvato tramite PayPal (email: " + paymentService.maskEmail(email) + ")");
    }
}
