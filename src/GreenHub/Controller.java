package GreenHub;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            //view.showMessage("Pagamento completato con successo!");
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

    public double randomBattery (User user){
        return user.getPersonalVehicle().generateRandomBatteryLevel();
    }

    public double randomBatteryPercentage (User user){
        double value =  (randomBattery(user) / user.getPersonalVehicle().getCapacity() ) * 100;
        return value;
    }

    public double calculateRechargeCost(User user,Vehicle vehicle, ChargingStation chargingStation) {
        double batteryCapacity = user.getPersonalVehicle().getCapacity();
        double currentBatteryLevel = randomBattery(user);
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
    
        double cost = calculateRechargeCost(user,vehicle, cs);
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

    // ==============================
    // Near Available Charging Stations
    // ==============================
    public List<ChargingStation> getNearAvailableStation(User user) {
    List<ChargingStation> availableStations = new ArrayList<>(); // Lista per le stazioni disponibili

    // Controlla che l'utente abbia un veicolo personale
    if (user.getPersonalVehicle() == null) {
        view.showMessage("L'utente non ha un veicolo personale.");
        return availableStations; // Restituisce una lista vuota
    }
    
    // Ottieni la posizione dell'utente
    Location userLocation = user.getPersonalVehicle().getLocation();

    // Mappa per memorizzare le stazioni e le loro distanze
    Map<ChargingStation, Double> distanceMap = new HashMap<>();

    // Itera attraverso le stazioni di ricarica
    for (ChargingStation cs : chargingStationList) {
        // Controlla che la stazione non sia in manutenzione e sia compatibile con il veicolo dell'utente
        if (!cs.isMaintenance() && cs.isCompatibleWith(user.getPersonalVehicle())) {
            // Calcola la distanza tra l'utente e la stazione di ricarica
            double distance = userLocation.distance(cs.getLocation());
            distanceMap.put(cs, distance); // Aggiungi la stazione e la sua distanza alla mappa
        }
    }

    // Ordina le stazioni in base alla distanza
    List<Map.Entry<ChargingStation, Double>> sortedStations = new ArrayList<>(distanceMap.entrySet());
    sortedStations.sort((entry1, entry2) -> Double.compare(entry1.getValue(), entry2.getValue()));

    // Aggiungi le prime 3 stazioni alla lista disponibile
    for (int i = 0; i < Math.min(3, sortedStations.size()); i++) {
        availableStations.add(sortedStations.get(i).getKey());
    }

    // Controlla se ci sono stazioni disponibili
    if (availableStations.isEmpty()) {
        view.showMessage("Non ci sono stazioni di ricarica disponibili.");
    } else {
        for (ChargingStation station : availableStations) {
            view.showMessage(station.toString()); // Mostra le stazioni disponibili
        }
    }

    return availableStations; // Restituisce la lista delle stazioni disponibili
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
