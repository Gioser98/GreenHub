package GreenHub;

import java.io.Serializable;


public class Reward implements Serializable{
    private GreenPointsStrategy strategy;
    private RewardType rewardType;
       
    
    // Setter per impostare la strategia
    public void setStrategy(GreenPointsStrategy strategy) {
        this.strategy = strategy;
    }

    
    public RewardType getRewardType() {
        return rewardType;
    }

    public void setRewardType(RewardType rewardType) {
        this.rewardType = rewardType;
    }

    public void setRewardType(String type) {
        this.rewardType = RewardFactory.getReward(type);
        if (this.rewardType == null) {
            throw new IllegalArgumentException("Tipo di ricompensa non valido");
        }
    }
   

    // Metodo per calcolare i punti utilizzando la strategia corrente
    public int calculatePoints(int value) {
        if (strategy == null) {
            throw new IllegalStateException("Strategy not set");
        }
        return strategy.calculatePoints(value);
    }

    // Metodo per aggiungere i punti calcolati all'utente
    public void addPoints(User user, int value) {
        int points = calculatePoints(value);  // Usa la strategia per calcolare i punti
        user.increaseGPBalance(points);     // Incrementa i punti dell'utente
        System.out.println("Aggiunti " + points + " Green Points all'utente: " + user.getUsername());
    }

    
    
    // Genera solo il codice della ricompensa senza salvare nulla
    public String generateRewardCode(User user) {
        if (rewardType == null) {
            throw new IllegalStateException("Reward type not set");
        }

        return rewardType.redeem(user);  // Ritorna il risultato del riscatto
    }
    

   



}
