package GreenHub;

import java.io.Serializable;
import java.util.regex.Pattern;

public class PaymentService implements Serializable {

    // ==============================
    // Validazioni
    // ==============================

    // Validazione del numero della carta di credito (16 cifre)
    public boolean isValidCardNumber(String cardNumber) {
        return cardNumber != null && cardNumber.matches("\\d{16}");
    }

    // Validazione della data di scadenza della carta di credito (formato MM/AA)
    public boolean isValidExpiryDate(String expiryDate) {
        return expiryDate != null && expiryDate.matches("(0[1-9]|1[0-2])/\\d{2}");
    }

    // Validazione del CVV della carta di credito (3 cifre)
    public boolean isValidCvv(String cvv) {
        return cvv != null && cvv.matches("\\d{3}");
    }

    // Validazione dell'email per PayPal
    public boolean isValidEmail(String email) {
        return email != null && Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email);
    }

    public boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }

    // ==============================
    // Mascheramento Dati Sensibili
    // ==============================

    // Mascheramento del numero di carta di credito, mostrando solo le ultime 4 cifre
    public String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) return "**** **** **** ****";
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }

    // Mascheramento dell'email, mostrando solo le prime 2 lettere e il dominio
    public String maskEmail(String email) {
        if (email == null || !email.contains("@")) return "****@****";
        int atIndex = email.indexOf('@');
        String maskedPart = email.substring(0, 2) + "****";
        return maskedPart + email.substring(atIndex);
    }

    // ==============================
    // Simulazione Ritardo Pagamento
    // ==============================

    // Simula un'attesa per il pagamento (default: 2 secondi)
    public void simulatePaymentDelay() {
        simulatePaymentDelay(2000); // Default a 2 secondi
    }

    // Simula un'attesa personalizzata per il pagamento
    public void simulatePaymentDelay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}