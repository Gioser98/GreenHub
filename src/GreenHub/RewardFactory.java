package GreenHub;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RewardFactory {
    private static final Map<String, RewardType> rewardTypes = new HashMap<>();
    private static final Map<String, String> prefixToRewardType = new HashMap<>(); // Mappa dei prefissi

    static {
        rewardTypes.put("Ricarica gratuita", new RFFreeRechargeReward());
        rewardTypes.put("Sconto del 20%", new RFDiscount20PercentReward());
        rewardTypes.put("Zalando Voucher", new RFZalandoVoucherReward());

         // Mappa dei prefissi per associare il prefisso numerico al tipo di ricompensa
         prefixToRewardType.put("20", "Sconto del 20%");  // Prefisso "20" corrisponde a Discount20
         prefixToRewardType.put("100", "Ricarica gratuita"); // Prefisso "100" corrisponde a FreeRecharge
    }

    public static RewardType getReward(String rewardType) {
        return rewardTypes.getOrDefault(rewardType, null);
    }

    

    public static RewardType getRewardByCode(String discountCode) {
        // Legge i primi caratteri del codice e cerca nella mappa dei prefissi
        for (String prefix : prefixToRewardType.keySet()) {
            if (discountCode.startsWith(prefix)) {
                String rewardName = prefixToRewardType.get(prefix); // Ottiene il nome della ricompensa
                return rewardTypes.get(rewardName); // Restituisce il RewardType corrispondente
            }
        }
        return null; // Restituisce null se il codice non è valido
    }

    public static Map<String, RewardType> getAllRewards() {
        return Collections.unmodifiableMap(rewardTypes);
    }
}
