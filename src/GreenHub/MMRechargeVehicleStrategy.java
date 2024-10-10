package GreenHub;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MMRechargeVehicleStrategy implements MainMenuStrategy {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void execute(UserInterface ui, User user) throws IOException {
        // Ottieni il veicolo personale dell'utente
        Vehicle currentVehicle = user.getPersonalVehicle();

        // Mostra il nome del veicolo all'utente e chiedi conferma
        System.out.println("Stai per ricaricare il veicolo: " 
        + (user.getPersonalVehicle().getMaker() != null ? user.getPersonalVehicle().getMaker() : "Produttore sconosciuto") + " " 
        + (user.getPersonalVehicle().getModel() != null ? user.getPersonalVehicle().getModel() : "Modello sconosciuto") 
        + ". Vuoi continuare? (s/n)");
    
        String confirmation = scanner.next();

        // Se l'utente non conferma, annulla l'operazione
        if (!confirmation.equalsIgnoreCase("s")) {
            System.out.println("Operazione annullata.");
            return;
        }

        // Procedi con la selezione della stazione di ricarica
        List<ChargingStation> chargingStationList = ui.getController().getChargingStationList();  
        ChargingStation.getNearAvailableStation(user, chargingStationList);  
        ChargingStation currentCS = ChargingStation.chooseStation(currentVehicle, chargingStationList);  
        
        // Imposta l'ora corrente e crea una nuova carica
        LocalDateTime currentTime = LocalDateTime.now();  
        Charge newCharge = new Charge(currentCS, currentVehicle, currentTime);
    
        Time startTime = new Time(currentTime.getHour(), currentTime.getMinute());
    
        // Scegli il metodo di pagamento
        PaymentStrategy paymentStrategy = choosePaymentMethod();

        if (paymentStrategy == null) {
            System.out.println("Operazione annullata: nessun metodo di pagamento selezionato.");
            return;
        }
    
        // Registra la carica
        ui.getController().registerCharge(user, currentVehicle, currentCS, currentTime, newCharge, startTime);
    
        // Calcola il costo della carica
        double amount = newCharge.getCost();
    
        // Registra la transazione
        String result = ui.getController().registerTransaction(user, currentVehicle, currentCS, newCharge, amount, paymentStrategy);
    
        // Mostra il risultato della registrazione della transazione
        System.out.println(result);

         /// **Assegnazione Green Points per la ricarica**
        GreenPointsStrategy gpStrategy = new GPRechargeStrategy();  // Usa la strategia di ricarica
        int chargePercentage = (int) amount;  // Supponiamo che sia la percentuale di ricarica

        // Calcola i punti verdi usando la strategia
        int greenPoints = gpStrategy.calculatePoints(chargePercentage);

        // Usa il metodo del controller per assegnare i punti
        ui.getController().assignGreenPoints(user, gpStrategy, chargePercentage);

        // Stampa il messaggio con i punti verdi calcolati
        System.out.println("Ricarica completata. Hai guadagnato " + greenPoints + " Green Points!");
        
        try {
			ui.getController().saveAll();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private PaymentStrategy choosePaymentMethod() {
        System.out.println("Scegli un metodo di pagamento:");
        System.out.println("1. Carta di credito");
        System.out.println("2. PayPal");
        
        int choice = scanner.nextInt();
        PaymentStrategy paymentStrategy = null;

        switch (choice) {
            case 1:
                paymentStrategy = new PCreditCardStrategy();  // Istanzia la strategia di carta di credito
                break;
            case 2:
                paymentStrategy = new PPayPalStrategy();  // Istanzia la strategia di PayPal
                break;
            default:
                System.out.println("Scelta non valida. Riprova.");
        }
        return paymentStrategy;
    }
}
