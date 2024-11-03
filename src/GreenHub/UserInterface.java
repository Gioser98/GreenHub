package GreenHub;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {
    private Controller controller = new Controller();  // Il Controller gestisce tutto
    private Scanner scanner = new Scanner(System.in);
    private View view = new View();
    private Map<Integer, MainMenuStrategy> strategies = new HashMap<>();
    
    public UserInterface() {
        
        controller.readAll();  // Carica i dati esistenti
        // Popola la mappa con le strategie
        strategies.put(1, new MMRechargeVehicleStrategy());
        strategies.put(2, new MMBookRechargeStrategy());
        strategies.put(3, new MMReservationListStrategy());
        strategies.put(4, new MMRedeemRewardStrategy());
        strategies.put(5, new MMRegisterCarStrategy());
        strategies.put(6, new MMExitStrategy());
    }

    public Controller getController() {
        return controller;
    }
	public Scanner getScanner() {
		return scanner;
	}

    public View getView() {
        return view;
    }

    

    

    public void WelcomeMenu() throws IOException, ClassNotFoundException, InterruptedException {
        Map<Integer, Runnable> actions = new HashMap<>();
        
        actions.put(1, () -> {
            controller.registerUser();
            controller.saveAll();  // Salva i dati
        });
        
        actions.put(2, () -> {
            try {
                User user = controller.loginUser();
                if (user != null) {
                    MainMenu(user);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        actions.put(3, () -> System.exit(0));
        
        actions.put(4, () -> controller.printino());
    
        while (true) {
            view.showWelcomeMenuOptions();
            int choice = scanner.nextInt();
    
            Runnable action = actions.getOrDefault(choice, () -> System.out.println("Opzione non valida, riprova."));
            action.run();
        }
    }

    public void MainMenu(User user) throws InterruptedException {
        
        
        while (true) {
            view.showMessage("\nCiao " + user.getName() + "! Saldo Green Points: " + user.getGreenPointsBalance());
        
            if (user.getPersonalVehicle() != null) {
                view.showVehicleStatus(user);  // Usa il metodo nella View
            } else {
                view.showMessage("\nNon hai un veicolo personale associato.");
            }
            
            view.showMenuOptions(user);  // Usa il metodo nella View
            view.showMessage("Scelta: ");
            int choice = scanner.nextInt();
    
            if (isActionAllowed(choice, user)) {
                executeMenuAction(choice, user);
            } else {
                view.showMessage("\nDevi registrare un veicolo elettrico per effettuare questa operazione.");
                Thread.sleep(2000);
            }
        }
    }
    
    // Controlla se l'azione scelta è consentita
    private boolean isActionAllowed(int choice, User user) {
        return choice == 4 || choice == 5 || user.getPersonalVehicle() != null;
    }
    
    // Esegue l'azione di menu scelta
    private void executeMenuAction(int choice, User user) throws InterruptedException {
        MainMenuStrategy strategy = strategies.get(choice);
        if (strategy != null) {
            try {
                strategy.execute(this, user);
            } catch (IOException e) {
                e.printStackTrace();
                view.showMessage("Errore durante l'esecuzione dell'operazione.");
            }
        } else {
            view.showMessage("Opzione non valida, riprova.");
        }
    }
}
