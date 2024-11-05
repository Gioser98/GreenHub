package GreenHub;

public interface RewardType {
	String redeem(User user);       // Riscuote la ricompensa e fornisce il codice
	int requiredPoints();           // Punti necessari per riscattare la ricompensa
	double applyReward();           // Restituisce il fattore di sconto (es. 0.8 per 20% di sconto)
	boolean isValidCode(String code); // Verifica se il codice corrisponde a questa ricompensa
}