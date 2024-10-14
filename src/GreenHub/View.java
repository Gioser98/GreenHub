package GreenHub;

import java.util.ArrayList;

public class View {

    public View() {
        // Costruttore vuoto, puoi aggiungere logica se necessario
    }

    // Metodo generico per mostrare un messaggio
    public void showMessage(String message) {
        System.out.println(message);
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
