package GreenHub;

import java.util.Map;

public class MMRedeemRewardStrategy implements MainMenuStrategy {

    @Override
    public void execute(UserInterface ui, User user) {
        
        View view = ui.getView();

        // Ottieni tutti i tipi di ricompense dal RewardFactory
        Map<String, RewardType> availableRewards = RewardFactory.getAllRewards();

        // Ciclo del menù
        while (true) {
            // Mostra il menù delle ricompense e ottiene la scelta dell'utente
            int choice = view.showRewardsMenu(user.getGreenPointsBalance(), availableRewards);

            // Se l'utente sceglie di tornare al menù principale
            if (choice == availableRewards.size() + 1) {
                view.showMessage("Tornando al menù principale...");
                return;
            }

            // Validazione della scelta
            if (choice < 1 || choice > availableRewards.size()) {
                view.showMessage("Scelta non valida. Riprova.");
                continue;
            }

            // Mappa la scelta dell'utente a una ricompensa
            String selectedRewardName = (String) availableRewards.keySet().toArray()[choice - 1];
            try {
                // Passa il tipo di ricompensa selezionato al Controller
                ui.getController().redeemReward(user, selectedRewardName);
            } catch (IllegalArgumentException e) {
                view.showMessage("Errore: tipo di ricompensa non valido.");
            } catch (IllegalStateException e) {
                view.showMessage("Errore: strategia o tipo di ricompensa non impostato correttamente.");
            }
        }
    }
}
