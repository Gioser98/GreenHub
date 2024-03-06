package GreenHub;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class ChargingStation implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private Location location;
	private String[] timeTable;
	private ArrayList<ChargingRate> availableRates;
	private boolean maintenance;
	private EnergySupplier owner;

	// Getters&Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String[] getTimeTable() {
		return timeTable;
	}

	public void setTimeTable(String string, int pos) {
		this.timeTable[pos] = string;
	}

	public ArrayList<ChargingRate> getAvailableRates() {
		return availableRates;
	}

	public void setAvailableRates(ArrayList<ChargingRate> availableRates) {
		this.availableRates = availableRates;
	}

	public boolean isMaintenance() {
		return maintenance;
	}

	public void setMaintenance(boolean maintenance) {
		this.maintenance = maintenance;
	}

	public EnergySupplier getOwner() {
		return owner;
	}

	public void setOwner(EnergySupplier owner) {
		this.owner = owner;
	}

	// Constructors
	public ChargingStation() {
		this.timeTable = new String[48];
	}

	// Methods
	public String toString() {
		String text = "";
		if (maintenance) {
			text = ". Stazione in manutenzione!";
		}
		return "Stazione in " + location + " di proprietà di " + owner.getName() + " (ID:" + id + ") " + text;
	}

	public static void printAll(ArrayList<ChargingStation> chargingStationList) {
		int i = 1;
		for (ChargingStation cs : chargingStationList) {
			System.out.println(i + ") " + cs);
			i++;
		}
	}

	public boolean isCompatibleWithVehicle(Vehicle vehicle) {
		// Recupera la tariffa supportata dal veicolo
		ChargingRate carSupportedRate = vehicle.getSupportedRate();

		// Controlla se la tariffa supportata è presente tra le tariffe disponibili
		for (ChargingRate availableRate : availableRates) {
			if (availableRate.getId() == carSupportedRate.getId())
				return true;
		}

		// Se la tariffa non è stata trovata, restituisce False
		return false;
	}

	public boolean isAvailable(LocalDateTime startTime, LocalDateTime endTime) {
		System.out.println("startTime: " + startTime);
		System.out.println("endTime: " + endTime);
		System.out.println("timeTable: " + timeTable);

		return true;
	}

	public void printTimeTableWithTimeSlots() {
		System.out.println("Stato attuale della colonnina di ricarica:");

		for (int i = 0; i < timeTable.length; i++) {
			String startTime = formatTime(i * 30);
			String endTime = formatTime((i + 1) * 30);
			String slotStatus = (timeTable[i] == "") ? "Disponibile" : "Occupata";

			System.out.printf("[Slot%s] %s-%s: %-14s ", i, startTime, endTime, slotStatus);

			if ((i + 1) % 4 == 0) {
				System.out.println();
			}
		}
	}

	public static String formatTime(int minutes) {
		int hours = minutes / 60;
		int mins = minutes % 60;
		return String.format("%02d:%02d", hours, mins);
	}

	public void resetTimeTable() {
		for (int i = 0; i < 48; i++) {
			this.timeTable[i] = "";
		}
		System.out.println("Il vettore timeTable è stato azzerato.");
	}
	
	public static void getNearAvailableStation(User currentUser, ArrayList<ChargingStation> chargingStationList) {
		Scanner in = new Scanner(System.in);
		System.out.println("Dove ti trovi?");
		Location locCurrUser = new Location();
		System.out.print("X: ");
		locCurrUser.setLatitude(in.nextInt());
		System.out.print("Y: ");
		locCurrUser.setLongitude(in.nextInt());
		currentUser.setLocation(locCurrUser);
		System.out.print("Distanza massima della stazione: ");
		int range = in.nextInt();

		System.out.println("Ecco la lista delle stazioni disponibili intorno a te");
		for (ChargingStation cs : chargingStationList) {
			if (!cs.isMaintenance()) {
				if (cs.getLocation().distance(locCurrUser) < range) {
					System.out.println(cs + "- distanza: " + cs.getLocation().distance(locCurrUser));
				}
			}
		}
	}
	
	public static ChargingStation chooseStation(Vehicle currentVehicle, ArrayList<ChargingStation> chargingStationList) {
		Scanner in = new Scanner(System.in);
		ChargingStation currentCS = null;
		System.out.print("Inserisci l'ID della stazione dove vuoi effettuare la ricarica: ");
		int csID = in.nextInt();
		while (true) {
			if (chargingStationList.get(csID - 1).isCompatibleWithVehicle(currentVehicle)) {
				currentCS = chargingStationList.get(csID);
				break;
			} else {
				System.out.print("La stazione scelta non è compatibile con il tuo veicolo. Scegline un'altra: ");
				csID = in.nextInt();
			}
		}
		return currentCS;
	}
}