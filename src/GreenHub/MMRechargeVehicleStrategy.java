package GreenHub;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MMRechargeVehicleStrategy implements MainMenuStrategy {
    Scanner scanner = new Scanner(System.in);
    // Mappa per i metodi di pagamento
    private final Map<String, PaymentStrategy> paymentOptions;
    private GreenPointsStrategy greenPointsStrategy;

    public MMRechargeVehicleStrategy(GreenPointsStrategy greenPointsStrategy) {
        this.greenPointsStrategy = greenPointsStrategy;
        this.paymentOptions = new HashMap<>(); // Inizializza la mappa
        paymentOptions.put("Carta di credito", new PCreditCardStrategy()); // Carta di credito
        paymentOptions.put("PayPal", new PPayPalStrategy());
    }
    

    @Override
    public void execute(UserInterface ui, User user) throws IOException {
        Vehicle currentVehicle = user.getPersonalVehicle();
    
        // Mostra il nome del veicolo e chiedi conferma
        ui.getView().showMessage("Stai per ricaricare il veicolo: " 
            + (currentVehicle.getMaker() != null ? currentVehicle.getMaker() : "Produttore sconosciuto") + " " 
            + (currentVehicle.getModel() != null ? currentVehicle.getModel() : "Modello sconosciuto") 
            + ". Vuoi continuare? (s/n)");
    
        String confirmation = scanner.next();
        if (!confirmation.equalsIgnoreCase("s")) {
            ui.getView().showMessage("Operazione annullata.");
            return;
        }
    
        ui.getView().showMessage("Stazioni di ricarica vicino a te: " + user.getLocation() + "\n");
    
        // Recupera la lista delle stazioni di ricarica nelle vicinanze
        List<ChargingStation> nearStations = ui.getController().getNearAvailableStation(user);
        
        // Verifica se ci sono stazioni disponibili
        if (nearStations.isEmpty()) {
            ui.getView().showMessage("Nessuna stazione di ricarica disponibile.");
            return; // Esci se non ci sono stazioni disponibili
        }
    
        // Procedi con la selezione della stazione di ricarica
        ChargingStation currentCS = ui.getController().chooseStation(currentVehicle);
    
        // Verifica se la stazione è occupata
        List<Reservation> allReservations  = ui.getController().getAllReservations();
        
        if (currentCS.isCurrentlyOccupied(allReservations, user)) {
            ui.getView().showMessage( "\u001B[31m" + "\nLa stazione è occupata. Scegli un'altra stazione o attendi.\n" + "\u001B[0m");
            return;
        } else {
            ui.getView().showMessage("\u001B[32m" + "La stazione è disponibile. Puoi procedere con la ricarica." + "\u001B[0m");
        }

        // Chiedi se l'utente è già alla stazione o deve arrivarci
        ui.getView().showMessage("Sei già alla stazione di ricarica di " + currentCS.getOwner() + " o devi ancora arrivarci? (1. Sono già qui / 2. Devo arrivare)");
        int travelOption = scanner.nextInt();
    
        if (travelOption == 2) {
            // Simula la connessione a un'app di navigazione
            ui.getView().simulateNavigationToStation(currentCS);
        }
    
        // Guida l'utente nel collegamento della presa
        ui.getView().guidePlugInProcess();

        // Scegli il metodo di pagamento e inizializza i dati di pagamento
        String choice = ui.getView().choosePaymentMethod(paymentOptions);
        PaymentStrategy paymentStrategy = paymentOptions.get(choice);
        
        if (paymentStrategy == null) {
            ui.getView().showMessage("Operazione annullata: nessun metodo di pagamento selezionato.");
            return;
        }
    
        paymentStrategy.initializePayment();
    
        // Chiedi se l'utente ha un codice sconto subito dopo il pagamento
        ui.getView().showMessage("Hai un codice sconto per la ricarica? (s/n)");
        String hasDiscountCode = scanner.next();
        double discount = 1.0; // Nessuno sconto di default

        if (hasDiscountCode.equalsIgnoreCase("s")) {
            // Chiedi il codice sconto
            ui.getView().showMessage("Inserisci il codice sconto:");
            String discountCode = scanner.next();
        
            // Verifica se il codice è valido per l'utente corrente
            if (ui.getController().isDiscountCodeValid(discountCode, user)) {
                // Ottieni la ricompensa appropriata dalla RewardFactory
                RewardType reward = RewardFactory.getRewardByCode(discountCode);

                if (reward != null) {
                    discount = reward.applyReward();
                    ui.getView().showMessage( "\u001B[32m"+ "\n Codice sconto valido! \n" + "\u001B[0m");
                } else {
                    ui.getView().showMessage("\u001B[31m"+ "Codice sconto non valido. Nessuno sconto applicato." + "\u001B[0m");
                }
            } else {
                ui.getView().showMessage("\u001B[31m"+ "Codice sconto non valido o non associato a questo utente. Nessuno sconto applicato." + "\u001B[0m");
            }
        }

    
        // Imposta l'ora corrente e crea una nuova carica
        LocalDateTime currentTime = LocalDateTime.now();
        Charge newCharge = new Charge(currentCS, currentVehicle, currentTime);
        Time startTime = new Time(currentTime.getHour(), currentTime.getMinute());

        // Simulazione della ricarica
        try {
            ui.getView().simulateCharging(user);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    
        // Dopo la ricarica, chiedi all'utente di scollegare la presa
        ui.getView().guidePlugOutProcess();
    
        // Registra la carica
        ui.getController().registerCharge(user, currentVehicle, currentCS, currentTime, newCharge, startTime, discount);
    
        // Calcola il costo della ricarica
        double amount = newCharge.getCost();
    
        // Registra la transazione e ottieni il risultato
        ui.getController().registerTransaction(user, currentVehicle, currentCS, newCharge, amount, paymentStrategy);
        
        // Calcolo dei Green Points
        // Assegna i green points utilizzando la strategia fornita
        if (greenPointsStrategy != null) {
            ui.getController().assignGreenPoints(user, greenPointsStrategy, (int) amount);
        } else {
            ui.getView().showMessage("Strategia di Green Points non definita.");
        }
        //GreenPointsStrategy gpStrategy = new GPRechargeStrategy(); 
        //ui.getController().assignGreenPoints(user, gpStrategy, (int) amount);
    
        ui.getView().showMessage("\nPremi Invio per tornare al menu principale o 'q' per uscire.");
        scanner.nextLine(); // Consuma la nuova linea rimasta nel buffer
        String input = scanner.nextLine(); // Leggi l'input dell'utente
        if (input.equalsIgnoreCase("q")) {
            // Salva tutte le informazioni
            ui.getController().saveAll();
            System.exit(0);
        }    
        ui.getController().saveAll();
    }

    

  
}
