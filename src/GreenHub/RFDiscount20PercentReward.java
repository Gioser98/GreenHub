package GreenHub;

public class RFDiscount20PercentReward implements RewardType{

	@Override
	public String redeem(User user) {
		String code = "20" + RewardUtils.generateRandomChars(3);
		return "\nCodice sconto 20% generato. Usalo alla tua prossima ricarica: " + code + "\n";
	}

	@Override
	public int requiredPoints() {
		return 20;  // 20 punti necessari
	}

	@Override
	public double applyReward() {
		return 0.8; // 20% di sconto
	}

	@Override
	public boolean isValidCode(String code) {
		return code.startsWith("20") && code.length() == 5;
	}

}