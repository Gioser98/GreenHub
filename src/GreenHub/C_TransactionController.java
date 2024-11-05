package GreenHub;

import java.time.LocalDateTime;

public class C_TransactionController {
	private DataManager dataSaver;
	private View view;

	public C_TransactionController(DataManager dataSaver, View view) {
		this.dataSaver = dataSaver;
		this.view = view;
	}

	public String registerTransaction(User user, Vehicle vehicle, ChargingStation chargingStation, Charge charge, double amount, PaymentStrategy strategy) {
		Transaction transaction = new Transaction();
		try {
			transaction.setUser(user);
			transaction.setVehicle(vehicle);
			transaction.setCharge(charge);
			transaction.setAmount(amount);
			transaction.setPaymentStrategy(strategy);

			LocalDateTime now = LocalDateTime.now();
			Time timestamp = new Time(now.getHour(), now.getMinute());
			transaction.setTimestamp(timestamp);

			int newId = dataSaver.getTransactionList().isEmpty() ? 1 : dataSaver.getTransactionList().get(dataSaver.getTransactionList().size() - 1).getId() + 1;
			transaction.setId(newId);

			if (!transaction.processPayment()) {
				view.showMessage("Nessuna strategia di pagamento definita. Impossibile completare il pagamento.");
			}

			dataSaver.getTransactionList().add(transaction);
			return "Pagamento effettuato con successo e transazione registrata.";
		} catch (Exception e) {
			return "Errore durante il pagamento: " + e.getMessage();
		}
	}
}
