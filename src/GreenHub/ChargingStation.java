package GreenHub;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChargingStation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private Location location;
    private String[] timeTable = new String[48];
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
	public void setTimeTable(String[] timeTable) {
		this.timeTable = timeTable;
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
	    // Costruttore vuoto
	}
	public ChargingStation(int id, Location location, ArrayList<ChargingRate> availableRate, boolean maintenance, 
			EnergySupplier owner) {
	    this.id = id;
	    this.location = location;
	    // this.timeTable = 0; Azzerare il timetable?
	    this.availableRates = availableRate;
	    this.maintenance = maintenance;
	    this.owner = owner;
	}
	
	// Methods
	public String toString() {
		String text = "";
		if (maintenance) {
			text = ". Stazione in manutenzione!";
		}
		return "Stazione in " + location + " di proprietà di " + owner.getName() + " (ID:" + id + ") " + text ;
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
		System.out.println("startTime: "+ startTime);
		System.out.println("endTime: "+ endTime);
		System.out.println("timeTable: "+ timeTable);
		
		return true;
	}
	
	// metodo per mostrare la timetable senza gli slot occupati
	
}