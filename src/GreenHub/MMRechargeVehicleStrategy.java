package GreenHub;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MMRechargeVehicleStrategy implements MainMenuStrategy {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void execute(UserInterface ui, User user) throws IOException {
        Vehicle currentVehicle = user.getPersonalVehicle();

        // Mostra il nome del veicolo e chiedi conferma
        System.out.println("Stai per ricaricare il veicolo: " 
            + (currentVehicle.getMaker() != null ? currentVehicle.getMaker() : "Produttore sconosciuto") + " " 
            + (currentVehicle.getModel() != null ? currentVehicle.getModel() : "Modello sconosciuto") 
            + ". Vuoi continuare? (s/n)");

        String confirmation = scanner.next();
        if (!confirmation.equalsIgnoreCase("s")) {
            System.out.println("Operazione annullata.");
            return;
        }

        // Richiedi la distanza massima per trovare le stazioni di ricarica disponibili
        System.out.print("Stazioni di ricarica vicino a te: " + user.getLocation() + "\n ") ;
        

        // Recupera la lista delle stazioni di ricarica nelle vicinanze
        List<ChargingStation> nearStations = ui.getController().getNearAvailableStation(user);
        
        // Verifica se ci sono stazioni disponibili
        if (nearStations.isEmpty()) {
            System.out.println("Nessuna stazione di ricarica disponibile nella distanza specificata.");
            return; // Esci se non ci sono stazioni disponibili
        }

        // Procedi con la selezione della stazione di ricarica
        ChargingStation currentCS = ui.getController().chooseStation(currentVehicle);

        // Chiedi se l'utente è già alla stazione o deve arrivarci
        System.out.println("Sei già alla stazione di ricarica di " + currentCS.getOwner() + " o devi ancora arrivarci? (1. Sono già qui / 2. Devo arrivare)");
        int travelOption = scanner.nextInt();

        if (travelOption == 2) {
            // Simula la connessione a un'app di navigazione
            simulateNavigationToStation(currentCS);
        }

        // Guida l'utente nel collegamento della presa
        guidePlugInProcess();

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

        // Simulazione della ricarica
        try {
            simulateCharging(ui, user);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Dopo la ricarica, chiedi all'utente di scollegare la presa
        guidePlugOutProcess();

        // Registra la carica
        ui.getController().registerCharge(user, currentVehicle, currentCS, currentTime, newCharge, startTime);

        // Calcola il costo della ricarica
        double amount = newCharge.getCost();

        // Registra la transazione e ottieni il risultato
        ui.getController().registerTransaction(user, currentVehicle, currentCS, newCharge, amount, paymentStrategy);
        
        // Calcolo dei Green Points
        GreenPointsStrategy gpStrategy = new GPRechargeStrategy();
        int chargePercentage = (int) amount;  // Supponiamo che sia la percentuale di ricarica
        gpStrategy.calculatePoints(chargePercentage);
        ui.getController().assignGreenPoints(user, gpStrategy, chargePercentage);

        System.out.println("\nPremi Invio per tornare al menu principale o 'q' per uscire.");
        scanner.nextLine(); // Consuma la nuova linea rimasta nel buffer
        String input = scanner.nextLine(); // Leggi l'input dell'utente
        if (input.equalsIgnoreCase("q")) {
            // Salva tutte le informazioni
            ui.getController().saveAll();
            System.exit(0);
        }    
        ui.getController().saveAll();
    }

    // Metodo per scegliere il metodo di pagamento
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

    // Simulazione della ricarica con una barra di avanzamento
    private void simulateCharging(UserInterface ui, User user) throws InterruptedException {

        int max = (int) user.getPersonalVehicle().getCapacity();
        int marco = (int) user.getPersonalVehicle().getBatteryLevel();
        int percentuale = (int) ((marco / (double) max) * 100);

        int initialCharge = percentuale; // Ottieni la percentuale iniziale di carica
        int targetCharge = 100; // Ricarica fino al 100%
    
        System.out.println("Inizio ricarica..." + percentuale);
        for (int i = initialCharge; i <= targetCharge; i++) {
            // Simula la barra di avanzamento
            System.out.print("\r[");

            // Determina il colore in base alla percentuale
            String color;
            if (i <= 30) {
                color = "\u001B[31m"; // Rosso
            } else if (i <= 70) {
                color = "\u001B[33m"; // Giallo
            } else {
                color = "\u001B[32m"; // Verde
            }

            // Stampa la barra di avanzamento con il colore appropriato
            int progress = i / 10;  // Ogni 10% aggiunge un segmento alla barra
            for (int j = 0; j < 10; j++) {
                if (j < progress) {
                    System.out.print(color + "#");  // Parte già caricata
                } else {
                    System.out.print(" ");  // Parte non caricata
                }
            }
            System.out.print("\u001B[0m"); // Resetta il colore
            System.out.print("] " + i + "%");

            // Attesa per simulare il tempo di ricarica
            Thread.sleep(300);  // Attesa di 300 millisecondi per ogni incremento di 1%
        }
        System.out.println("\nRicarica completata.");
    }
    
    // Simula la connessione a un'app di navigazione
    private void simulateNavigationToStation(ChargingStation station) {
        System.out.println("Connessione a un'app di navigazione per arrivare a " + station.getOwner() + "...");
        System.out.println("Simulazione di navigazione in corso: segui le indicazioni per " + station.getLocation() + ".");
        // Simula una pausa di 2 secondi
        try {
            Thread.sleep(5000); // Pausa di 5000 millisecondi
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Sei arrivato alla stazione di ricarica " + station.getOwner() + ".");
    }

    // Guida l'utente nel collegamento della presa
    private void guidePlugInProcess() {
        System.out.println("Collega il cavo di ricarica al tuo veicolo.");
        try {
            Thread.sleep(3000); // Pausa di 3000 millisecondi
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Cavo collegato correttamente. La ricarica inizierà a breve.");
    }

    // Guida l'utente nello scollegamento della presa dopo la ricarica
    private void guidePlugOutProcess() {
        System.out.println("La ricarica è completata. Scollega il cavo di ricarica dal tuo veicolo.");
        try {
            Thread.sleep(3000); // Pausa di 5000 millisecondi
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Cavo scollegato correttamente. \n\n");
        try {
            Thread.sleep(3000); // Pausa di 5000 millisecondi
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
