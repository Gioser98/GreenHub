package GreenHub;

import java.util.Scanner;

public class View {
	private Controller controller = new Controller();
	private Scanner scanner = new Scanner(System.in);

	public void WelcomeMenu() {
		while (true) {
			System.out.println("-------------BENVENUTO IN GREENHUB-------------");
			System.out.println("Tutti i dati verranno caricati dai file fra pochi secondi.");
			System.out.println("Per salvare le modifiche sul file, terminare il programma scegliendo "
					+ "l'apposita opzione, altrimenti verranno perse.");
			System.out.println("1. Registrazione nuovo utente");
			System.out.println("2. Login utente già registrato");
			System.out.println("3. Exit");
			System.out.print("Scelta: ");
			int choice = scanner.nextInt();

			switch (choice) {
			case 1:
				registerUser();
				break;
			case 2:
				System.out.print("Inserisci il tuo username: ");
				String username = scanner.next();
				loginUser(username);
				break;
			case 3:
				System.exit(0);
			default:
				System.out.println("Opzione non valida, riprova.");
			}
		}
	}

	public void MainMenu(User user) {
		while (true) {
            System.out.println("Benvenuto, " + user.getName() + ". Saldo GP: " + user.getGreenPointsBalance());
			System.out.println("1) Ricarica il tuo veicolo elettrico");
			System.out.println("2) Prenota una ricarica");
			System.out.println("3) Noleggia un veicolo");
			System.out.println("4) Riscatta una ricompensa");
			System.out.println("5) Registrazione nuova auto");
			System.out.println("6) Esci");
			System.out.print("Scelta: ");
			int choice = scanner.nextInt();

			switch (choice) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				System.exit(0);
			default:
				System.out.println("Opzione non valida, riprova.");
			}
		}
	}

	private void registerUser() {
		System.out.print("Inserisci il tuo username: ");
		String username = scanner.next();
		System.out.print("Inserisci il tuo nome: ");
		String name = scanner.next();
		System.out.print("Inserisci il tuo cognome: ");
		String surname = scanner.next();
		System.out.print("Posizione X: ");
		int latitude = scanner.nextInt();
		System.out.print("Posizione Y: ");
		int longitude = scanner.nextInt();
		Location location = new Location(latitude, longitude);
		User user = new User(username, 0, 2, name, surname, location);

		controller.addUser(user);
		System.out.println("Utente registrato correttamente!");
	}

	private void loginUser(String username) {
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

	public static void main(String[] args) {
		View view = new View();
		view.WelcomeMenu();
	}
}
