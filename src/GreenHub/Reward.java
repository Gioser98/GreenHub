package GreenHub;

public class Reward {
    private GreenPointsStrategy strategy;

    

    // Setter per impostare la strategia
    public void setStrategy(GreenPointsStrategy strategy) {
        this.strategy = strategy;
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
}
