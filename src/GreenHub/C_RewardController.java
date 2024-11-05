package GreenHub;

import java.util.Map;

public class C_RewardController {
    private Reward rewards;
    private DataManager dataSaver;
    private View view;

    public C_RewardController(Reward rewards, DataManager dataSaver, View view) {
        this.rewards = rewards;
        this.dataSaver = dataSaver;
        this.view = view;
    }

    public void assignGreenPoints(User user, GreenPointsStrategy strategy, int value) {
        rewards.setStrategy(strategy);
        rewards.addPoints(user, value);
    }

    public void redeemReward(User user, String rewardType) {
        rewards.setRewardType(rewardType);

        if (rewards.getRewardType() == null) {
            view.showMessage("Reward type not set");
            return;
        }

        int userPoints = user.getGreenPointsBalance();
        int requiredPoints = rewards.getRewardType().requiredPoints();

        if (userPoints >= requiredPoints) {
            String result = rewards.generateRewardCode(user);
            user.decreaseGPBalance(requiredPoints);

            String code = result.split(": ")[1].trim();
            dataSaver.getRedeemedRewardsMap().put(code, user.getUsername());

            view.showMessage("Codice riscattato: " + code);
        } else {
            view.showMessage("Punti insufficienti per riscattare questa ricompensa.");
        }
    }

    public boolean isDiscountCodeValid(String discountCode, User user) {
        return dataSaver.getRedeemedRewardsMap().containsKey(discountCode) &&
               user.getUsername().equals(dataSaver.getRedeemedRewardsMap().get(discountCode));
    }

    public Map<String, String> getRedeemedRewardsMap() {
        return dataSaver.getRedeemedRewardsMap();
    }
}
