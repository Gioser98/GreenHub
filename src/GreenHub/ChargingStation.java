package GreenHub;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ChargingStation implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private Location location;
	private String[] timeTable;
	private ArrayList < ChargingRate > availableRates;
	private boolean maintenance;
	private EnergySupplier owner;

	// Costruttore
	public ChargingStation() {
		this.timeTable = new String[48]; // Inizializza il timeTable
		this.availableRates = new ArrayList < >(); // Inizializza la lista di tariffe
	}

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

	public ArrayList < ChargingRate > getAvailableRates() {
		return availableRates;
	}

	public void setAvailableRates(ArrayList < ChargingRate > availableRates) {
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

	// Nuovo metodo per ottenere il tasso di ricarica per il veicolo
	public double getChargingRateForVehicle(Vehicle vehicle) {
		if (availableRates.isEmpty()) {
			throw new IllegalArgumentException("Nessuna tariffa di ricarica disponibile.");
		}
		// Assumiamo di restituire la tariffa della prima opzione disponibile
		return availableRates.get(0).getRatePerKWh(); // Tariffa della prima tariffa disponibile
	}

	// Methods
	public String toString() {
		String text = "";
		if (maintenance) {
			text = ". Stazione in manutenzione!";
		}
		return "Stazione in " + location + " di proprietà di " + owner.getName() + " (ID:" + id + ") " + text;
	}

	public static void printAll(List < ChargingStation > chargingStationList) {
		int i = 1;
		for (ChargingStation cs: chargingStationList) {
			System.out.println(i + ") " + cs);
			i++;
		}
	}

	public boolean isCompatibleWith(Vehicle vehicle) {
		// Rimuoviamo il controllo sul socket type
		return true; // La stazione è sempre compatibile
	}

	public List < TimeSlotStatus > getTimeSlotStatus() {
		List < TimeSlotStatus > slotStatusList = new ArrayList < >();

		for (int i = 0; i < timeTable.length - 1; i++) {
			String startTime = formatTime(i * 30);
			String endTime = formatTime((i + 1) * 30);

			if (i * 30 >= 24 * 60) {
				break;
			}

			String status = (timeTable[i] == null || timeTable[i].isEmpty()) ? "Disponibile": "Occupata";
			slotStatusList.add(new TimeSlotStatus(i, startTime, endTime, status));
		}

		return slotStatusList;
	}

	// Metodo privato per formattare l'ora
	private String formatTime(int minutes) {
		int hours = minutes / 60;
		int mins = minutes % 60;
		return String.format("%02d:%02d", hours, mins);
	}

	
	// Nuovo metodo per verificare la disponibilità di uno slot
	public boolean isSlotAvailable(int slot) {
		if (slot < 0 || slot >= timeTable.length) {
			return false; // Se lo slot è fuori dai limiti, non è disponibile
		}
		return timeTable[slot] == null || timeTable[slot].isEmpty(); // Controlla se lo slot è vuoto
	}

	public void resetTimeTable() {
		for (int i = 0; i < timeTable.length; i++) {
			this.timeTable[i] = ""; // Imposta tutti gli slot come vuoti
		}

	}

	public static void resetAllTimeTables(List < ChargingStation > chargingStationList) {
		for (ChargingStation station: chargingStationList) {
			station.resetTimeTable();
		}
	}

	/**
	 * Metodo per verificare se la stazione di ricarica è occupata in base alle prenotazioni
	 * @param reservations lista di prenotazioni
	 * @return true se la stazione è occupata, altrimenti false
	 */
	public boolean isCurrentlyOccupied(List < Reservation > reservations, User currentUser) {
		LocalTime currentTime = LocalTime.now(); // Ottiene l'ora corrente

		for (Reservation reservation: reservations) {
			// Controlla se la stazione di ricarica corrisponde
			if (reservation.getChargingStation().getId() == this.id) {
				// Ottieni l'orario di inizio e di fine della prenotazione
				Time startTime = reservation.getStartTime();
				Time endTime = reservation.getEndTime();

				// Controlla se startTime e endTime sono validi
				if (startTime.getHour() < 0 || startTime.getHour() >= 24 || endTime.getHour() < 0 || endTime.getHour() >= 24) {
					throw new IllegalArgumentException("L'ora deve essere compresa tra 0 e 23");
				}

				LocalTime start = LocalTime.of(startTime.getHour(), startTime.getMinute());
				LocalTime end = LocalTime.of(endTime.getHour(), endTime.getMinute());

				// Verifica se l'ora corrente è compresa tra l'orario di inizio e fine della prenotazione
				if (!currentTime.isBefore(start) && !currentTime.isAfter(end)) {
					// Se l'ora corrente è tra l'orario di inizio e di fine, verifica lo username
					if (reservation.getUser().getUsername().equals(currentUser.getUsername())) {
						// Se l'utente loggato è lo stesso della prenotazione, si può procedere
						return false; // Non occupato, stesso utente
					} else {
						// Se l'utente è diverso, la stazione è occupata
						return true; // Occupato da un altro utente
					}
				}
			}
		}

		// Se nessuna condizione di occupazione è stata soddisfatta, la stazione non è occupata
		return false;
	}

}