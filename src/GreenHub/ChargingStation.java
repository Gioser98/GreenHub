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

    public static void printAll(List<ChargingStation> chargingStationList) {
        int i = 1;
        for (ChargingStation cs : chargingStationList) {
            System.out.println(i + ") " + cs);
            i++;
        }
    }

    public boolean isCompatibleWith(Vehicle vehicle) {
        for (ChargingRate rate : availableRates) {
            if (rate.getSocketType().equals(vehicle.getSocketType())) {
                return true;
            }
        }
        return false;
    }

    public static void getNearAvailableStation(User user, List<ChargingStation> chargingStationList) {
        Location userLocation = user.getPersonalVehicle().getPosition();
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
}
