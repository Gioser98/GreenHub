package GreenHub;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Map;

public class View {

	private Scanner scanner = new Scanner(System.in);

	public View() {
		// Costruttore vuoto, puoi aggiungere logica se necessario
	}

	// Metodo generico per mostrare un messaggio
	public void showMessage(String message) {
		System.out.println(message);
	}

	// Metodo per ottenere l'ID della stazione dall'utente
	public int getStationIdFromUser() {
		System.out.print("Inserisci l'ID della stazione di ricarica scelta: ");
		return scanner.nextInt();
	}

	// Metodo per ottenere l'username dall'utente
	public String getInputUsername() {
		System.out.print("Inserisci il tuo username: ");
		return scanner.next();
	}

	// Metodo per ottenere il nome dall'utente
	public String getInputName() {
		System.out.print("Inserisci il tuo nome: ");
		return scanner.next();
	}

	// Metodo per ottenere il cognome dall'utente
	public String getInputSurname() {
		System.out.print("Inserisci il tuo cognome: ");
		return scanner.next();
	}

	// Metodo per stampare lo stato del veicolo
	public void showVehicleStatus(User user) {
		var vehicle = user.getPersonalVehicle();
		int batteryPercentage = (int)((vehicle.getBatteryLevel() / vehicle.getCapacity()) * 100);
		System.out.println("\nLa tua " + vehicle.getMaker() + " " + vehicle.getModel() +
				" ha una percentuale di carica pari a " + batteryPercentage + " %");
	}

	public void showWelcomeMenuOptions() {
		System.out.println("====== BENVENUTO IN GREENHUB ======");
		System.out.println("Tutti i dati verranno caricati dai file fra pochi secondi.");
		System.out.println("Per salvare le modifiche sul file, terminare il programma scegliendo " +
				"l'apposita opzione, altrimenti verranno perse.");
		System.out.println("1) Registrazione nuovo utente");
		System.out.println("2) Login utente già registrato");
		System.out.println("3) Exit");
		System.out.print("Scelta: ");
	}

	public String showDynamicMenuOptions(User user, Map<String, MainMenuStrategy> strategies) {
		System.out.println("\n=== Menù Opzioni ===");

		// Separiamo l'opzione "Esci" per mostrarla alla fine
		List<String> keys = new ArrayList<>(strategies.keySet());
		keys.remove("Esci"); // Rimuovi temporaneamente "Esci" per mostrarla in fondo

		// Rimuovi "Aggiungi un veicolo" se l'utente ha già un veicolo personale
		if (user.getPersonalVehicle() != null) {
			keys.remove("Aggiungi un veicolo");
		}

		int index = 1;
		for (String option : keys) {
			System.out.println(index + ") " + option);
			index++;
		}

		// Aggiungi "Esci" come ultima opzione
		System.out.println(index + ") Esci");

		System.out.print("Inserisci il numero dell'opzione scelta: ");
		int choiceIndex = scanner.nextInt() - 1;

		// Controlla se l'utente ha scelto "Esci" (ultima opzione)
		if (choiceIndex == keys.size()) {
			return "Esci";
		} else if (choiceIndex >= 0 && choiceIndex < keys.size()) {
			return keys.get(choiceIndex);
		} else {
			System.out.println("Scelta non valida. Riprova.");
			return showDynamicMenuOptions(user, strategies);
		}
	}

	public String choosePaymentMethod(Map<String, PaymentStrategy> paymentOptions) {
		System.out.println("Scegli un metodo di pagamento:");

		List<String> options = new ArrayList<>(paymentOptions.keySet());
		int index = 1;

		// Stampa le opzioni con indice numerico
		for (String option : options) {
			System.out.println(index + ". " + option);
			index++;
		}

		// Ottieni la scelta dell'utente
		System.out.print("\nInserisci il numero del metodo di pagamento scelto: ");
		int choiceIndex = scanner.nextInt() - 1;

		if (choiceIndex >= 0 && choiceIndex < options.size()) {
			return options.get(choiceIndex);
		} else {
			System.out.println("Scelta non valida. Riprova.");
			return choosePaymentMethod(paymentOptions);
		}
	}

	public void showUserRedeemedRewards(Map<String, String> redeemedRewardsMap, String username) {
		System.out.println("\n=== Le tue Ricompense ===");

		boolean hasRewards = false;
		for (Map.Entry<String, String> entry : redeemedRewardsMap.entrySet()) {
			if (entry.getValue().equals(username)) {
				System.out.println("- Codice: " + entry.getKey());
				hasRewards = true;
			}
		}

		if (!hasRewards) {
			System.out.println("Non hai ancora riscattato alcuna ricompensa.");
		}

		// Aggiungi opzione per tornare al menu principale
		System.out.println("\nPremi 0 per tornare al menu principale.");
		System.out.print("Scelta: ");

		// Acquisisci l'input dell'utente
		int choice = scanner.nextInt();

		// Se l'utente sceglie 0, torna al menu principale
		if (choice == 0) {
			return;  // Esce dal ciclo e ritorna al menu principale
		} else {
			System.out.println("Opzione non valida. Riprova.");
		}
	}

	// Simulazione della ricarica con una barra di avanzamento
	public void simulateCharging(User user) throws InterruptedException {
		int max = (int) user.getPersonalVehicle().getCapacity();
		int batteryLevel = (int) user.getPersonalVehicle().getBatteryLevel();
		int initialCharge = (int)((batteryLevel / (double) max) * 100);
		int targetCharge = 100;

		System.out.println("Inizio ricarica...\n");
		for (int i = initialCharge; i <= targetCharge; i++) {
			System.out.print("\r[");

			// Determina il colore in base alla percentuale
			String color;
			if (i <= 29) {
				color = "\u001B[31m"; // Rosso
			} else if (i <= 69) {
				color = "\u001B[33m"; // Giallo
			} else {
				color = "\u001B[32m"; // Verde
			}

			// Stampa la barra di avanzamento con il colore appropriato
			int progress = i / 10;
			for (int j = 0; j < 10; j++) {
				if (j < progress) {
					System.out.print(color + "#");
				} else {
					System.out.print(" ");
				}
			}
			System.out.print("\u001B[0m"); // Resetta il colore
			System.out.print("] " + i + "%");

			// Attesa per simulare il tempo di ricarica
			Thread.sleep(300);
		}
		System.out.println("\nRicarica completata.");
	}

	// Simula la connessione a un'app di navigazione
	public void simulateNavigationToStation(ChargingStation station) {
		System.out.println("Connessione a un'app di navigazione per arrivare a " + station.getOwner() + "...");
		System.out.println("Simulazione di navigazione in corso: segui le indicazioni per " + station.getLocation() + ".");

		try {
			Thread.sleep(5000); // Pausa di 5 secondi
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Sei arrivato alla stazione di ricarica " + station.getOwner() + ".");
	}

	// Guida l'utente nel collegamento della presa
	public void guidePlugInProcess() {
		System.out.println("Collega il cavo di ricarica al tuo veicolo.\n");
		try {
			Thread.sleep(3000); // Pausa di 3 secondi
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Cavo collegato correttamente. La ricarica inizierà a breve.\n");
	}

	// Guida l'utente nello scollegamento della presa dopo la ricarica
	public void guidePlugOutProcess() {
		System.out.println("La ricarica è completata. Scollega il cavo di ricarica dal tuo veicolo.\n");
		try {
			Thread.sleep(3000); // Pausa di 3 secondi
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Cavo scollegato correttamente. \n\n");
	}

	// Metodo per mostrare il menù delle ricompense e ottenere la scelta dell'utente
	public int showRewardsMenu(int greenPointsBalance, Map < String, RewardType > availableRewards) {
		System.out.println("\n=== Menù Riscatto Ricompense ===");
		System.out.println("Saldo punti attuale: " + greenPointsBalance);

		System.out.println("\nScegli una ricompensa da riscattare:");

		// Stampa tutte le ricompense disponibili con i punti necessari
		int index = 1;
		for (String rewardName: availableRewards.keySet()) {
			RewardType rewardType = availableRewards.get(rewardName);
			System.out.println(index + ". " + rewardName + " (" + rewardType.requiredPoints() + " punti)");
			index++;
		}
		System.out.println(index + ". Torna al menù principale");

		System.out.print("Inserisci il numero della tua scelta: ");
		return scanner.nextInt();
	}

	public void printTimeTableWithTimeSlots(List<TimeSlotStatus> timeSlotStatusList, int stationId) {
		System.out.println("Stato attuale della colonnina di ricarica (ID:" + stationId + "):");

		for (int i = 0; i < timeSlotStatusList.size(); i++) {
			TimeSlotStatus slot = timeSlotStatusList.get(i);

			System.out.printf("[Slot %d] %s-%s: %-14s ", slot.getSlotIndex(), slot.getStartTime(), slot.getEndTime(), slot.getStatus());

			if ((i + 1) % 4 == 0) {
				System.out.println();
			}
		}
		System.out.println();
	}

	// Metodi per stampare le varie liste
	public void printChargingRateList(ArrayList < ChargingRate > chargingRateList) {
		System.out.println("Charging Rate List: " + chargingRateList);
	}

	public void printEnergySupplierList(ArrayList < EnergySupplier > energySupplierList) {
		System.out.println("Energy Supplier List: " + energySupplierList);
	}

	public void printChargingStationList(ArrayList < ChargingStation > chargingStationList) {
		System.out.println("Charging Station List: " + chargingStationList);
	}

	public void printRewardList(ArrayList < Reward > rewardList) {
		System.out.println("Reward List: " + rewardList);
	}

	public void printUserList(ArrayList < User > userList) {
		System.out.println("User List: " + userList);
	}

	public void printVehicleList(ArrayList < Vehicle > vehicleList) {
		System.out.println("Vehicle List: " + vehicleList);
	}

	public void printTransactionList(ArrayList < Transaction > transactionList) {
		System.out.println("Transaction List: " + transactionList);
	}

	public void printReservationList(ArrayList < Reservation > reservationList) {
		System.out.println("Reservation List: " + reservationList);
	}

	public void printRedeemedRewardsMap(Map < String, String > redeemedRewardsMap) {
		if (redeemedRewardsMap == null || redeemedRewardsMap.isEmpty()) {
			System.out.println("La mappa delle ricompense riscattate è vuota.");
			return;
		}
		System.out.println("Contenuto della mappa delle ricompense riscattate:");
		for (Map.Entry < String, String > entry: redeemedRewardsMap.entrySet()) {
			System.out.println("Codice: " + entry.getKey() + " - Utente: " + entry.getValue());
		}
	}

	public String getCarMaker() {
		System.out.print("Inserisci la marca dell'auto: ");
		return scanner.next();
	}

	public String getCarModel() {
		System.out.print("Inserisci il modello dell'auto: ");
		return scanner.next();
	}

	public int getCarCapacity() {
		while (true) {
			System.out.print("Inserisci la capacità della batteria dell'auto: ");
			try {
				if (scanner.hasNextInt()) {
					return scanner.nextInt();
				} else {
					System.out.println("Input non valido. Per favore, inserisci un numero intero per la capacità.");
					scanner.next(); // Avanza oltre l'input non valido
				}
			} catch (InputMismatchException e) {
				System.out.println("Errore di input. Per favore, inserisci un numero intero.");
				scanner.nextLine(); // Pulisce il buffer nel caso di input errato
			}
		}
	}

	public int selectChargingRate(ArrayList < ChargingRate > chargingRates) {
		System.out.println("Seleziona il rate di ricarica supportato (ID): ");
		ChargingRate.printAll(chargingRates);
		return scanner.nextInt();
	}

	public void showInvalidChargingRateMessage() {
		System.out.println("Rate di ricarica selezionato non valido.");
	}
}