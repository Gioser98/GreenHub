package GreenHub;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;

public class DataSaver {
    // Le liste non devono essere qui, il DataSaver ora si occupa solo di salvare e leggere dati, non di gestire liste
    public DataSaver(){
        // Costruttore vuoto
    }

    // Metodo per salvare tutte le liste su file
    public void saveAll(ArrayList<ChargingRate> chargingRateList, ArrayList<EnergySupplier> energySupplierList, 
                        ArrayList<ChargingStation> chargingStationList, ArrayList<Reward> rewardList, 
                        ArrayList<User> userList, ArrayList<Vehicle> vehicleList, 
                        ArrayList<Transaction> transactionList, ArrayList<Reservation> reservationList, Map<String, String> redeemedRewardsMap) throws IOException {
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
        // Salvataggio di redeemedRewardsMap
        try (FileOutputStream rewardMapFOS = new FileOutputStream(new File("RedeemedRewardsMap.txt"));
            ObjectOutputStream rewardMapOOS = new ObjectOutputStream(rewardMapFOS)) {
            rewardMapOOS.writeObject(redeemedRewardsMap);
            }
        System.out.println("\n----------------------------------------------------------------");
        System.out.println("Tutti i dati sono stati correttamente salvati su file.");
        System.out.println("----------------------------------------------------------------");
    }

    // Metodo per leggere tutte le liste dai file
    @SuppressWarnings("unchecked")
    public void readAll(ArrayList<ChargingRate> chargingRateList, ArrayList<EnergySupplier> energySupplierList, 
                        ArrayList<ChargingStation> chargingStationList, ArrayList<Reward> rewardList, 
                        ArrayList<User> userList, ArrayList<Vehicle> vehicleList, 
                        ArrayList<Transaction> transactionList, ArrayList<Reservation> reservationList, Map<String, String> redeemedRewardsMap) throws FileNotFoundException, IOException {
        // Lettura chargingRateList
        FileInputStream FIS = new FileInputStream("ChargingRate.txt");
        ObjectInputStream OIS = new ObjectInputStream(FIS);
        try {
            chargingRateList.addAll((ArrayList<ChargingRate>) OIS.readObject());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            OIS.close();
        }

        // Lettura energySupplierList
        FIS = new FileInputStream("EnergySupplier.txt");
        OIS = new ObjectInputStream(FIS);
        try {
            energySupplierList.addAll((ArrayList<EnergySupplier>) OIS.readObject());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            OIS.close();
        }

        // Lettura chargingStationList
        FIS = new FileInputStream("ChargingStation.txt");
        OIS = new ObjectInputStream(FIS);
        try {
            chargingStationList.addAll((ArrayList<ChargingStation>) OIS.readObject());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            OIS.close();
        }

        // Lettura rewardList
        FIS = new FileInputStream("Reward.txt");
        OIS = new ObjectInputStream(FIS);
        try {
            rewardList.addAll((ArrayList<Reward>) OIS.readObject());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            OIS.close();
        }

        // Lettura userList
        FIS = new FileInputStream("User.txt");
        OIS = new ObjectInputStream(FIS);
        try {
            userList.addAll((ArrayList<User>) OIS.readObject());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            OIS.close();
        }

        // Lettura vehicleList
        FIS = new FileInputStream("Vehicle.txt");
        OIS = new ObjectInputStream(FIS);
        try {
            vehicleList.addAll((ArrayList<Vehicle>) OIS.readObject());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            OIS.close();
        }

        // Lettura transactionList
        FIS = new FileInputStream("Transaction.txt");
        OIS = new ObjectInputStream(FIS);
        try {
            transactionList.addAll((ArrayList<Transaction>) OIS.readObject());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            OIS.close();
        }

        // Lettura reservationList
        FIS = new FileInputStream("Reservation.txt");
        OIS = new ObjectInputStream(FIS);
        try {
            reservationList.addAll((ArrayList<Reservation>) OIS.readObject());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            OIS.close();
        }

        // Caricamento di redeemedRewardsMap
        try (FileInputStream mapFIS = new FileInputStream("RedeemedRewardsMap.txt");
            ObjectInputStream mapOIS = new ObjectInputStream(mapFIS)) {
            // Carica la mappa direttamente e assegna
            Map<String, String> loadedMap = (Map<String, String>) mapOIS.readObject();
   
            // Svuota e sostituisci la mappa originale con i dati caricati
        
            redeemedRewardsMap.putAll(loadedMap);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        }


       
    
}
