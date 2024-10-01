package GreenHub;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChargingStation implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private Location location;
    private String[] timeTable;
    private ArrayList<ChargingRate> availableRates;
    private boolean maintenance;
    private EnergySupplier owner;
    private static ArrayList<ChargingStation> chargingStationList = new ArrayList<>();

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

    // Metodo statico per ottenere la lista
    public static ArrayList<ChargingStation> getChargingStationList() {
        return chargingStationList;
    }

    // Metodo statico per impostare la lista
    public static void setChargingStationList(ArrayList<ChargingStation> chargingStationList) {
        ChargingStation.chargingStationList = chargingStationList;
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

    public static void printAll(List<ChargingStation> chargingStationList) {
        int i = 1;
        for (ChargingStation cs : chargingStationList) {
            System.out.println(i + ") " + cs);
            i++;
        }
    }

    
    

    /* 
    public boolean isCompatibleWith(Vehicle vehicle) {
        for (ChargingRate rate : availableRates) {
            if (rate.getSocketType().equals(vehicle.getSocketType())) {
                return true;
            }
        }
        return false;
    }
     */

     public boolean isCompatibleWith(Vehicle vehicle) {
        // Rimuoviamo il controllo sul socket type
        return true; // La stazione è sempre compatibile
    }
    
    

    public static void getNearAvailableStation(User user, List<ChargingStation> chargingStationList) {
        Location userLocation = user.getPersonalVehicle().getLocation();
        System.out.println("Stazioni di ricarica disponibili vicino a " + userLocation + ":");
        for (ChargingStation cs : chargingStationList) {
            if (!cs.isMaintenance() && cs.isCompatibleWith(user.getPersonalVehicle())) {
                System.out.println(cs);
            }
        }
    }

    public static ChargingStation chooseStation(Vehicle vehicle, List<ChargingStation> chargingStationList) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci l'ID della stazione di ricarica scelta: ");
        int stationId = scanner.nextInt();

        for (ChargingStation cs : chargingStationList) {
            if (cs.getId() == stationId && cs.isCompatibleWith(vehicle) && !cs.isMaintenance()) {
                System.out.println("Hai scelto: " + cs);
                return cs;
            }
        }

        System.out.println("Stazione non disponibile o non compatibile.");
        return null;
    }

    public void printTimeTableWithTimeSlots() {
		System.out.println("Stato attuale della colonnina di ricarica:");

		for (int i = 0; i < timeTable.length; i++) {
			String startTime = formatTime(i * 30);
			String endTime = formatTime((i + 1) * 30);
			String slotStatus = (timeTable[i].isEmpty()) ? "Disponibile" : "Occupata";

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
}
