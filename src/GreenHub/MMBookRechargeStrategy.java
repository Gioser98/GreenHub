package GreenHub;

import java.util.Scanner;

public class MMBookRechargeStrategy implements MainMenuStrategy {
	@Override
	public void execute(UserInterface ui, User user) {

		ChargingStation currentCS; 
        Vehicle currentVehicle = user.getPersonalVehicle(); // Ottieni il veicolo personale dell'utente
        Scanner in = ui.getScanner(); // Assumi che UserInterface fornisca un oggetto Scanner
		

		//if (currentVehicle == null || currentVehicle.getType() != 0) {  // Verifica che l'utente abbia un veicolo elettrico
        //	System.out.println("Funzione non disponibile! Devi avere un veicolo elettrico.");
        //	return;
		//}
		ui.getController().getNearAvailableStation(user);
		currentCS = ui.getController().chooseStation(currentVehicle);
		currentCS.printTimeTableWithTimeSlots();

		System.out.print("Quali slot vuoi prenotare? Inseriscili nella forma 14-18: ");
		String[] slot = in.next().split("-");
		int startingSlot = Integer.parseInt(slot[0]);
		int endingSlot = Integer.parseInt(slot[1]);
		boolean slotAvailable = true;

		slotAvailable = checkSlotAvailability(currentCS, startingSlot, endingSlot, slotAvailable);
		ui.getController().reserveSlot(user, currentVehicle, currentCS, startingSlot, endingSlot);

		 // **Assegnazione Green Points per la prenotazione**
		GreenPointsStrategy gpStrategy = new GPReservationStrategy();  // Usa la strategia di prenotazione
		int value = 1;  // Per la prenotazione, possiamo usare un valore fisso

		// Calcola i punti verdi usando la strategia
		int greenPoints = gpStrategy.calculatePoints(value);

		// Usa il metodo del controller per assegnare i punti
		ui.getController().assignGreenPoints(user, gpStrategy, value);

		// Stampa il messaggio con i punti verdi calcolati
		System.out.println("Prenotazione completata. Hai guadagnato " + greenPoints + " Green Point!");

		ui.getController().saveAll();
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