package GreenHub;

import java.io.IOException;
import java.util.Map;

public class MMRewardList implements MainMenuStrategy {

	@Override
	public void execute(UserInterface ui, User user) throws IOException, InterruptedException {
		// Ottiene la mappa delle ricompense riscattate dal controller
		Map<String, String> redeemedRewardsMap = ui.getController().getRedeemedRewardsMap();

		// Mostra le ricompense riscattate dall'utente usando la view
		ui.getView().showUserRedeemedRewards(redeemedRewardsMap, user.getUsername());
	}
}