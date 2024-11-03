package GreenHub;

import java.util.Map;
import java.util.Scanner;

public class MMRedeemRewardStrategy implements MainMenuStrategy {

    @Override
    public void execute(UserInterface ui, User user) {
        Reward reward = new Reward();
        Scanner scanner = new Scanner(System.in);

        // Ottieni tutti i tipi di ricompense dal RewardFactory
        Map<String, RewardType> availableRewards = RewardFactory.getAllRewards();

        // Ciclo del menù
        while (true) {

            // Mostra il menù delle ricompense
            System.out.println("=== Menù Riscatto Ricompense ===");
            System.out.println("Saldo punti attuale: " + user.getGreenPointsBalance());

            System.out.println("\nScegli una ricompensa da riscattare:");
            
            // Stampa tutte le ricompense disponibili con i punti necessari
            int index = 1;
            for (String rewardName : availableRewards.keySet()) {
                RewardType rewardType = availableRewards.get(rewardName);
                System.out.println(index + ". " + rewardName + " (" + rewardType.requiredPoints() + " punti)");
                index++;
            }
            System.out.println(index + ". Torna al menù principale");
            
            System.out.print("Inserisci il numero della tua scelta: ");
            int choice = scanner.nextInt();

            // Se l'utente sceglie di tornare al menù principale
            if (choice == index) {
                System.out.println("Tornando al menù principale...");
                return;
            }

            // Validazione della scelta
            if (choice < 1 || choice > availableRewards.size()) {
                System.out.println("Scelta non valida. Riprova.");
                continue;
            }

            // Mappa la scelta dell'utente a una ricompensa
            String selectedRewardName = (String) availableRewards.keySet().toArray()[choice - 1];
            try {
                // Passa il tipo di ricompensa selezionato al Controller
                ui.getController().redeemReward(user, selectedRewardName);
            } catch (IllegalArgumentException e) {
                System.out.println("Errore: tipo di ricompensa non valido.");
            } catch (IllegalStateException e) {
                System.out.println("Errore: strategia o tipo di ricompensa non impostato correttamente.");
            }
        }
    }
}
