package GreenHub;

import java.util.ArrayList;
import java.util.List;

public class C_ReservationController {
	private DataManager dataSaver;
	private View view;

	public C_ReservationController(DataManager dataSaver, View view) {
		this.dataSaver = dataSaver;
		this.view = view;
	}

	public void reserveSlot(User currentUser, Vehicle currentVehicle, ChargingStation currentCS, int startingSlot, int endingSlot) {
		boolean slotAvailable = true;

		// Controlla la disponibilità degli slot
		for (int j = startingSlot; j < endingSlot; j++) {
			if (!currentCS.isSlotAvailable(j)) {
				slotAvailable = false;
				break;
			}
		}

		if (slotAvailable) {
			for (int j = startingSlot; j < endingSlot; j++) {
				currentCS.setTimeTable(currentUser.getUsername(), j);
			}

			view.showMessage("Slot prenotati!");

			// Crea una nuova prenotazione
			Reservation newReservation = new Reservation();
			newReservation.setUser(currentUser);
			newReservation.setVehicle(currentVehicle);
			newReservation.setChargingStation(currentCS);
			newReservation.setStartTime(new Time(startingSlot * 30 / 60, startingSlot * 30 % 60));
			newReservation.setEndTime(new Time(endingSlot * 30 / 60, endingSlot * 30 % 60));

			int maxID = dataSaver.getReservationList().stream()
					.mapToInt(Reservation::getId)
					.max()
					.orElse(0);
			newReservation.setId(maxID + 1);

			currentUser.getReservations().add(newReservation);
			dataSaver.getReservationList().add(newReservation);

		} else {
			view.showMessage("Slot non disponibili!");
		}
	}

	public boolean reserveSlotIfAvailable(User user, Vehicle vehicle, ChargingStation station, int startSlot, int endSlot) {
		for (int j = startSlot; j < endSlot; j++) {
			if (!station.getTimeTable()[j].isEmpty()) {
				return false; // Slot non disponibile
			}
		}

		reserveSlot(user, vehicle, station, startSlot, endSlot);
		return true;
	}

	public List<Reservation> getAllReservations() {
		return new ArrayList<>(dataSaver.getReservationList());
	}
}
