package GreenHub;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.NetworkChannel;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
//import java.util.ResourceBundle.Control;
import java.util.Scanner;



public class Controller {
	public ArrayList<ChargingRate> chargingRateList = new ArrayList<ChargingRate>();
	public ArrayList<EnergySupplier> energySupplierList = new ArrayList<EnergySupplier>();
	public ArrayList<ChargingStation> chargingStationList = new ArrayList<ChargingStation>();
	public ArrayList<Reward> rewardList = new ArrayList<Reward>();
	public ArrayList<User> userList = new ArrayList<User>();
	public ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();
	public ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
	public ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
	//public DataSaver dataSaver;

	public Controller(){
		//
	}

	// User methods
	public void addUser(User user) {
		if (getUserByUsername(user.getUsername()) == null) {
			userList.add(user);
		} else {
			System.out.println("Username già esistente.");
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
		System.out.println("Saldo GP aggiornato correttamente!");
	}


	// Transaction methods
	public String registerTransaction(User user, Vehicle vehicle, ChargingStation chargingStation, Charge charge, double amount, PaymentStrategy strategy, int paymentType) {
		// Crea una nuova transazione
		Transaction transaction = new Transaction();
	
		try {
			// Processa il pagamento usando la strategia selezionata
			strategy.pay(amount);
			
			// Imposta i dettagli della transazione
			transaction.setUser(user);
			transaction.setVehicle(vehicle);
			transaction.setCharge(charge);
			transaction.setAmount(amount);
			transaction.setPaymentStrategy(strategy);
			transaction.setType(paymentType); // Imposta il tipo di pagamento (es. carta di credito, PayPal, ecc.)
	
			// Crea un timestamp basato sull'ora corrente
			LocalDateTime now = LocalDateTime.now();
			Time timestamp = new Time(now.getHour(), now.getMinute()); 
			transaction.setTimestamp(timestamp);
	
			// Genera un ID unico per la transazione
			int newId = transactionList.isEmpty() ? 1 : transactionList.get(transactionList.size() - 1).getId() + 1;
			transaction.setId(newId);
	
			// Aggiungi la transazione alla lista delle transazioni
			transactionList.add(transaction);
	
			// Mostra un messaggio di successo
			System.out.println("Pagamento completato con successo!");
			return "Pagamento effettuato con successo e transazione registrata.";
	
		} catch (Exception e) {
			// In caso di errore, mostra un messaggio e non registra la transazione
			return "Errore durante il pagamento: " + e.getMessage();
		}
	}
	
	
	

	// Reward methods

	public String calculateGreenPoints(int value, String strategy) {

		GreenPointsStrategy greenPointsStrategy;

		// Seleziona la strategia in base al parametro 'strategy'
		switch (strategy.toLowerCase()) {
		case "reservation":
			greenPointsStrategy = new GPReservationStrategy();
			break;
		case "recharge":
			greenPointsStrategy = new GPRechargeStrategy();
			break;
		default:
			return "Errore: Strategia di calcolo dei punti non supportata.";
		}

		// Crea un oggetto Reward con la strategia selezionata
		Reward reward = new Reward(greenPointsStrategy);
		int points = reward.calculateGreenPoints(value);

		return "Green points calcolati: " + points;
	}


	// Reservation methods
	public void reserveSlot(User currentUser, Vehicle currentVehicle, ChargingStation currentCS, int startingSlot, int endingSlot) {
		boolean slotAvailable = true; // Logica per verificare la disponibilità dello slot
		Reservation.reserveSlot(currentUser, currentVehicle, currentCS, startingSlot, endingSlot, slotAvailable, reservationList);
	}


	// Vehicle methods
	public void addVehicle(User owner, Vehicle vehicle) {
		if (owner == null || vehicle == null) {
			throw new IllegalArgumentException("Owner or vehicle cannot be null");
		}

		// Imposta il proprietario del veicolo
		vehicle.setOwner(owner);

		// Aggiungi il veicolo alla lista
		vehicleList.add(vehicle);

		// Aggiorna il veicolo dell'utente se necessario
		if (owner.getPersonalVehicle() == null) {
			owner.setPersonalVehicle(vehicle);
		}

		System.out.println("Veicolo registrato correttamente!");
	}

	




	// ChargingRate methods
	public ArrayList<ChargingRate> getChargingRateList() {
		return chargingRateList;
	}


	// EnergySupplier methods

	
	// ChargingStation methods
	
	// Metodo per ottenere la lista delle stazioni di ricarica
	public ArrayList<ChargingStation> getChargingStationList() {
    return chargingStationList;
	}


	public double calculateRechargeCost(Vehicle vehicle, ChargingStation chargingStation) {
        double batteryCapacity = vehicle.getCapacity(); // Capacità della batteria in kWh
        double currentBatteryLevel = vehicle.generateRandomBatteryLevel(); // Usa il metodo per ottenere il livello di batteria
        double chargingRate = chargingStation.getChargingRateForVehicle(vehicle); // Tariffa per kWh della stazione
		System.out.println(batteryCapacity);
		System.out.println(currentBatteryLevel);
        // Calcola l'energia ricaricabile
        double energyToRecharge = batteryCapacity - currentBatteryLevel;
        if (energyToRecharge <= 0) {
            System.out.println("La batteria è già carica o sopra il massimo.");
            return 0; // Non serve ricaricare
        }

        // Calcola il costo totale della ricarica
        double cost = energyToRecharge * chargingRate;
        return cost; // Ritorna l'importo da utilizzare nel metodo di registrazione
    }
	

	public void updateBatteryLevel(User user) {
		Vehicle vehicle = user.getPersonalVehicle(); // Supponendo che tu abbia un metodo per ottenere il veicolo
		if (vehicle != null) {
			double newBatteryLevel = user.getPersonalVehicle().generateRandomBatteryLevel(); // Usa il tuo metodo per generare un livello casuale
			vehicle.setBatteryLevel(newBatteryLevel);
		} else {
			System.out.println("Nessun veicolo associato a questo utente.");
		}
	}
	
	
	

	public void registerCharge(User user, Vehicle vehicle, ChargingStation cs, LocalDateTime currentTime, Charge newCharge, Time startTime) {
		newCharge.setChargingStation(cs);
		newCharge.setVehicle(vehicle);
		newCharge.setChargingRate(vehicle.getSupportedRate());
		newCharge.setUser(user);
		newCharge.setId(0); //chiedere a Skabboz!!!
	
		startTime.setHour(currentTime.getHour());
		startTime.setMinute(currentTime.getMinute());
	
		float timeToCharge = vehicle.getCapacity() / vehicle.getSupportedRate().getPower();
		int hour = (int) timeToCharge;
		int minute = (int) (timeToCharge * 60) % 60; // Modifica per ottenere i minuti corretti
		int endHour = startTime.getHour() + hour;
		int endMinute = startTime.getMinute() + minute;
	
		// Gestisci il caso in cui i minuti superano 59
		if (endMinute >= 60) {
			endHour += endMinute / 60;
			endMinute = endMinute % 60;
		}
	
		newCharge.setStartTime(startTime);
		newCharge.setEndTime(new Time(endHour, endMinute));
	
		// Calcola il costo della ricarica e impostalo nel nuovoCharge
		double cost = calculateRechargeCost(vehicle, cs);
		newCharge.setCost(cost); 
	}
	


		
	

	/* 
    public void registerTransaction(User user, Vehicle vehicle, LocalDateTime currentTime, Charge newCharge) {
        // Logica per registrare la transazione
        System.out.println("Transazione registrata con successo. Ma penso che sia meglio collocare questo metodo nel controller");
    }
	*/

/*

	private static void registerTransaction(User currentUser, Vehicle currentVehicle, LocalTime currentTime,
			Charge newCharge, Transaction newTransaction) {
		double chargeAmount = currentVehicle.getCapacity() * currentVehicle.getSupportedRate().getPrice();
		System.out.println("Il totale è " + chargeAmount + "€. Come vuoi pagare?");
		System.out.println("Inserisci 0 per contanti");
		System.out.println("Inserisci 1 per carta di credito");
		System.out.println("Inserisci 2 per carta di debito");
		System.out.print("Scelta: ");

		
		newTransaction.setType(in.nextInt());
		newTransaction.setCharge(newCharge);
		newTransaction.setAmount(chargeAmount);
		newTransaction.setTimestamp(new Time(currentTime.getHour(), currentTime.getMinute()));
		int maxID = 0;
		for (Transaction t : transactionList) {
			if (t.getId() > maxID) {
				maxID = t.getId();
			}
		}
		newTransaction.setId(maxID + 1);
		transactionList.add(newTransaction);

		
		int newPoints = (int) (chargeAmount * currentRewardSystem.getRechargeFactor());
		currentUser.increaseGPBalance(newPoints);
		System.out.println("Ricarica effettuata! Con questa ricarica hai guadagnato " + newPoints + " punti.");
		 

	
	}

 */
	
	



	






	//Metodo per salvare tutti i dati su file
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

		/* 
        // Salvataggio di RewardSystem
        try (FileOutputStream rewardSystemFOS = new FileOutputStream(new File("RewardSystem.txt"));
             ObjectOutputStream rewardSystemOOS = new ObjectOutputStream(rewardSystemFOS)) {
            rewardSystemOOS.writeObject(currentRewardSystem);
        }*/


		System.out.println("\n\n----------------------------------------------------------------");
		System.out.println("Tutti i dati sono stati correttamente salvati su file.");
		System.out.println("----------------------------------------------------------------");
	}




	// Metodo per caricare tutti i dati dai file
	@SuppressWarnings("unchecked")
	public void readAll() throws FileNotFoundException, IOException {
		// Lettura chargingRateList
		FileInputStream FIS = new FileInputStream("ChargingRate.txt");
		ObjectInputStream OIS = new ObjectInputStream(FIS);
		try {
			chargingRateList = (ArrayList<ChargingRate>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Lettura energySupplierList
		FIS = new FileInputStream("EnergySupplier.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			energySupplierList = (ArrayList<EnergySupplier>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Lettura chargingStationList
		FIS = new FileInputStream("ChargingStation.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			chargingStationList = (ArrayList<ChargingStation>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Lettura rewardList
		FIS = new FileInputStream("Reward.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			rewardList = (ArrayList<Reward>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Lettura userList
		FIS = new FileInputStream("User.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			userList = (ArrayList<User>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Lettura vehicleList
		FIS = new FileInputStream("Vehicle.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			vehicleList = (ArrayList<Vehicle>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Lettura transactionList
		FIS = new FileInputStream("Transaction.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			transactionList = (ArrayList<Transaction>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Lettura reservationList
		FIS = new FileInputStream("Reservation.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			reservationList = (ArrayList<Reservation>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		/*  Lettura rewardSystem
		FIS = new FileInputStream("RewardSystem.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			currentRewardSystem = (RewardSystem) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		 */

	}

	// Metodo per stampare tutti i dati
	public void printino() {

		System.out.println("Charging Rate List: " + chargingRateList);
		System.out.println("Energy Supplier List: " + energySupplierList);
		System.out.println("Charging Station List: " + chargingStationList);
		System.out.println("Reward List: " + rewardList);
		System.out.println("User List: " + userList);
		System.out.println("Vehicle List: " + vehicleList);
		System.out.println("Transaction List: " + transactionList);
		System.out.println("Reservation List: " + reservationList);
		//.out.println("Reward System: " + currentRewardSystem);
	}

}