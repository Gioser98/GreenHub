package GreenHub;

public class RFZalandoVoucherReward implements RewardType{
    @Override
    public String redeem(User user) {
        String prefix = "ZALANDO";
        String randomPart = RewardUtils.generateRandomChars(13); // Genera 13 caratteri casuali
        String code = prefix + randomPart; // Combina il prefisso con la parte casuale
        return "\nBuono Zalando generato: " + code;
    }

    @Override
    public int requiredPoints() {
        return 150;  // 150 punti necessari
    }

    @Override
    public double applyReward() {
        return 1.0; // Nessuno sconto applicato alla ricarica, solo buono
    }

    @Override
    public boolean isValidCode(String code) {
        // Questo tipo di ricompensa non utilizza un prefisso specifico per la ricarica
        return code.length() == 20; // Controllo solo sulla lunghezza
    }

}
