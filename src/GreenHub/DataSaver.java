package GreenHub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class DataSaver {

    private List<ChargingRate> chargingRateList;
    private List<EnergySupplier> energySupplierList;
    private List<ChargingStation> chargingStationList;
    private List<Reward> rewardList;
    private List<User> userList;
    private List<Vehicle> vehicleList;
    private List<Transaction> transactionList;
    private List<Reservation> reservationList;
    private RewardSystem currentRewardSystem;

    // Costruttore per inizializzare le liste e l'oggetto RewardSystem
    public DataSaver(List<ChargingRate> chargingRateList,
                     List<EnergySupplier> energySupplierList,
                     List<ChargingStation> chargingStationList,
                     List<Reward> rewardList,
                     List<User> userList,
                     List<Vehicle> vehicleList,
                     List<Transaction> transactionList,
                     List<Reservation> reservationList,
                     RewardSystem currentRewardSystem) {
        this.chargingRateList = chargingRateList;
        this.energySupplierList = energySupplierList;
        this.chargingStationList = chargingStationList;
        this.rewardList = rewardList;
        this.userList = userList;
        this.vehicleList = vehicleList;
        this.transactionList = transactionList;
        this.reservationList = reservationList;
        this.currentRewardSystem = currentRewardSystem;
    }

    // Metodo per salvare tutti i dati su file
    public void saveAll() throws IOException {
        // Salvataggio di chargingRateList
        try (FileOutputStream chargingRateFOS = new FileOutputStream(new File("ChargingRate.txt"));
             ObjectOutputStream chargingRateOOS = new ObjectOutputStream(chargingRateFOS)) {
            chargingRateOOS.writeObject(chargingRateList);
        }

        // Salvataggio di energySupplierList
        try (FileOutputStream energySupplierFOS = new FileOutputStream(new File("EnergySupplier.txt"));
             ObjectOutputStream energySupplierOOS = new ObjectOutputStream(energySupplierFOS)) {
            energySupplierOOS.writeObject(energySupplierList);
        }

        // Salvataggio di chargingStationList
        try (FileOutputStream chargingStationFOS = new FileOutputStream(new File("ChargingStation.txt"));
             ObjectOutputStream chargingStationOOS = new ObjectOutputStream(chargingStationFOS)) {
            chargingStationOOS.writeObject(chargingStationList);
        }

        // Salvataggio di rewardList
        try (FileOutputStream rewardFOS = new FileOutputStream(new File("Reward.txt"));
             ObjectOutputStream rewardOOS = new ObjectOutputStream(rewardFOS)) {
            rewardOOS.writeObject(rewardList);
        }

        // Salvataggio di userList
        try (FileOutputStream userFOS = new FileOutputStream(new File("User.txt"));
             ObjectOutputStream userOOS = new ObjectOutputStream(userFOS)) {
            userOOS.writeObject(userList);
        }

        // Salvataggio di vehicleList
        try (FileOutputStream vehicleFOS = new FileOutputStream(new File("Vehicle.txt"));
             ObjectOutputStream vehicleOOS = new ObjectOutputStream(vehicleFOS)) {
            vehicleOOS.writeObject(vehicleList);
        }

        // Salvataggio di transactionList
        try (FileOutputStream transactionFOS = new FileOutputStream(new File("Transaction.txt"));
             ObjectOutputStream transactionOOS = new ObjectOutputStream(transactionFOS)) {
            transactionOOS.writeObject(transactionList);
        }

        // Salvataggio di reservationList
        try (FileOutputStream reservationFOS = new FileOutputStream(new File("Reservation.txt"));
             ObjectOutputStream reservationOOS = new ObjectOutputStream(reservationFOS)) {
            reservationOOS.writeObject(reservationList);
        }

        // Salvataggio di RewardSystem
        try (FileOutputStream rewardSystemFOS = new FileOutputStream(new File("RewardSystem.txt"));
             ObjectOutputStream rewardSystemOOS = new ObjectOutputStream(rewardSystemFOS)) {
            rewardSystemOOS.writeObject(currentRewardSystem);
        }

        System.out.println("\n\n----------------------------------------------------------------");
        System.out.println("Tutti i dati sono stati correttamente salvati su file.");
        System.out.println("----------------------------------------------------------------");
    }
}
