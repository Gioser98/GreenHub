package GreenHub;

public class MainApp {

	public static void main(String[] args) throws ClassNotFoundException, InterruptedException {
		UserInterface ui = new UserInterface();  // Crea un'istanza di UserInterface
		try {
			ui.WelcomeMenu();  // Avvia il menu di benvenuto
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
