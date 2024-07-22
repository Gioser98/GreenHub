package GreenHub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle.Control;



public class Controller {
	private static ArrayList<ChargingRate> chargingRateList = new ArrayList<ChargingRate>();
	private static ArrayList<EnergySupplier> energySupplierList = new ArrayList<EnergySupplier>();
	private static ArrayList<ChargingStation> chargingStationList = new ArrayList<ChargingStation>();
	private static ArrayList<Reward> rewardList = new ArrayList<Reward>();
	private static ArrayList<User> userList = new ArrayList<User>();
	private static ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();
	private static ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
	private static ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
	private DataSaver dataSaver;

	public Controller(){
		//
	}

	public Controller(DataSaver dataSaver){
		this.dataSaver = dataSaver;
	}


	public void saveRequest() throws IOException {
        if (dataSaver != null) {
            dataSaver.saveAll();
        } else {
            throw new IllegalStateException("DataSaver non è stato inizializzato.");
        }
    }


	// User methods
	public void addUser(User user) {
		if (getUserByUsername(user.getUsername()) == null) {
			userList.add(user);
		} else {
			System.out.println("Username già esistente.");
		}
	}

	public User getUserByUsername(String username) {
		for (User u : userList) {
			if (u.getUsername().equals(username)) {
				return u;
			}
		}
		return null;
	}

	public void increaseUserGPBalance(String username, int points) {
		User user = getUserByUsername(username);
		user.increaseGPBalance(points);
		System.out.println("Saldo GP aggiornato correttamente!");
	}

	
	// Transaction methods
	public String pay(double amount, String method, String email, String password, String cardNumber, String expirationDate, String cvv)
	{

		PaymentStrategy strategy;

        switch (method.toLowerCase()) {
            case "paypal":
                strategy = new PayPalStrategy(email, password);
                break;
            case "creditcard":
                strategy = new CreditCardStrategy(cardNumber, expirationDate, cvv);
                break;
            default:
                return "Errore: Metodo di pagamento non supportato.";
        }

		Transaction transaction = new Transaction(strategy);

	try {
		transaction.processPayment(amount);
		return "Pagamento effettuato con successo";
	} catch (Exception e){
		return "Errore durante il pagamento" + e.getMessage();
	}
	
	}
	
	// Reward methods

    public String calculateGreenPoints(int value, String strategy) {

        GreenPointsStrategy greenPointsStrategy;

        // Seleziona la strategia in base al parametro 'strategy'
        switch (strategy.toLowerCase()) {
            case "reservation":
                greenPointsStrategy = new ReservationStrategy();
                break;
            case "recharge":
                greenPointsStrategy = new RechargeStrategy();
                break;
            default:
                return "Errore: Strategia di calcolo dei punti non supportata.";
        }

        // Crea un oggetto Reward con la strategia selezionata
        Reward reward = new Reward(greenPointsStrategy);
        int points = reward.calculateGreenPoints(value);

        return "Green points calcolati: " + points;
    }
	

	// Reservation methods
	public void reserveSlot(User currentUser, Vehicle currentVehicle, ChargingStation currentCS, int startingSlot, int endingSlot) {
        boolean slotAvailable = true; // Logica per verificare la disponibilità dello slot
        Reservation.reserveSlot(currentUser, currentVehicle, currentCS, startingSlot, endingSlot, slotAvailable, reservationList);
    }


	// Vehicle methods
	// ChargingRate methods
	// EnergySupplier methods
	// ChargingStation methods


}