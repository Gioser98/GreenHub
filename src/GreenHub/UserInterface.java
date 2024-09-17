package GreenHub;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Random;

public class UserInterface {
	private Controller controller = new Controller();
	private Scanner scanner = new Scanner(System.in);
	private Random random = new Random();
	private Map<Integer, MainMenuStrategy> strategies = new HashMap<>();

	public UserInterface() {
		scanner = new Scanner(System.in);

		try {
			controller.readAll();  // Carica i dati esistenti
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Errore durante il caricamento dei dati.");
		}

		// Popola la mappa con le strategie
		strategies.put(1, new RechargeVehicleStrategy());
		strategies.put(2, new BookRechargeStrategy());
		strategies.put(3, new RentVehicleStrategy());
		strategies.put(4, new RedeemRewardStrategy());
		strategies.put(5, new RegisterCarStrategy());
		strategies.put(6, new ExitStrategy());
	}

	public static Controller getController() {
		return controller;
	}

	public Scanner getScanner() {
		return scanner;
	}

	public void WelcomeMenu() throws IOException, ClassNotFoundException {
		while (true) {
			System.out.println("-------------BENVENUTO IN GREENHUB-------------");
			System.out.println("Tutti i dati verranno caricati dai file fra pochi secondi.");
			System.out.println("Per salvare le modifiche sul file, terminare il programma scegliendo "
					+ "l'apposita opzione, altrimenti verranno perse.");
			System.out.println("1) Registrazione nuovo utente");
			System.out.println("2) Login utente già registrato");
			System.out.println("3) Exit");
			System.out.println("4) Leggi contenuti dei file");
			System.out.print("Scelta: ");
			int choice = scanner.nextInt();

			switch (choice) {
			case 1:
				registerUser();
				try {
					controller.saveAll();  // Salva i dati
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Errore durante il salvataggio dei dati.");
				}
				break;
			case 2:
				System.out.print("Inserisci il tuo username: ");
				String username = scanner.next();
				loginUser(username);
				break;
			case 3:
				System.exit(0);
			case 4:
				controller.printino();  // Legge i dati
				break;

			default:
				System.out.println("Opzione non valida, riprova.");
			}
		}
	}

	public void MainMenu(User user) {
		while (true) {
			System.out.println("Ciao " + user.getName() + "! Saldo Green Points: " + user.getGreenPointsBalance());
			System.out.println("1) Ricarica il tuo veicolo elettrico");
			System.out.println("2) Prenota una ricarica");
			System.out.println("3) Noleggia un veicolo");
			System.out.println("4) Riscatta una ricompensa");
			System.out.println("5) Registrazione nuova auto");
			System.out.println("6) Esci");
			System.out.print("Scelta: ");
			int choice = scanner.nextInt();

			MainMenuStrategy strategy = strategies.get(choice);
			if (strategy != null) {
				try {
					strategy.execute(this, user);
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Errore durante l'esecuzione dell'operazione.");
				}
			} else {
				System.out.println("Opzione non valida, riprova.");
			}
		}
	}

	public void registerUser() {
		System.out.print("Inserisci il tuo username: ");
		String username = scanner.next();
		System.out.print("Inserisci il tuo nome: ");
		String name = scanner.next();
		System.out.print("Inserisci il tuo cognome: ");
		String surname = scanner.next();
		int latitude = random.nextInt(100);
		int longitude = random.nextInt(100);
		Location location = new Location(latitude, longitude);
		User user = new User(username, 0, 2, name, surname, location);

		controller.addUser(user);
		System.out.println("Utente registrato correttamente!");
	}

	public void loginUser(String username) {
		User user = controller.getUserByUsername(username);
		if (user != null) {
			MainMenu(user);
		} else {
			System.out.println("Utente non trovato.");
		}
	}

	@SuppressWarnings("unused")
	private void increaseUserGreenPoints(String username, int points) {
		controller.increaseUserGPBalance(username, points);
		System.out.println("Green points updated successfully!");
	}

	public static void main(String[] args) throws ClassNotFoundException {
		UserInterface view = new UserInterface();
		try {
			view.WelcomeMenu();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
