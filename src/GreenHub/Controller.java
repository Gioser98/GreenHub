package GreenHub;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
//import java.util.ResourceBundle.Control;



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
	public String pay(double amount, String method, String email, String password, String cardNumber, String expirationDate, String cvv)
	{

		PaymentStrategy strategy;

		switch (method.toLowerCase()) {
		case "paypal":
			strategy = new PPayPalStrategy(email, password);
			break;
		case "creditcard":
			strategy = new PCreditCardStrategy(cardNumber, expirationDate, cvv);
			break;
		default:
			return "Errore: Metodo di pagamento non supportato.";
		}

		Transaction transaction = new Transaction(strategy);

		try {
			transaction.processPayment(amount);
			return "Pagamento effettuato con successo";
		} catch (Exception e){
			return "Errore durante il pagamento" + e.getMessage();
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




	public void registerCharge(User user, Vehicle vehicle, ChargingStation cs, LocalDateTime currentTime, Charge newCharge, Time startTime) {
        newCharge.setChargingStation(cs);
		newCharge.setVehicle(vehicle);
		newCharge.setChargingRate(vehicle.getSupportedRate());
		newCharge.setUser(user);
		newCharge.setEnergy(vehicle.getCapacity());
		newCharge.setId(0); //chiedere a Skabboz!!!

		startTime.setHour(currentTime.getHour());
		startTime.setMinute(currentTime.getMinute());
		float timeToCharge = vehicle.getCapacity() / vehicle.getSupportedRate().getPower();
		int hour = (int) timeToCharge;
		int minute = (int) (timeToCharge - hour * 60);
		int endHour = startTime.getHour() + hour;
		int endMinute = startTime.getMinute() + minute;
		if (endMinute > 59) {
			endHour = endHour + 1;
			endMinute = endMinute - 60;
		}
		newCharge.setStartTime(startTime);
		newCharge.setEndTime(new Time(endHour, endMinute));
    }


		
	


    public void registerTransaction(User user, Vehicle vehicle, LocalDateTime currentTime, Charge newCharge) {
        // Logica per registrare la transazione
        System.out.println("Transazione registrata con successo. Ma penso che sia meglio collocare questo metodo nel controller");
    }

	



	






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