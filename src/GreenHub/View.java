package GreenHub;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;

public class View {

    private Scanner scanner = new Scanner(System.in);

    public View() {
        // Costruttore vuoto, puoi aggiungere logica se necessario
    }

    // Metodo generico per mostrare un messaggio
    public void showMessage(String message) {
        System.out.println(message);
    }

     // Metodo per ottenere l'ID della stazione dall'utente
     public int getStationIdFromUser() {
        System.out.print("Inserisci l'ID della stazione di ricarica scelta: ");
        return scanner.nextInt();
    }


    // Metodo per ottenere l'username dall'utente
    public String getInputUsername() {
        System.out.print("Inserisci il tuo username: ");
        return scanner.next();
    }

    // Metodo per ottenere il nome dall'utente
    public String getInputName() {
        System.out.print("Inserisci il tuo nome: ");
        return scanner.next();
    }

    // Metodo per ottenere il cognome dall'utente
    public String getInputSurname() {
        System.out.print("Inserisci il tuo cognome: ");
        return scanner.next();
    }

    
    // Metodo per stampare lo stato del veicolo
    public void showVehicleStatus(User user) {
        var vehicle = user.getPersonalVehicle();
        int batteryPercentage = (int) ((vehicle.getBatteryLevel() / vehicle.getCapacity()) * 100);
        System.out.println("\nLa tua " + vehicle.getMaker() + " " + vehicle.getModel() +
                " ha una percentuale di carica pari a " + batteryPercentage + " %");
    }

    // Metodo per mostrare le opzioni del menu
    public void showMenuOptions(User user) {
        System.out.println("\n1) Ricarica il tuo veicolo elettrico");
        System.out.println("2) Prenota una ricarica");
        System.out.println("3) Elenco prenotazioni");
        System.out.println("4) Riscatta una ricompensa");
        if (user.getPersonalVehicle() == null) {
            System.out.println("5) Registrazione nuova auto");
        }
        System.out.println("6) Esci");
    }
   
    public void showWelcomeMenuOptions() {
        System.out.println("-------------BENVENUTO IN GREENHUB-------------");
            System.out.println("Tutti i dati verranno caricati dai file fra pochi secondi.");
            System.out.println("Per salvare le modifiche sul file, terminare il programma scegliendo "
                    + "l'apposita opzione, altrimenti verranno perse.");
            System.out.println("1) Registrazione nuovo utente");
            System.out.println("2) Login utente già registrato");
            System.out.println("3) Exit");
            System.out.println("4) Leggi contenuti dei file");
            System.out.print("Scelta: ");
    }

    // Metodo per scegliere il metodo di pagamento
    public int choosePaymentMethod() {
        System.out.println("Scegli un metodo di pagamento:");
        System.out.println("1. Carta di credito");
        System.out.println("2. PayPal");

        return scanner.nextInt();
    }


    // Simulazione della ricarica con una barra di avanzamento
    public void simulateCharging(User user) throws InterruptedException {
        int max = (int) user.getPersonalVehicle().getCapacity();
        int batteryLevel = (int) user.getPersonalVehicle().getBatteryLevel();
        int initialCharge = (int) ((batteryLevel / (double) max) * 100);
        int targetCharge = 100;

        System.out.println("Inizio ricarica...\n");
        for (int i = initialCharge; i <= targetCharge; i++) {
            System.out.print("\r[");

            // Determina il colore in base alla percentuale
            String color;
            if (i <= 29) {
                color = "\u001B[31m"; // Rosso
            } else if (i <= 69) {
                color = "\u001B[33m"; // Giallo
            } else {
                color = "\u001B[32m"; // Verde
            }

            // Stampa la barra di avanzamento con il colore appropriato
            int progress = i / 10;
            for (int j = 0; j < 10; j++) {
                if (j < progress) {
                    System.out.print(color + "#");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.print("\u001B[0m"); // Resetta il colore
            System.out.print("] " + i + "%");

            // Attesa per simulare il tempo di ricarica
            Thread.sleep(300);
        }
        System.out.println("\nRicarica completata.");
    }

    // Simula la connessione a un'app di navigazione
    public void simulateNavigationToStation(ChargingStation station) {
        System.out.println("Connessione a un'app di navigazione per arrivare a " + station.getOwner() + "...");
        System.out.println("Simulazione di navigazione in corso: segui le indicazioni per " + station.getLocation() + ".");
        
        try {
            Thread.sleep(5000); // Pausa di 5 secondi
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Sei arrivato alla stazione di ricarica " + station.getOwner() + ".");
    }

    // Guida l'utente nel collegamento della presa
    public void guidePlugInProcess() {
        System.out.println("Collega il cavo di ricarica al tuo veicolo.\n");
        try {
            Thread.sleep(3000); // Pausa di 3 secondi
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Cavo collegato correttamente. La ricarica inizierà a breve.\n");
    }

    // Guida l'utente nello scollegamento della presa dopo la ricarica
    public void guidePlugOutProcess() {
        System.out.println("La ricarica è completata. Scollega il cavo di ricarica dal tuo veicolo.\n");
        try {
            Thread.sleep(3000); // Pausa di 3 secondi
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Cavo scollegato correttamente. \n\n");
    }




    // Metodi per stampare le varie liste
    public void printChargingRateList(ArrayList<ChargingRate> chargingRateList) {
        System.out.println("Charging Rate List: " + chargingRateList);
    }

    public void printEnergySupplierList(ArrayList<EnergySupplier> energySupplierList) {
        System.out.println("Energy Supplier List: " + energySupplierList);
    }

    public void printChargingStationList(ArrayList<ChargingStation> chargingStationList) {
        System.out.println("Charging Station List: " + chargingStationList);
    }

    public void printRewardList(ArrayList<Reward> rewardList) {
        System.out.println("Reward List: " + rewardList);
    }

    public void printUserList(ArrayList<User> userList) {
        System.out.println("User List: " + userList);
    }

    public void printVehicleList(ArrayList<Vehicle> vehicleList) {
        System.out.println("Vehicle List: " + vehicleList);
    }

    public void printTransactionList(ArrayList<Transaction> transactionList) {
        System.out.println("Transaction List: " + transactionList);
    }

    public void printReservationList(ArrayList<Reservation> reservationList) {
        System.out.println("Reservation List: " + reservationList);
    }
    
    
public void printRedeemedRewardsMap(Map<String, String> redeemedRewardsMap) {
    if (redeemedRewardsMap == null || redeemedRewardsMap.isEmpty()) {
        System.out.println("La mappa delle ricompense riscattate è vuota.");
        return;
    }
    System.out.println("Contenuto della mappa delle ricompense riscattate:");
    for (Map.Entry<String, String> entry : redeemedRewardsMap.entrySet()) {
        System.out.println("Codice: " + entry.getKey() + " - Utente: " + entry.getValue());
    }
}

}
