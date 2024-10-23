/* 
package GreenHub;


import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AdminMenu {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private ArrayList<ChargingStation> stations = new ArrayList<>();
    private ArrayList<Reservation> reservations = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private ArrayList<Charge> charges = new ArrayList<>();
    private ArrayList<EnergySupplier> suppliers = new ArrayList<>();
    private ArrayList<Reward> rewards = new ArrayList<>();

    private Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add an entity");
            System.out.println("2. Print all entities");
            System.out.println("3. Delete an entity");
            System.out.println("4. Clear all lists");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addEntity();
                    break;
                case 2:
                    printEntities();
                    break;
                case 3:
                    deleteEntity();
                    break;
                case 4:
                    clearAllLists();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        } while (choice != 0);
    }

    private void addEntity() {
        System.out.println("\n--- Add Entity ---");
        System.out.println("1. User");
        System.out.println("2. Vehicle");
        System.out.println("3. Charging Station");
        System.out.println("4. Reservation");
        System.out.println("5. Transaction");
        System.out.println("6. Charge");
        System.out.println("7. Energy Supplier");
        System.out.println("8. Reward");
        System.out.print("Choose an entity to add: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1:
                addUser();
                break;
            case 2:
                addVehicle();
                break;
            case 3:
                addChargingStation();
                break;
            case 4:
                addReservation();
                break;
            case 5:
                addTransaction();
                break;
            case 6:
                addCharge();
                break;
            case 7:
                addSupplier();
                break;
            case 8:
                addReward();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void printEntities() {
        System.out.println("\n--- Print Entities ---");
        System.out.println("1. Users");
        System.out.println("2. Vehicles");
        System.out.println("3. Charging Stations");
        System.out.println("4. Reservations");
        System.out.println("5. Transactions");
        System.out.println("6. Charges");
        System.out.println("7. Energy Suppliers");
        System.out.println("8. Rewards");
        System.out.print("Choose an entity to print: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1:
                printList(users);
                break;
            case 2:
                printList(vehicles);
                break;
            case 3:
                printList(stations);
                break;
            case 4:
                printList(reservations);
                break;
            case 5:
                printList(transactions);
                break;
            case 6:
                printList(charges);
                break;
            case 7:
                printList(suppliers);
                break;
            case 8:
                printList(rewards);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }


    private void deleteEntity() {
        System.out.println("\n--- Delete Entity ---");
        System.out.println("1. Delete a single entity");
        System.out.println("2. Clear an entire list");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline
    
        switch (choice) {
            case 1:
                deleteSingleEntity();
                break;
            case 2:
                clearEntireList();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
    
    private void deleteSingleEntity() {
        System.out.println("\n--- Select a List ---");
        System.out.println("1. User");
        System.out.println("2. Vehicle");
        System.out.println("3. Charging Station");
        System.out.println("4. Reservation");
        System.out.println("5. Transaction");
        System.out.println("6. Charge");
        System.out.println("7. Energy Supplier");
        System.out.println("8. Reward");
        System.out.print("Choose an entity type: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline
    
        switch (choice) {
            case 1:
                deleteFromList(users, "User");
                break;
            case 2:
                deleteFromList(vehicles, "Vehicle");
                break;
            case 3:
                deleteFromList(stations, "Charging Station");
                break;
            case 4:
                deleteFromList(reservations, "Reservation");
                break;
            case 5:
                deleteFromList(transactions, "Transaction");
                break;
            case 6:
                deleteFromList(charges, "Charge");
                break;
            case 7:
                deleteFromList(suppliers, "Energy Supplier");
                break;
            case 8:
                deleteFromList(rewards, "Reward");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
    
    private <T> void deleteFromList(ArrayList<T> list, String entityName) {
        if (list.isEmpty()) {
            System.out.println(entityName + " list is empty.");
            return;
        }
    
        System.out.println("\n--- " + entityName + " List ---");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + 1 + ". " + list.get(i));
        }
        
        System.out.print("Select the number of the " + entityName + " to delete: ");
        int index = scanner.nextInt();
        scanner.nextLine();  // Consume newline
    
        if (index > 0 && index <= list.size()) {
            list.remove(index - 1);  // Rimuove l'elemento selezionato
            System.out.println(entityName + " removed.");
        } else {
            System.out.println("Invalid selection.");
        }
    }
    
    private void clearEntireList() {
        System.out.println("\n--- Select a List to Clear ---");
        System.out.println("1. User");
        System.out.println("2. Vehicle");
        System.out.println("3. Charging Station");
        System.out.println("4. Reservation");
        System.out.println("5. Transaction");
        System.out.println("6. Charge");
        System.out.println("7. Energy Supplier");
        System.out.println("8. Reward");
        System.out.print("Choose a list to clear: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline
    
        switch (choice) {
            case 1:
                users.clear();
                System.out.println("User list cleared.");
                break;
            case 2:
                vehicles.clear();
                System.out.println("Vehicle list cleared.");
                break;
            case 3:
                stations.clear();
                System.out.println("Charging Station list cleared.");
                break;
            case 4:
                reservations.clear();
                System.out.println("Reservation list cleared.");
                break;
            case 5:
                transactions.clear();
                System.out.println("Transaction list cleared.");
                break;
            case 6:
                charges.clear();
                System.out.println("Charge list cleared.");
                break;
            case 7:
                suppliers.clear();
                System.out.println("Energy Supplier list cleared.");
                break;
            case 8:
                rewards.clear();
                System.out.println("Reward list cleared.");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
    

    private void clearAllLists() {
        users.clear();
        vehicles.clear();
        stations.clear();
        reservations.clear();
        transactions.clear();
        charges.clear();
        suppliers.clear();
        rewards.clear();
        System.out.println("All lists have been cleared.");
    }

    private <T> void printList(ArrayList<T> list) {
        if (list.isEmpty()) {
            System.out.println("List is empty.");
        } else {
            for (T item : list) {
                System.out.println(item);
            }
        }
    }

    // Methods to add entities
    private void addUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter green points balance: ");
        int greenPoints = scanner.nextInt();
        System.out.print("Enter user type (int): ");
        int type = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter first name: ");
        String name = scanner.nextLine();
        System.out.print("Enter surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter location latitude: ");
        int latitude = scanner.nextInt();
        System.out.print("Enter location longitude: ");
        int longitude = scanner.nextInt();
        Location location = new Location(latitude, longitude);  // Creazione dell'oggetto Location
        users.add(new User(username, greenPoints, type, name, surname, location));
        System.out.println("User added.");
    }

    private void addVehicle() {
        System.out.print("Enter vehicle ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter vehicle maker: ");
        String maker = scanner.nextLine();
        System.out.print("Enter vehicle model: ");
        String model = scanner.nextLine();
        System.out.print("Enter vehicle supported rate (ChargingRate enum): ");
        String rate = scanner.nextLine();  // Supponiamo che sia un enum, puoi cambiarlo se è diverso
        ChargingRate supportedRate = ChargingRate.valueOf(rate);  // Parsing dell'enum
        System.out.print("Enter vehicle location latitude: ");
        int latitude = scanner.nextInt();
        System.out.print("Enter vehicle location longitude: ");
        int longitude = scanner.nextInt();
        Location location = new Location(latitude, longitude);  // Creazione dell'oggetto Location
        System.out.print("Enter vehicle capacity: ");
        int capacity = scanner.nextInt();
        System.out.print("Enter vehicle battery level: ");
        int batteryLevel = scanner.nextInt();
        vehicles.add(new Vehicle(id, maker, model, supportedRate, location, capacity, batteryLevel));
        System.out.println("Vehicle added.");
    }

    private void addChargingStation() {
    System.out.println("\n--- Add Charging Station ---");

    // Generazione automatica di un ID casuale
    int id = generateRandomId();
    System.out.println("Generated ID for the station: " + id);

    // Inserimento della location
    System.out.print("Enter latitude for the location: ");
    int latitude = scanner.nextInt();
    System.out.print("Enter longitude for the location: ");
    int longitude = scanner.nextInt();
    scanner.nextLine();  // Consume newline
    Location location = new Location(latitude, longitude);

    // Selezione delle tariffe disponibili (ChargingRate)
    ArrayList<ChargingRate> selectedRates = new ArrayList<>();
    System.out.println("Select available charging rates:");
    for (ChargingRate rate : ChargingRate.values()) {
        System.out.println(rate.ordinal() + 1 + ". " + rate);
    }
    System.out.println("Enter the numbers of the rates separated by commas (e.g., 1,3): ");
    String[] selectedRateIndexes = scanner.nextLine().split(",");
    for (String rateIndex : selectedRateIndexes) {
        int rateNum = Integer.parseInt(rateIndex.trim()) - 1;
        if (rateNum >= 0 && rateNum < ChargingRate.values().length) {
            selectedRates.add(ChargingRate.values()[rateNum]);
        } else {
            System.out.println("Invalid rate number: " + rateNum);
        }
    }

    // Stato di manutenzione (maintenance) impostato come disponibile (false)
    boolean maintenance = false;

    // Selezione del proprietario (EnergySupplier)
    if (suppliers.isEmpty()) {
        System.out.println("No energy suppliers available. Cannot create charging station without a supplier.");
        return;
    }
    System.out.println("Select an energy supplier:");
    for (int i = 0; i < suppliers.size(); i++) {
        System.out.println((i + 1) + ". " + suppliers.get(i).getName());
    }
    System.out.print("Enter the number of the energy supplier: ");
    int supplierIndex = scanner.nextInt() - 1;
    scanner.nextLine();  // Consume newline
    if (supplierIndex >= 0 && supplierIndex < suppliers.size()) {
        EnergySupplier owner = suppliers.get(supplierIndex);

        // Time table (Non definito, uso un array di stringhe vuote)
        String[] timeTable = new String[0]; // Sostituiscilo con logica adeguata quando disponibile

        // Creazione della nuova stazione di ricarica
        ChargingStation newStation = new ChargingStation();
        stations.add(newStation);

        System.out.println("Charging station added successfully.");
    } else {
        System.out.println("Invalid energy supplier selection.");
    }
}

// Metodo per generare un ID casuale
private int generateRandomId() {
    Random rand = new Random();
    return rand.nextInt(10000);  // Genera un ID casuale tra 0 e 9999
}


    /* 
    private void addReservation() {
        System.out.print("Enter reservation ID: ");
        int id = scanner.nextInt();
        reservations.add(new Reservation(id));  // In base al costruttore che hai fornito
        System.out.println("Reservation added.");
    }
        */
      /*   
    private void addTransaction() {
        System.out.print("Enter transaction ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter transaction amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter transaction time (hh:mm:ss): ");
        String timeString = scanner.nextLine();
        Time timestamp = Time.valueOf(timeString);  // Parsing del tempo
        System.out.print("Enter charge ID (for this transaction): ");
        int chargeId = scanner.nextInt();
        Charge charge = findChargeById(chargeId);  // Trova il Charge (metodo di ricerca semplificato)
        scanner.nextLine();  // Consume newline
        System.out.print("Enter payment strategy (name of the strategy): ");
        String paymentStrategyName = scanner.nextLine();
        PaymentStrategy paymentStrategy = rewards.getStrategy(paymentStrategyName);  
        System.out.print("Enter user ID for transaction: ");
        int userId = scanner.nextInt();
        User user = findUserById(userId);  // Trova l'utente
        System.out.print("Enter vehicle ID for transaction: ");
        int vehicleId = scanner.nextInt();
        Vehicle vehicle = findVehicleById(vehicleId);  // Trova il veicolo
        transactions.add(new Transaction(id, timestamp, amount, charge, paymentStrategy, user, vehicle));
        System.out.println("Transaction added.");
    }
    /* 
    private void addCharge() {
        System.out.print("Enter charge ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter charge value: ");
        double value = scanner.nextDouble();
        charges.add(new Charge(id, value));
        System.out.println("Charge added.");
    }
    */
    /* 
    private void addSupplier() {
        System.out.print("Enter supplier name: ");
        String name = scanner.nextLine();
        suppliers.add(new EnergySupplier(name));
        System.out.println("Supplier added.");
    }

    /* 
    private void addReward() {
        System.out.print("Enter reward ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter reward points: ");
        int points = scanner.nextInt();
        rewards.add(new Reward(id, points));
        System.out.println("Reward added.");
    }
    */
/* 
    // Metodi helper per cercare entità (da implementare in base alla tua logica)
    private Charge findChargeById(int id) {
        for (Charge charge : charges) {
            if (charge.getId() == id) {
                return charge;
            }
        }
        return null;  // Se non trovato
    }

    private User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername() == username) {
                return user;
            }
        }
        return null;  // Se non trovato
    }

    private Vehicle findVehicleById(int id) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId() == id) {
                return vehicle;
            }
        }
        return null;  // Se non trovato
    }

    public static void main(String[] args) {
        AdminMenu menu = new AdminMenu();
        menu.showMenu();
    }
}
*/