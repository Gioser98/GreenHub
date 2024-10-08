package GreenHub;


import java.io.IOException;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MMRechargeVehicleStrategy implements MainMenuStrategy {
    
  

    Scanner scanner = new Scanner(System.in);
    

    @Override
    public void execute(UserInterface ui, User user) throws IOException {
        Vehicle currentVehicle = user.getPersonalVehicle();
    
        List<ChargingStation> chargingStationList = ui.getController().getChargingStationList();  
        ChargingStation.getNearAvailableStation(user, chargingStationList);  
        ChargingStation currentCS = ChargingStation.chooseStation(currentVehicle, chargingStationList);  
    
        LocalDateTime currentTime = LocalDateTime.now();  
        Charge newCharge = new Charge(currentCS, currentVehicle, currentTime);
    
        Time startTime = new Time(currentTime.getHour(), currentTime.getMinute());
    
        PaymentStrategy paymentStrategy = choosePaymentMethod();
    
        if (paymentStrategy == null) {
            System.out.println("Operazione annullata: nessun metodo di pagamento selezionato.");
            return;
        }
    
        // Registra la carica
        ui.getController().registerCharge(user, currentVehicle, currentCS, currentTime, newCharge, startTime);
    
        // Calcola il costo della carica
        double amount = newCharge.getCost(); // Assicurati che Charge abbia un metodo per ottenere il costo
    
        // Registra la transazione
        int paymentType = (paymentStrategy instanceof PCreditCardStrategy) ? 1 : 2; // Esempio di tipo di pagamento
        String result = ui.getController().registerTransaction(user, currentVehicle, currentCS, newCharge, amount, paymentStrategy, paymentType);
    
        // Mostra il risultato della registrazione della transazione
        System.out.println(result);
    }
    
    

    private PaymentStrategy choosePaymentMethod() {
        
        
        
        System.out.println("Scegli un metodo di pagamento:");
        System.out.println("1. Carta di credito");
        System.out.println("2. PayPal");
        
        
        int choice = scanner.nextInt();
        PaymentStrategy paymentStrategy = null;

        switch (choice) {
            case 1:
                // Richiedi i dettagli della carta di credito
                System.out.print("Inserisci il numero della carta: ");
                String cardNumber = scanner.next();
                System.out.print("Inserisci la data di scadenza (MM/AA): ");
                String expiryDate = scanner.next();
                System.out.print("Inserisci il CVV: ");
                String cvv = scanner.next();
                paymentStrategy = new PCreditCardStrategy(cardNumber, expiryDate, cvv);
                break;
            case 2:
                // Richiedi i dettagli di PayPal
                System.out.print("Inserisci l'email di PayPal: ");
                String email = scanner.next();
                System.out.print("Inserisci la password di PayPal: ");
                String password = scanner.next();
                paymentStrategy = new PPayPalStrategy(email, password);
                break;
            default:
                System.out.println("Scelta non valida. Riprova.");
        }
        return paymentStrategy;
    }
}
