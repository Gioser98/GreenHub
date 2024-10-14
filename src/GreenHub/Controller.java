package GreenHub;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Controller {
    // Liste per i dati
    public ArrayList<ChargingRate> chargingRateList = new ArrayList<>();
    public ArrayList<EnergySupplier> energySupplierList = new ArrayList<>();
    public ArrayList<ChargingStation> chargingStationList = new ArrayList<>();
    public ArrayList<Reward> rewardList = new ArrayList<>();
    public ArrayList<User> userList = new ArrayList<>();
    public ArrayList<Vehicle> vehicleList = new ArrayList<>();
    public ArrayList<Transaction> transactionList = new ArrayList<>();
    public ArrayList<Reservation> reservationList = new ArrayList<>();
    private Reward rewards = new Reward();
    private DataSaver dataSaver = new DataSaver();
    private View view = new View();  // Riferimento alla View

    public Controller() {
        //;  // Inizializza la View
    }
	


    // ==============================
    // User methods
    // ==============================
    public void addUser(User user) {
        if (getUserByUsername(user.getUsername()) == null) {
            userList.add(user);
        } else {
            view.showMessage("Username già esistente.");
        }
    }

    public User getUserByUsername(String username) {
        for (User u : userList) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public void increaseUserGPBalance(String username, int points) {
        User user = getUserByUsername(username);
        user.increaseGPBalance(points);
        view.showMessage("Saldo GP aggiornato correttamente!");
    }

    // ==============================
    // Transaction methods
    // ==============================
    public String registerTransaction(User user, Vehicle vehicle, ChargingStation chargingStation, Charge charge, double amount, PaymentStrategy strategy) {
        Transaction transaction = new Transaction();
        try {
            transaction.setUser(user);
            transaction.setVehicle(vehicle);
            transaction.setCharge(charge);
            transaction.setAmount(amount);
            transaction.setPaymentStrategy(strategy);

            LocalDateTime now = LocalDateTime.now();
            Time timestamp = new Time(now.getHour(), now.getMinute());
            transaction.setTimestamp(timestamp);

            int newId = transactionList.isEmpty() ? 1 : transactionList.get(transactionList.size() - 1).getId() + 1;
            transaction.setId(newId);

            transaction.processPayment();
            transactionList.add(transaction);
            view.showMessage("Pagamento completato con successo!");
            return "Pagamento effettuato con successo e transazione registrata.";
        } catch (Exception e) {
            return "Errore durante il pagamento: " + e.getMessage();
        }
    }

    // ==============================
    // Reward methods
    // ==============================
    public void assignGreenPoints(User user, GreenPointsStrategy strategy, int value) {
        rewards.setStrategy(strategy);
        rewards.addPoints(user, value);
    }

    // ==============================
    // Reservation methods
    // ==============================
    public void reserveSlot(User currentUser, Vehicle currentVehicle, ChargingStation currentCS, int startingSlot, int endingSlot) {
        boolean slotAvailable = true; // Logica per verificare la disponibilità dello slot
        Reservation.reserveSlot(currentUser, currentVehicle, currentCS, startingSlot, endingSlot, slotAvailable, reservationList);
    }

    // ==============================
    // Vehicle methods
    // ==============================
    public void addVehicle(User owner, Vehicle vehicle) {
        if (owner == null || vehicle == null) {
            throw new IllegalArgumentException("Owner or vehicle cannot be null");
        }

        vehicle.setOwner(owner);
        vehicleList.add(vehicle);

        if (owner.getPersonalVehicle() == null) {
            owner.setPersonalVehicle(vehicle);
        }

        view.showMessage("Veicolo registrato correttamente!");
    }

    // ==============================
    // ChargingRate methods
    // ==============================
    public ArrayList<ChargingRate> getChargingRateList() {
        return chargingRateList;
    }

    // ==============================
    // EnergySupplier methods
    // ==============================

    // ==============================
    // ChargingStation methods
    // ==============================
    public ArrayList<ChargingStation> getChargingStationList() {
        return chargingStationList;
    }

    public ChargingStation chooseStation(Vehicle vehicle) {
        int stationId = view.getStationIdFromUser(); // Chiede alla View l'ID della stazione
    
        for (ChargingStation cs : chargingStationList) {
            if (cs.getId() == stationId && cs.isCompatibleWith(vehicle) && !cs.isMaintenance()) {
                view.showMessage("Hai scelto: " + cs);
                return cs;
            }
        }
    
        view.showMessage("Stazione non disponibile o non compatibile.");
        return null;
    }
    
    

    public double calculateRechargeCost(Vehicle vehicle, ChargingStation chargingStation) {
        double batteryCapacity = vehicle.getCapacity();
        double currentBatteryLevel = vehicle.generateRandomBatteryLevel();
        double chargingRate = chargingStation.getChargingRateForVehicle(vehicle);
        
        double energyToRecharge = batteryCapacity - currentBatteryLevel;
        if (energyToRecharge <= 0) {
            view.showMessage("La batteria è già carica o sopra il massimo.");
            return 0; // Non serve ricaricare
        }

        double cost = energyToRecharge * chargingRate;
        return cost;
    }

    public void updateBatteryLevel(User user) {
        Vehicle vehicle = user.getPersonalVehicle();
        if (vehicle != null) {
            double newBatteryLevel = vehicle.generateRandomBatteryLevel();
            vehicle.setBatteryLevel(newBatteryLevel);
        } else {
            view.showMessage("Nessun veicolo associato a questo utente.");
        }
    }

    public void registerCharge(User user, Vehicle vehicle, ChargingStation cs, LocalDateTime currentTime, Charge newCharge, Time startTime) {
        newCharge.setChargingStation(cs);
        newCharge.setVehicle(vehicle);
        newCharge.setChargingRate(vehicle.getSupportedRate());
        newCharge.setUser(user);
        newCharge.setId(0); // Placeholder, da modificare

        startTime.setHour(currentTime.getHour());
        startTime.setMinute(currentTime.getMinute());

        double timeToCharge = vehicle.getCapacity() / vehicle.getSupportedRate().getPower();
        int hour = (int) timeToCharge;
        int minute = (int) (timeToCharge * 60) % 60;
        int endHour = startTime.getHour() + hour;
        int endMinute = startTime.getMinute() + minute;

        // Gestisci il caso in cui i minuti superano 59
        if (endMinute >= 60) {
            endHour += endMinute / 60;
            endMinute = endMinute % 60;
        }

        newCharge.setStartTime(startTime);
        newCharge.setEndTime(new Time(endHour, endMinute));

        double cost = calculateRechargeCost(vehicle, cs);
        newCharge.setCost(cost);
    }

    // ==============================
    // Data management methods
    // ==============================
    public void saveAll() {
        try {
            dataSaver.chargingRateList = this.chargingRateList;
            dataSaver.energySupplierList = this.energySupplierList;
            dataSaver.chargingStationList = this.chargingStationList;
            dataSaver.rewardList = this.rewardList;
            dataSaver.userList = this.userList;
            dataSaver.vehicleList = this.vehicleList;
            dataSaver.transactionList = this.transactionList;
            dataSaver.reservationList = this.reservationList;

            dataSaver.saveAll();
        } catch (IOException e) {
            view.showMessage("Errore durante il salvataggio: " + e.getMessage());
        }
    }

    public void readAll() {
        try {
            dataSaver.readAll();

            this.chargingRateList = dataSaver.chargingRateList;
            this.energySupplierList = dataSaver.energySupplierList;
            this.chargingStationList = dataSaver.chargingStationList;
            this.rewardList = dataSaver.rewardList;
            this.userList = dataSaver.userList;
            this.vehicleList = dataSaver.vehicleList;
            this.transactionList = dataSaver.transactionList;
            this.reservationList = dataSaver.reservationList;
        } catch (IOException e) {
            view.showMessage("Errore durante la lettura: " + e.getMessage());
        }
    }

    // Metodo per stampare tutti i dati
    public void printino() {
        view.printChargingRateList(chargingRateList);
        view.printEnergySupplierList(energySupplierList);
        view.printChargingStationList(chargingStationList);
        view.printRewardList(rewardList);
        view.printUserList(userList);
        view.printVehicleList(vehicleList);
        view.printTransactionList(transactionList);
        view.printReservationList(reservationList);
    }
}
