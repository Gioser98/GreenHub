package GreenHub;

import java.io.Serializable;
import java.util.Scanner;
import java.util.regex.Pattern;

public class PPayPalStrategy implements PaymentStrategy, Serializable {
    private String email;
    private String password;

    public PPayPalStrategy() {
        Scanner scanner = new Scanner(System.in);

        // Validazione dell'email
        do {
            System.out.print("Inserisci l'email di PayPal: ");
            this.email = scanner.next();
        } while (!isValidEmail(this.email));

        // Acquisizione della password (simulazione, nessuna validazione)
        System.out.print("Inserisci la password di PayPal: ");
        this.password = scanner.next();
    }

    @Override
    public void pay(double amount) {
        
        System.out.println("Elaborazione pagamento tramite PayPal...");
        simulatePaymentDelay();  // Simula un'attesa per il pagamento
        
        System.out.println("Pagamento di Euro " + String.format("%.2f", amount) + " approvato tramite PayPal (email: " + maskEmail(email) + ")");
        
    }
  

    // Validazione dell'email con espressione regolare
    private boolean isValidEmail(String email) {
        return Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email);
    }

    // Mascheramento dell'email, mostrando solo le prime 2 lettere e il dominio
    private String maskEmail(String email) {
        int atIndex = email.indexOf('@');
        String maskedPart = email.substring(0, 2) + "****";
        return maskedPart + email.substring(atIndex);
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
