package GreenHub;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {
    private Controller controller = new Controller();  // Il Controller gestisce tutto
    private Scanner scanner = new Scanner(System.in);
    private Map<Integer, MainMenuStrategy> strategies = new HashMap<>();
    
    public UserInterface() {
        controller.readAll();  // Carica i dati esistenti

        // Popola la mappa con le strategie
        strategies.put(1, new MMRechargeVehicleStrategy());
        strategies.put(2, new MMBookRechargeStrategy());
        strategies.put(3, new MMReservationListStrategy());
        //strategies.put(3, new MMRentVehicleStrategy());
        //strategies.put(4, new MMRedeemRewardStrategy());
        strategies.put(4, new MMRegisterCarStrategy());
        strategies.put(5, new MMExitStrategy());
    }

    public Controller getController() {
        return controller;
    }
	public Scanner getScanner() {
		return scanner;
	}

    public void WelcomeMenu() throws IOException, ClassNotFoundException, InterruptedException {
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
                controller.registerUser();
                controller.saveAll();  // Salva i dati
                break;
            case 2:
                User user = controller.loginUser();
                if (user != null) {
                    MainMenu(user);
                }
                break;
            case 3:
                System.exit(0);
            case 4:
                controller.printino();  // Legge i dati attraverso il Controller
                break;
        
            default:
                System.out.println("Opzione non valida, riprova.");
            }
        }
    }

    public void MainMenu(User user) throws InterruptedException {
        while (true) {
            System.out.println("\nCiao " + user.getName() + "! Saldo Green Points: " + user.getGreenPointsBalance());
    
            if (user.getPersonalVehicle() != null) {
                int max = (int) user.getPersonalVehicle().getCapacity();
                int marco = (int) user.getPersonalVehicle().getBatteryLevel();
                int percentuale = (int) ((marco / (double) max) * 100);
                System.out.println("\nLa tua " + user.getPersonalVehicle().getMaker() + " " + user.getPersonalVehicle().getModel() + 
                    " ha una percentuale di carica pari a " + percentuale + " %");
            } else {
                System.out.println("\nNon hai un veicolo personale associato.");
            }
    
            System.out.println("\n1) Ricarica il tuo veicolo elettrico");
            System.out.println("2) Prenota una ricarica");
            System.out.println("3) Elenco prenotazioni");
            //System.out.println("4) Riscatta una ricompensa");
    
            // Mostra l'opzione di registrazione solo se l'utente non ha un veicolo
            if (user.getPersonalVehicle() == null) {
                System.out.println("4) Registrazione nuova auto");
            }
    
            System.out.println("5) Esci");
            System.out.print("Scelta: ");
            int choice = scanner.nextInt();
    
            // Se l'utente non ha un veicolo registrato, blocca le scelte 1, 2 e 3
            if ((choice == 1 || choice == 2 || choice == 3) && user.getPersonalVehicle() == null) {
                System.out.println("\nDevi registrare un veicolo elettrico per effettuare questa operazione.");
                Thread.sleep(2000);
            } else {
                // Esegui la strategia scelta
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
    }
    

}
