package GreenHub;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
        // Costruttore vuoto
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

    // Metodo per registrare un utente
    public void registerUser() {
        String username;
        while (true) {  // Ciclo fino a quando non si ottiene un username valido
            username = view.getInputUsername();  // Ottiene l'username dall'utente

            // Controlla se l'username esiste già
            if (getUserByUsername(username) != null) {
                view.showMessage("Username già esistente. Scegline un altro.");
            } else {
                break;  // Esci dal ciclo se l'username è unico
            }
        }

        String name = view.getInputName();          // Usa il metodo esistente per ottenere il nome
        String surname = view.getInputSurname();    // Usa il metodo esistente per ottenere il cognome
        int latitude = new Random().nextInt(100);   // Genera casualmente latitudine
        int longitude = new Random().nextInt(100);  // Genera casualmente longitudine
        Location location = new Location(latitude, longitude);
        User user = new User(username, 0, 2, name, surname, location);

        addUser(user);
        view.showMessage("Utente registrato correttamente!");
    }

     // Metodo per il login di un utente
     public User loginUser() {
    
        String username = view.getInputUsername();  // Ottieni l'username dalla View
        User user = getUserByUsername(username);
    
        if (user != null) {
            // Aggiorna la posizione dell'utente con una nuova posizione casuale
            Location newLocation = generateRandomLocation();
            user.setLocation(newLocation);
    
            // Controlla se l'utente ha un veicolo personale associato
            if (user.getPersonalVehicle() != null) {
                // Aggiorna il livello di batteria del veicolo con un nuovo livello casuale
                int newBatteryLevel = generateRandomBatteryLevel(user);
                user.getPersonalVehicle().setBatteryLevel(newBatteryLevel);
            } else {
                view.showMessage("Non hai un veicolo associato. Puoi aggiungerne uno successivamente.");
            }
    
            return user;  // Ritorna l'utente se trovato
        } else {
            view.showMessage("Utente non trovato.");
            return null;  // Ritorna null se non trovato
        }
    }

    // Metodo per generare una posizione casuale
    private Location generateRandomLocation() {
        int latitude = new Random().nextInt(100);   // Genera casualmente latitudine
        int longitude = new Random().nextInt(100);  // Genera casualmente longitudine
    return new Location(latitude, longitude);
    }

    // Metodo per generare un livello di batteria casuale
    private int generateRandomBatteryLevel(User user) {
        int maxBatteryLevel = (int) user.getPersonalVehicle().getCapacity(); // Ottieni la capacità del veicolo
    return new Random().nextInt(maxBatteryLevel + 1); // Genera un livello casuale tra 0 e capacity
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
        int newBatteryLevel = generateRandomBatteryLevel(owner);
        vehicle.setBatteryLevel(newBatteryLevel);
        view.showMessage("Veicolo registrato correttamente!");
    }

    // ==============================
    // ChargingRate methods
    // ==============================
    public ArrayList<ChargingRate> getChargingRateList() {
        return chargingRateList;
    }

    // ==============================
    // ChargingStation methods
    // ==============================
    public ArrayList<ChargingStation> getChargingStationList() {
        return chargingStationList;
    }

    public ChargingStation chooseStation(Vehicle vehicle) {
        int stationId = view.getStationIdFromUser(); // Chiede alla View l'ID della stazione
    
        for (ChargingStation cs : chargingStationList) {
            if (cs.getId() == stationId  && !cs.isMaintenance()) {
                view.showMessage("Hai scelto: " + cs);
                return cs;
            }
        }
    
        view.showMessage("Stazione non disponibile o non compatibile.");
        return null;
    }

    

    public double calculateRechargeCost(User user, ChargingStation chargingStation) {
        double batteryCapacity = user.getPersonalVehicle().getCapacity();
        double currentBatteryLevel = user.getPersonalVehicle().getBatteryLevel();
        double chargingRate = chargingStation.getChargingRateForVehicle(user.getPersonalVehicle());
        
        double energyToRecharge = batteryCapacity - currentBatteryLevel;
        if (energyToRecharge <= 0) {
            view.showMessage("La batteria è già carica");
            return 0; // Non serve ricaricare
        }

        double cost = energyToRecharge * chargingRate;
        return cost;
    }

    

    public List<ChargingStation> getNearAvailableStation(User user) {
        List<ChargingStation> availableStations = new ArrayList<>(); // Lista per le stazioni disponibili

        // Controlla che l'utente abbia un veicolo personale
        if (user.getPersonalVehicle() == null) {
            view.showMessage("L'utente non ha un veicolo personale.");
            return availableStations; // Restituisce una lista vuota
        }

        // Ottieni la posizione dell'utente
        Location userLocation = user.getLocation();

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

    public void registerCharge(User user, Vehicle vehicle, ChargingStation cs, LocalDateTime currentTime, Charge newCharge, Time startTime) {
        newCharge.setChargingStation(cs);
        newCharge.setVehicle(vehicle);
        newCharge.setChargingRate(vehicle.getSupportedRate());
        newCharge.setUser(user);

        // Imposta l'ora di inizio ricarica
        startTime.setHour(currentTime.getHour());
        startTime.setMinute(currentTime.getMinute());

        // Calcola il tempo necessario per la ricarica in ore
        double timeToCharge = vehicle.getCapacity() / vehicle.getSupportedRate().getPower();

        // Utilizza il metodo di Time per calcolare l'ora di fine
        Time endTime = startTime.addDuration(timeToCharge);

        // Imposta l'ora di inizio e fine ricarica
        newCharge.setStartTime(startTime);
        newCharge.setEndTime(endTime);

        // Calcola il costo della ricarica
        double cost = calculateRechargeCost(user, cs);
        newCharge.setCost(cost);
        vehicle.setBatteryLevel(100);
    }

    // ==============================
    // Reservation methods
    // ==============================
    public void reserveSlot(User currentUser, Vehicle currentVehicle, ChargingStation currentCS,
                        int startingSlot, int endingSlot) {
    boolean slotAvailable = true;  // Logica per verificare la disponibilità dello slot

    // Controlla la disponibilità degli slot
    for (int j = startingSlot; j < endingSlot; j++) {
        if (!currentCS.isSlotAvailable(j)) { // Controlla la disponibilità dello slot
            slotAvailable = false;
            break;
        }
    }   

    if (slotAvailable) {
        // Prenota gli slot nella stazione di ricarica
        for (int j = startingSlot; j < endingSlot; j++) {
            currentCS.setTimeTable(currentUser.getUsername(), j); // Assegna lo slot all'utente
        }
        
        view.showMessage("Slot prenotati!");

        // Crea una nuova prenotazione
        Reservation newReservation = new Reservation();
        newReservation.setUser(currentUser);
        newReservation.setVehicle(currentVehicle);
        newReservation.setChargingStation(currentCS);
        newReservation.setStartTime(new Time(startingSlot * 30 / 60, startingSlot * 30 % 60));
        newReservation.setEndTime(new Time(endingSlot * 30 / 60, endingSlot * 30 % 60));

        // Genera un nuovo ID per la prenotazione
        int maxID = 0;
        for (Reservation r : reservationList) {
            if (r.getId() > maxID) {
                maxID = r.getId();
            }
        }
        newReservation.setId(maxID + 1);

        // Aggiungi la prenotazione alla lista di prenotazioni dell'utente
        currentUser.getReservations().add(newReservation);

        // Aggiungi la prenotazione alla lista globale
        reservationList.add(newReservation);

    } else {
        view.showMessage("Slot non disponibili!");
        }
    }


    // Metodo per aggiungere una prenotazione
    public  void addReservation(Reservation reservation) {
        reservationList.add(reservation);
    }

    // Metodo per ottenere tutte le prenotazioni
    public  List<Reservation> getAllReservations() {
        return new ArrayList<>(reservationList); // Ritorna una copia della lista per evitare modifiche esterne
    }

    // Metodo per resettare tutte le prenotazioni (facoltativo)
    public  void resetReservations() {
        reservationList.clear();
    }

    // Metodo per stampare tutte le prenotazioni
    public  void printAllReservations() {
        for (Reservation reservation : reservationList) {
            System.out.println(reservation);
        }
    }


    // ==============================
    // Data management methods
    // ==============================
    public void saveAll() {
        try {
            dataSaver.saveAll(chargingRateList, energySupplierList, chargingStationList, rewardList,
                              userList, vehicleList, transactionList, reservationList);
        } catch (IOException e) {
            view.showMessage("Errore durante il salvataggio: " + e.getMessage());
        }
    }

    public void readAll() {
        try {
            dataSaver.readAll(chargingRateList, energySupplierList, chargingStationList, rewardList,
                              userList, vehicleList, transactionList, reservationList);
        } catch (IOException e) {
            view.showMessage("Errore durante la lettura: " + e.getMessage());
        }
    }


    // ==============================
    // Debugging
    // ==============================


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



    // Metodo per resettare tutte le prenotazioni
    public void resettone() {
        ChargingStation.resetAllTimeTables(chargingStationList);
    }

    
}

