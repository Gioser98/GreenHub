package GreenHub;

import java.io.IOException;
import java.util.Scanner;

public class MMRegisterCarStrategy implements MainMenuStrategy {
	@Override
	public void execute(UserInterface ui, User user) throws IOException {
		Scanner scanner = ui.getScanner();
		Controller controller = ui.getController();

		System.out.print("Inserisci il maker dell'auto: ");
		String maker = scanner.next();
		System.out.print("Inserisci il modello dell'auto: ");
		String model = scanner.next();
		System.out.print("Inserisci il tipo dell'auto (0: Elettrica, 1: Ibrida, 2: Combustione): ");
		int type = scanner.nextInt();
		System.out.print("Inserisci la capacità dell'auto: ");
		int capacity = scanner.nextInt();

		// Mostra la lista dei ChargingRate disponibili e chiedi all'utente di selezionarne uno
		System.out.println("Seleziona il rate di ricarica supportato (ID): ");
		ChargingRate.printAll(controller.getChargingRateList()); // Mostra la lista dei rate di ricarica
		int selectedRateId = scanner.nextInt();

		// Trova il ChargingRate corrispondente all'ID selezionato
		ChargingRate selectedRate = null;
		for (ChargingRate rate : controller.getChargingRateList()) {
			if (rate.getId() == selectedRateId) {
				selectedRate = rate;
				break;
			}
		}

		if (selectedRate == null) {
			System.out.println("Rate di ricarica selezionato non valido.");
			return;
		}

		// Usa la posizione dell'utente per il veicolo
		Location location = user.getLocation();

		// Crea un nuovo veicolo
		Vehicle vehicle = new Vehicle(0, maker, model, selectedRate, location, capacity);

		// Aggiungi il veicolo al controller
		controller.addVehicle(user, vehicle);

		// Salva lo stato aggiornato dei dati
        controller.saveAll(); // Aggiunta questa chiamata per salvare i dati

	}
}
