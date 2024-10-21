package GreenHub;

import java.util.ArrayList;
import java.util.Scanner;

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
}
