package GreenHub;

// Implementazione della strategia Reservation
class ReservationStrategy implements GreenPointsStrategy {
    @Override
    public int calculatePoints(int value) {
        // Implementazione della logica per calcolare i punti per il noleggio
        return value / value; // in questo modo si ottiene un GP quando si effettua una prenotazione 
    }
}
