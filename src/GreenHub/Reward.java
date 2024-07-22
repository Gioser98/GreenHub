package GreenHub;

import java.io.Serializable;
import java.util.ArrayList;

public class Reward implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private String name;
    private String description;
    private int greenPointsCost;
    private int remainingQuantity;
    private GreenPointsStrategy greenPointsStrategy;

    // Getter&Setter
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getGreenPointsCost() {
        return greenPointsCost;
    }
    public void setGreenPointsCost(int greenPointsCost) {
        this.greenPointsCost = greenPointsCost;
    }
    public int getRemainingQuantity() {
        return remainingQuantity;
    }
    public void setRemainingQuantity(int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }
    public GreenPointsStrategy getGreenPointsStrategy() {
        return greenPointsStrategy;
    }
    public void setGreenPointsStrategy(GreenPointsStrategy greenPointsStrategy) {
        this.greenPointsStrategy = greenPointsStrategy;
    }

    // Constructors
    public Reward() {
        // Costruttore vuoto
    }
    public Reward(String name, String description, int greenPointsCost, int remainingQuantity) {
        this.name = name;
        this.description = description;
        this.greenPointsCost = greenPointsCost;
        this.remainingQuantity = remainingQuantity;
    }
    public Reward(String name, String description, int greenPointsCost, int remainingQuantity, GreenPointsStrategy greenPointsStrategy) {
        this.name = name;
        this.description = description;
        this.greenPointsCost = greenPointsCost;
        this.remainingQuantity = remainingQuantity;
        this.greenPointsStrategy = greenPointsStrategy;
    }
    public Reward (GreenPointsStrategy greenPointsStrategy){
        this.greenPointsStrategy = greenPointsStrategy;
    }

    // Methods

    /**
     * Calcola i green points utilizzando la strategia fornita.
     */
    public int calculateGreenPoints(int value) {
        if (greenPointsStrategy != null) {
            return greenPointsStrategy.calculatePoints(value);
        } else {
            System.out.println("Nessuna strategia di calcolo dei punti definita.");
            return 0;
        }
    }

    /**
     * Restituisce la ricompensa in un formato leggibile.
     */
    @Override
    public String toString() {
        return name + ": " + description + ". Costo in GP: " + greenPointsCost + ". Quantità rimanente: " + remainingQuantity;
    }

    // Permette di effettuare lo sconto di tutte le ricompense
    public void discountReward(int value) {
        System.out.println("Che tipo di sconto vuoi applicare?");
    }

    public static void printAll (ArrayList<Reward> rewardList) {
        int i = 1;
        for (Reward r : rewardList) {
            System.out.println(i + ") " + r);
            i++;
        }
    }
}
