package GreenHub;

import java.util.List;
import java.util.Scanner;

public class MMReservationListStrategy implements MainMenuStrategy {
	@Override
	public void execute(UserInterface view, User user) {
		Scanner scanner = view.getScanner();  // Usa lo Scanner già esistente nella classe UserInterface

		while (true) {
			// Ottieni la lista delle prenotazioni dell'utente
			List<Reservation> reservations = user.getReservations();

			// Controlla se l'utente ha prenotazioni
			if (reservations.isEmpty()) {
				System.out.println("Non hai prenotazioni attive.");
			} else {
				// Mostra le prenotazioni
				System.out.println("Le tue prenotazioni:");
				for (Reservation reservation : reservations) {
					System.out.println(reservation);
				}
			}

			// Aggiungi opzione per tornare al menu principale
			System.out.println("\nPremi 0 per tornare al menu principale.");
			System.out.print("Scelta: ");

			// Acquisisci l'input dell'utente
			int choice = scanner.nextInt();

			// Se l'utente sceglie 0, torna al menu principale
			if (choice == 0) {
				break;  // Esce dal ciclo e ritorna al menu principale
			} else {
				System.out.println("Opzione non valida. Riprova.");
			}
		}
	}
}
