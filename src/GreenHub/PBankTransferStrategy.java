package GreenHub;

import java.io.Serializable;
import java.util.Scanner;

public class PBankTransferStrategy implements PaymentStrategy, Serializable {
    private String accountNumber;
    private String accountHolder;
    private final PaymentService paymentService = new PaymentService();

    @Override
    public void initializePayment() {
        Scanner scanner = new Scanner(System.in);

        // Validazione del numero del conto o IBAN
        do {
            System.out.print("Inserisci il numero del conto o IBAN: ");
            this.accountNumber = scanner.next();
        } while (!paymentService.isValidAccountNumber(this.accountNumber));

        // Inserimento del nome del titolare del conto
        System.out.print("Inserisci il nome del titolare del conto: ");
        this.accountHolder = scanner.next();
    }

    @Override
    public void pay(double amount) {
        System.out.println("Elaborazione pagamento tramite bonifico bancario...");
        paymentService.simulatePaymentDelay();
        System.out.println("Pagamento di Euro " + String.format("%.2f", amount) + " approvato verso il conto " + paymentService.maskAccountNumber(accountNumber) + " intestato a " + accountHolder);
    }
}
