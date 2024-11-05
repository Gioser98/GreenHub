package GreenHub;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataManager {
	// Liste gestite internamente
	private ArrayList<ChargingRate> chargingRateList = new ArrayList<>();
	private ArrayList<EnergySupplier> energySupplierList = new ArrayList<>();
	private ArrayList<ChargingStation> chargingStationList = new ArrayList<>();
	private ArrayList<Reward> rewardList = new ArrayList<>();
	private ArrayList<User> userList = new ArrayList<>();
	private ArrayList<Vehicle> vehicleList = new ArrayList<>();
	private ArrayList<Transaction> transactionList = new ArrayList<>();
	private ArrayList<Reservation> reservationList = new ArrayList<>();
	private Map<String, String> redeemedRewardsMap = new HashMap<>();

	public DataManager() {
		// Costruttore vuoto
	}

	// Getter per accedere alle liste dal Controller
	public ArrayList<ChargingRate> getChargingRateList() { return chargingRateList; }
	public ArrayList<EnergySupplier> getEnergySupplierList() { return energySupplierList; }
	public ArrayList<ChargingStation> getChargingStationList() { return chargingStationList; }
	public ArrayList<Reward> getRewardList() { return rewardList; }
	public ArrayList<User> getUserList() { return userList; }
	public ArrayList<Vehicle> getVehicleList() { return vehicleList; }
	public ArrayList<Transaction> getTransactionList() { return transactionList; }
	public ArrayList<Reservation> getReservationList() { return reservationList; }
	public Map<String, String> getRedeemedRewardsMap() { return redeemedRewardsMap; }

	// Metodo per salvare tutte le liste su file
	public void saveAll() throws IOException {
		saveObject(chargingRateList, "ChargingRate.txt");
		saveObject(energySupplierList, "EnergySupplier.txt");
		saveObject(chargingStationList, "ChargingStation.txt");
		saveObject(rewardList, "Reward.txt");
		saveObject(userList, "User.txt");
		saveObject(vehicleList, "Vehicle.txt");
		saveObject(transactionList, "Transaction.txt");
		saveObject(reservationList, "Reservation.txt");
		saveObject(redeemedRewardsMap, "RedeemedRewardsMap.txt");
		System.out.println("\n----------------------------------------------------------------");
		System.out.println("Tutti i dati sono stati correttamente salvati su file.");
		System.out.println("----------------------------------------------------------------");
	}

	private void saveObject(Object obj, String filename) throws IOException {
		try (FileOutputStream fos = new FileOutputStream(filename);
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(obj);
		}
	}

	// Metodo per leggere tutte le liste dai file
	@SuppressWarnings("unchecked")
	public void readAll() throws IOException {
		chargingRateList = (ArrayList<ChargingRate>) readObject("ChargingRate.txt");
		energySupplierList = (ArrayList<EnergySupplier>) readObject("EnergySupplier.txt");
		chargingStationList = (ArrayList<ChargingStation>) readObject("ChargingStation.txt");
		rewardList = (ArrayList<Reward>) readObject("Reward.txt");
		userList = (ArrayList<User>) readObject("User.txt");
		vehicleList = (ArrayList<Vehicle>) readObject("Vehicle.txt");
		transactionList = (ArrayList<Transaction>) readObject("Transaction.txt");
		reservationList = (ArrayList<Reservation>) readObject("Reservation.txt");
		redeemedRewardsMap = (Map<String, String>) readObject("RedeemedRewardsMap.txt");
	}

	private Object readObject(String filename) throws IOException {
		try (FileInputStream fis = new FileInputStream(filename);
				ObjectInputStream ois = new ObjectInputStream(fis)) {
			return ois.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException("Errore durante la lettura dell'oggetto dal file: " + filename, e);
		}
	}
}
