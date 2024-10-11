package GreenHub;

// Implementazione della strategia Recharge

class GPRechargeStrategy implements GreenPointsStrategy {
    @Override
    public int calculatePoints(int value) {
        // Implementazione della logica per calcolare i punti per la ricarica
        return value / 5; //value deve corrispondere alla percentuale di ricarica (/5 può essere sostituito, dobbiamo ragionare)
        
    }
}