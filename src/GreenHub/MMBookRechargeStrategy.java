package GreenHub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MMBookRechargeStrategy implements MainMenuStrategy {
	@Override
	public void execute(UserInterface ui, User user) {

		ChargingStation currentCS; 
        Vehicle currentVehicle = user.getPersonalVehicle(); // Ottieni il veicolo personale dell'utente
        List<ChargingStation> chargingStationList = ui.getController().getChargingStationList();
		ArrayList<Reservation> reservationList = new ArrayList<>();
        Scanner in = ui.getScanner(); // Assumi che UserInterface fornisca un oggetto Scanner
		

		//if (currentVehicle == null || currentVehicle.getType() != 0) {  // Verifica che l'utente abbia un veicolo elettrico
        //	System.out.println("Funzione non disponibile! Devi avere un veicolo elettrico.");
        //	return;
		//}
		ChargingStation.getNearAvailableStation(user, chargingStationList);
		currentCS = ChargingStation.chooseStation(currentVehicle, chargingStationList);
		currentCS.printTimeTableWithTimeSlots();

		System.out.print("Quali slot vuoi prenotare? Inseriscili nella forma 14-18: ");
		String[] slot = in.next().split("-");
		int startingSlot = Integer.parseInt(slot[0]);
		int endingSlot = Integer.parseInt(slot[1]);
		boolean slotAvailable = true;

		slotAvailable = checkSlotAvailability(currentCS, startingSlot, endingSlot, slotAvailable);
		Reservation.reserveSlot(user, currentVehicle, currentCS, startingSlot, endingSlot, slotAvailable,
				reservationList);
	}

	private static boolean checkSlotAvailability(ChargingStation currentCS, int startingSlot, int endingSlot,
			boolean slotAvailable) {
		for (int j = startingSlot; j < endingSlot; j++) {
			if (!currentCS.getTimeTable()[j].equals("")) {
				slotAvailable = false;
			}
		}
		return slotAvailable;
	}
	
}