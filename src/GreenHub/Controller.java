package GreenHub;

import java.util.ArrayList;

public class Controller {
	private static ArrayList<ChargingRate> chargingRateList = new ArrayList<ChargingRate>();
	private static ArrayList<EnergySupplier> energySupplierList = new ArrayList<EnergySupplier>();
	private static ArrayList<ChargingStation> chargingStationList = new ArrayList<ChargingStation>();
	private static ArrayList<Reward> rewardList = new ArrayList<Reward>();
	private static ArrayList<User> userList = new ArrayList<User>();
	private static ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();
	private static ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
	private static ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
	private static RewardSystem currentRewardSystem = new RewardSystem();

	

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
	public void addTransaction(Transaction transaction) {
        transactionList.add(transaction);
    }

	public Transaction getTransactionById(int id) {
        for (Transaction t : transactionList) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

	public void processTransaction(int id, Time timestamp, int type, double amount, Charge charge, PaymentStrategy paymentStrategy) {
        // Creare una nuova transazione con la strategia di pagamento
        Transaction transaction = new Transaction(id, timestamp, type, amount, charge, paymentStrategy);
        
        // Processare il pagamento
        transaction.processPayment();
        
        // Aggiungere la transazione alla lista delle transazioni
        addTransaction(transaction);
        
        System.out.println("Transazione creata e pagamento processato: " + transaction);
    }

	
	// Reward methods

	// ci sono dei dubbi su questa sezione, discutere nel briefing

	
    public void addReward(Reward reward) {
        rewardList.add(reward);
    }

    public Reward getRewardByName(String name) {
        for (Reward r : rewardList) {
            if (r.getName().equals(name)) {
                return r;
            }
        }
        return null;
    }

    public void createReward(String name, String description, int greenPointsCost, int remainingQuantity, GreenPointsStrategy strategy) {
        Reward reward = new Reward(name, description, greenPointsCost, remainingQuantity, strategy);
        addReward(reward);
        System.out.println("Ricompensa creata: " + reward);
    }
	
    public void rewardSystem(String rewardName, int value) {
        Reward reward = getRewardByName(rewardName);
        if (reward != null) {
            int points = reward.calculateGreenPoints(value);
            System.out.println("Punti calcolati per la ricompensa " + rewardName + ": " + points);
        } else {
            System.out.println("Ricompensa non trovata.");
        }
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