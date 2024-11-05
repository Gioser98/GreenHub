package GreenHub;

public class RFFreeRechargeReward implements RewardType{
	@Override
	public String redeem(User user) {
		String code = "100" + RewardUtils.generateRandomChars(2);
		return "\nRicarica gratuita riscattata! Usa questo codice alla tua prossima ricarica: " + code + "\n";
	}

	@Override
	public int requiredPoints() {
		return 100;  // 100 punti necessari
	}

	@Override
	public double applyReward() {
		return 0.0; // Ricarica gratuita
	}

	@Override
	public boolean isValidCode(String code) {
		return code.startsWith("100") && code.length() == 5;
	}
}