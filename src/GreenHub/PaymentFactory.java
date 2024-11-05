package GreenHub;

import java.util.HashMap;
import java.util.Map;

public class PaymentFactory {
	private static final Map<String, PaymentStrategy> paymentOptions = new HashMap<>();

	static {
		paymentOptions.put("Carta di credito", new PCreditCardStrategy());
		paymentOptions.put("PayPal", new PPayPalStrategy());
		paymentOptions.put("Bonifico Bancario", new PBankTransferStrategy());
	}

	public static Map<String, PaymentStrategy> getPaymentOptions() {
		return new HashMap<>(paymentOptions); // Ritorna una copia della mappa per evitare modifiche esterne
	}
}