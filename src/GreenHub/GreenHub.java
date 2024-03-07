package GreenHub;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class GreenHub {

	private static Scanner in = new Scanner(System.in);
	private static ArrayList<ChargingRate> chargingRateList = new ArrayList<ChargingRate>();
	private static ArrayList<EnergySupplier> energySupplierList = new ArrayList<EnergySupplier>();
	private static ArrayList<ChargingStation> chargingStationList = new ArrayList<ChargingStation>();
	private static ArrayList<Reward> rewardList = new ArrayList<Reward>();
	private static ArrayList<User> userList = new ArrayList<User>();
	private static ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();
	private static ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
	private static ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
	private static RewardSystem currentRewardSystem = new RewardSystem();

	public static void main(String[] args) throws IOException {

		System.out.println("-------------BENVENUTO IN GREENHUB-------------");
		System.out.println("Tutti i dati verranno caricati dai file fra pochi secondi.");
		System.out.println("Per salvare le modifiche sul file, terminare il programma scegliendo "
				+ "l'apposita opzione, altrimenti verranno perse.");

		readAll();

		boolean adminMode = false;

		System.out
				.print("Se non sei registrato, inserisci 0 per registrarti, altrimenti inserisci il tuo nome utente: ");
		String currentUsername = in.next();

		if (currentUsername.equals("0")) {
			System.out.println("Benvenuto al processo di registrazione per GreenHub!");

			User newUser = new User();
			System.out.print("Inserisci il tuo nome: ");
			newUser.setName(in.next());
			System.out.print("Inserisci il tuo cognome: ");
			newUser.setSurname(in.next());

			boolean nameOk = false;
			String username;
			do {
				System.out.print("Scegli ora il tuo username: ");
				username = in.next();
				nameOk = true;

				for (User u : userList) {
					if (u.getUsername().equals(username)) {
						System.out.println("L'username inserito non è disponibile!");
						nameOk = false;
						break;
					}
				}
			} while (!nameOk);

			newUser.setUsername(username);
			System.out.println("Se hai un veicolo elettrico, inserisci 0");
			System.out.println("Se hai un veicolo a combustione interna, inserisci 1");
			System.out.println("Se non hai un veicolo e vuoi usufruire del car sharing, inserisci 2");
			System.out.print("Scelta: ");
			newUser.setType(in.nextInt());
			newUser.setGreenPointsBalance(0);
			userList.add(newUser);

			// Aggiungi il veicolo dell'utente solo se ha selezionato di averlo
			if (newUser.getType() != 2) {
				System.out.println("Utente registrato correttamente! Andiamo ora ad aggiungere il tuo veicolo.");

				Vehicle newVehicle = new Vehicle();
				System.out.print("Inserisci la marca: ");
				newVehicle.setMaker(in.next());

				System.out.print("Inserisci il modello: ");
				in.nextLine();
				newVehicle.setModel(in.nextLine());

				System.out.print("Capacità della batteria (in kWh): ");
				newVehicle.setCapacity(in.nextInt());

				System.out.print("Che tipologia di veicolo è? 0 Electric - 1 Hybrid - 2 ICE: ");
				newVehicle.setType(in.nextInt());

				if (newVehicle.getType() != 2) {
					System.out.println("Quale rate di ricarica supporta il tuo veicolo?");
					// Stampa di tutti i rate di ricarica
					ChargingRate.printAll(chargingRateList);
					System.out.print("Scelta: ");
					int cr = in.nextInt();
					newVehicle.setSupportedRate(chargingRateList.get(cr - 1));
				}

				newVehicle.setLocation(newUser.getLocation());
				newVehicle.setOwner(newUser);
				newUser.setPersonalVehicle(newVehicle);
				System.out.println("Veicolo aggiunto ed associato a te!");
				vehicleList.add(newVehicle);
				currentUsername = username;
			}
		} else if (currentUsername.equals("admin")) {
			adminMode = true;
		}

		if (adminMode) {
			Admin.adminOp(chargingRateList, chargingStationList, userList, energySupplierList, rewardList, vehicleList);
		} else {
			User currentUser = User.getUserByUsername(userList, currentUsername);
			Vehicle currentVehicle = currentUser.getPersonalVehicle();
			ChargingStation currentCS = new ChargingStation();
			LocalTime currentTime = LocalTime.now();
			Charge newCharge = new Charge();
			Time startTime = new Time();
			Transaction newTransaction = new Transaction();
			Location locCurrUser = new Location();

			// currentRewardSystem.setRechargeFactor(1);
			// currentRewardSystem.setRentFactor(1);

			System.out.println(
					"Benvenuto " + currentUser.getName() + ". Saldo GP: " + currentUser.getGreenPointsBalance());
			System.out.println("1) Ricarica il tuo veicolo elettrico");
			System.out.println("2) Prenota una ricarica");
			System.out.println("3) Noleggia un veicolo");
			System.out.println("4) Riscatta una ricompensa");
			System.out.print("Cosa vuoi fare? ");
			int choice = in.nextInt();

			switch (choice) {
			case 1: {
				if (currentUser.getType() != 0) {
					System.out.println("Funzione non disponibile! Devi avere un veicolo elettrico");
					break;
				}
				locCurrUser = User.sendPosition(); // sendPosition
				currentUser.setLocation(locCurrUser);
				System.out.print("Distanza massima della stazione: ");
				int range = in.nextInt();
				ChargingStation.getNearAvailableStation(currentUser, range, chargingStationList); // stationList
				currentCS = ChargingStation.chooseStation(currentVehicle, chargingStationList);
				Charge.recharge(currentUser, currentVehicle, currentCS, currentTime, newCharge, startTime);
				Transaction.payment(currentUser, currentVehicle, currentTime, newCharge, newTransaction,
						transactionList, currentRewardSystem);

				break;
			}
			case 2: {
				if (currentUser.getType() != 0) {
					System.out.println("Funzione non disponibile! Devi avere un veicolo elettrico");
					break;
				}
				locCurrUser = User.sendPosition();
				currentUser.setLocation(locCurrUser);
				System.out.print("Distanza massima della stazione: ");
				int range = in.nextInt();
				ChargingStation.getNearAvailableStation(currentUser, range, chargingStationList);
				currentCS = ChargingStation.chooseStation(currentVehicle, chargingStationList);
				currentCS.printTimeTableWithTimeSlots();

				System.out.print("Quali slot vuoi prenotare? Inseriscili nella forma 14-18: ");
				String[] slot = in.next().split("-");
				int startingSlot = Integer.parseInt(slot[0]);
				int endingSlot = Integer.parseInt(slot[1]);
				boolean slotAvailable = true;

				slotAvailable = checkSlotAvailability(currentCS, startingSlot, endingSlot, slotAvailable);
				Reservation.reserveSlot(currentUser, currentVehicle, currentCS, startingSlot, endingSlot, slotAvailable,
						reservationList);

				break;
			}
			case 3: {
				System.out.println("Implementato nella seconda iterazione");
				break;
			}
			case 4: {
				System.out.println("Il tuo attuale saldo dei Green Points è: " + currentUser.getGreenPointsBalance());
				break;
			}
			}
		}

		in.close();

		saveAll();
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

	@SuppressWarnings("unchecked")
	private static void readAll() throws FileNotFoundException, IOException {
		// Lettura chargingRateList
		FileInputStream FIS = new FileInputStream("ChargingRate.txt");
		ObjectInputStream OIS = new ObjectInputStream(FIS);
		try {
			chargingRateList = (ArrayList<ChargingRate>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Lettura energySupplierList
		FIS = new FileInputStream("EnergySupplier.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			energySupplierList = (ArrayList<EnergySupplier>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Lettura chargingStationList
		FIS = new FileInputStream("ChargingStation.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			chargingStationList = (ArrayList<ChargingStation>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Lettura rewardList
		FIS = new FileInputStream("Reward.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			rewardList = (ArrayList<Reward>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Lettura userList
		FIS = new FileInputStream("User.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			userList = (ArrayList<User>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Lettura vehicleList
		FIS = new FileInputStream("Vehicle.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			vehicleList = (ArrayList<Vehicle>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Lettura transactionList
		FIS = new FileInputStream("Transaction.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			transactionList = (ArrayList<Transaction>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Lettura reservationList
		FIS = new FileInputStream("Reservation.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			reservationList = (ArrayList<Reservation>) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Lettura rewardSystem
		FIS = new FileInputStream("RewardSystem.txt");
		OIS = new ObjectInputStream(FIS);
		try {
			currentRewardSystem = (RewardSystem) OIS.readObject();
			OIS.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private static void saveAll() throws IOException {
		// Salvataggio di chargingRateList
		try (FileOutputStream chargingRateFOS = new FileOutputStream(new File("ChargingRate.txt"));
				ObjectOutputStream chargingRateOOS = new ObjectOutputStream(chargingRateFOS)) {
			chargingRateOOS.writeObject(chargingRateList);
		}

		// Salvataggio di energySupplierList
		try (FileOutputStream energySupplierFOS = new FileOutputStream(new File("EnergySupplier.txt"));
				ObjectOutputStream energySupplierOOS = new ObjectOutputStream(energySupplierFOS)) {
			energySupplierOOS.writeObject(energySupplierList);
		}

		// Salvataggio di chargingStationList
		try (FileOutputStream chargingStationFOS = new FileOutputStream(new File("ChargingStation.txt"));
				ObjectOutputStream chargingStationOOS = new ObjectOutputStream(chargingStationFOS)) {
			chargingStationOOS.writeObject(chargingStationList);
		}

		// Salvataggio di rewardList
		try (FileOutputStream rewardFOS = new FileOutputStream(new File("Reward.txt"));
				ObjectOutputStream rewardOOS = new ObjectOutputStream(rewardFOS)) {
			rewardOOS.writeObject(rewardList);
		}

		// Salvataggio di userList
		try (FileOutputStream userFOS = new FileOutputStream(new File("User.txt"));
				ObjectOutputStream userOOS = new ObjectOutputStream(userFOS)) {
			userOOS.writeObject(userList);
		}

		// Salvataggio di vehicleList
		try (FileOutputStream vehicleFOS = new FileOutputStream(new File("Vehicle.txt"));
				ObjectOutputStream vehicleOOS = new ObjectOutputStream(vehicleFOS)) {
			vehicleOOS.writeObject(vehicleList);
		}

		// Salvataggio di transactionList
		try (FileOutputStream transactionFOS = new FileOutputStream(new File("Transaction.txt"));
				ObjectOutputStream transactionOOS = new ObjectOutputStream(transactionFOS)) {
			transactionOOS.writeObject(transactionList);
		}

		// Salvataggio di reservationList
		try (FileOutputStream reservationFOS = new FileOutputStream(new File("Reservation.txt"));
				ObjectOutputStream reservationOOS = new ObjectOutputStream(reservationFOS)) {
			reservationOOS.writeObject(reservationList);
		}

		// Salvataggio di RewardSystem
		try (FileOutputStream rewardSystemFOS = new FileOutputStream(new File("RewardSystem.txt"));
				ObjectOutputStream rewardSystemOOS = new ObjectOutputStream(rewardSystemFOS)) {
			rewardSystemOOS.writeObject(currentRewardSystem);
		}

		System.out.println("\n\n----------------------------------------------------------------");
		System.out.println("Tutti i dati sono stati correttamente salvati su file.");
		System.out.println("----------------------------------------------------------------");
	}

}