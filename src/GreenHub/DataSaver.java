package GreenHub;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
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
    public DataSaver() {
        this.chargingRateList = new ArrayList<>();
        this.energySupplierList = new ArrayList<>();
        this.chargingStationList = new ArrayList<>();
        this.rewardList = new ArrayList<>();
        this.userList = new ArrayList<>();
        this.vehicleList = new ArrayList<>();
        this.transactionList = new ArrayList<>();
        this.reservationList = new ArrayList<>();
        this.currentRewardSystem = new RewardSystem(); // Assicurati che RewardSystem abbia un costruttore di default
    }

    public DataSaver(List<ChargingRate> chargingRateList, List<EnergySupplier> energySupplierList,
                     List<ChargingStation> chargingStationList, List<Reward> rewardList, 
                     List<User> userList, List<Vehicle> vehicleList, 
                     List<Transaction> transactionList, List<Reservation> reservationList,
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




    // Metodo per caricare tutti i dati dai file
    public void loadAll() throws IOException, ClassNotFoundException {
        // Caricamento di chargingRateList
        File file = new File("ChargingRate.txt");
        if (file.exists()) {
            try (FileInputStream chargingRateFIS = new FileInputStream(file);
                 ObjectInputStream chargingRateOIS = new ObjectInputStream(chargingRateFIS)) {
                chargingRateList = (List<ChargingRate>) chargingRateOIS.readObject();
            }
        } else {
            System.out.println("File ChargingRate.txt non trovato.");
        }

        // Caricamento di energySupplierList
        file = new File("EnergySupplier.txt");
        if (file.exists()) {
            try (FileInputStream energySupplierFIS = new FileInputStream(file);
                 ObjectInputStream energySupplierOIS = new ObjectInputStream(energySupplierFIS)) {
                energySupplierList = (List<EnergySupplier>) energySupplierOIS.readObject();
            }
        } else {
            System.out.println("File EnergySupplier.txt non trovato.");
        }

        // Caricamento di chargingStationList
        file = new File("ChargingStation.txt");
        if (file.exists()) {
            try (FileInputStream chargingStationFIS = new FileInputStream(file);
                 ObjectInputStream chargingStationOIS = new ObjectInputStream(chargingStationFIS)) {
                chargingStationList = (List<ChargingStation>) chargingStationOIS.readObject();
            }
        } else {
            System.out.println("File ChargingStation.txt non trovato.");
        }

        // Caricamento di rewardList
        file = new File("Reward.txt");
        if (file.exists()) {
            try (FileInputStream rewardFIS = new FileInputStream(file);
                 ObjectInputStream rewardOIS = new ObjectInputStream(rewardFIS)) {
                rewardList = (List<Reward>) rewardOIS.readObject();
            }
        } else {
            System.out.println("File Reward.txt non trovato.");
        }

        // Caricamento di userList
        file = new File("User.txt");
        if (file.exists()) {
            try (FileInputStream userFIS = new FileInputStream(file);
                 ObjectInputStream userOIS = new ObjectInputStream(userFIS)) {
                userList = (List<User>) userOIS.readObject();
            }
        } else {
            System.out.println("File User.txt non trovato.");
        }

        // Caricamento di vehicleList
        file = new File("Vehicle.txt");
        if (file.exists()) {
            try (FileInputStream vehicleFIS = new FileInputStream(file);
                 ObjectInputStream vehicleOIS = new ObjectInputStream(vehicleFIS)) {
                vehicleList = (List<Vehicle>) vehicleOIS.readObject();
            }
        } else {
            System.out.println("File Vehicle.txt non trovato.");
        }

        // Caricamento di transactionList
        file = new File("Transaction.txt");
        if (file.exists()) {
            try (FileInputStream transactionFIS = new FileInputStream(file);
                 ObjectInputStream transactionOIS = new ObjectInputStream(transactionFIS)) {
                transactionList = (List<Transaction>) transactionOIS.readObject();
            }
        } else {
            System.out.println("File Transaction.txt non trovato.");
        }

        // Caricamento di reservationList
        file = new File("Reservation.txt");
        if (file.exists()) {
            try (FileInputStream reservationFIS = new FileInputStream(file);
                 ObjectInputStream reservationOIS = new ObjectInputStream(reservationFIS)) {
                reservationList = (List<Reservation>) reservationOIS.readObject();
            }
        } else {
            System.out.println("File Reservation.txt non trovato.");
        }

        // Caricamento di RewardSystem
        file = new File("RewardSystem.txt");
        if (file.exists()) {
            try (FileInputStream rewardSystemFIS = new FileInputStream(file);
                 ObjectInputStream rewardSystemOIS = new ObjectInputStream(rewardSystemFIS)) {
                currentRewardSystem = (RewardSystem) rewardSystemOIS.readObject();
            }
        } else {
            System.out.println("File RewardSystem.txt non trovato.");
        }

        System.out.println("\n\n----------------------------------------------------------------");
        System.out.println("Tutti i dati sono stati correttamente caricati dai file.");
        System.out.println("----------------------------------------------------------------");
    }

    

    // Metodo per stampare tutti i dati
    public void printData() {

        System.out.println("Charging Rate List: " + chargingRateList);
        System.out.println("Energy Supplier List: " + energySupplierList);
        System.out.println("Charging Station List: " + chargingStationList);
        System.out.println("Reward List: " + rewardList);
        System.out.println("User List: " + userList);
        System.out.println("Vehicle List: " + vehicleList);
        System.out.println("Transaction List: " + transactionList);
        System.out.println("Reservation List: " + reservationList);
        System.out.println("Reward System: " + currentRewardSystem);
    }

    // Metodo per stampare solo gli utenti
    public void printUsers() {
        if (userList == null || userList.isEmpty()) {
            System.out.println("La lista degli utenti è vuota o non è stata inizializzata.");
            return;
        }
        
        System.out.println("Lista degli utenti:");
        for (User user : userList) {
            System.out.println(user);
        }
    }

}

