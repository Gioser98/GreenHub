package GreenHub;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class RechargeVehicleStrategy implements MainMenuStrategy {
    @Override
    public void execute(UserInterface ui, User user) throws IOException {
        if (user.getType() != 0) {  // Verifica che l'utente abbia un veicolo elettrico
            System.out.println("Funzione non disponibile! Devi avere un veicolo elettrico.");
            return;
        }

        Vehicle currentVehicle = user.getPersonalVehicle();
        List<ChargingStation> chargingStationList = ui.getController().getChargingStationList();  // Assumi che il controller gestisca la lista

        ChargingStation.getNearAvailableStation(user, chargingStationList);  // Mostra le stazioni vicine
        ChargingStation currentCS = ChargingStation.chooseStation(currentVehicle, chargingStationList);  // Seleziona la stazione

        LocalDateTime currentTime = LocalDateTime.now();  // Tempo corrente per la registrazione
        Charge newCharge = new Charge(currentCS, currentVehicle, currentTime);
        LocalDateTime startTime = currentTime;  // Assumiamo l'inizio immediato

        registerCharge(user, currentVehicle, currentCS, currentTime, newCharge, startTime);
        registerTransaction(user, currentVehicle, currentTime, newCharge);
    }

    private void registerCharge(User user, Vehicle vehicle, ChargingStation cs, LocalDateTime currentTime, Charge newCharge, LocalDateTime startTime) {
        // Logica per registrare la ricarica
        System.out.println("Ricarica registrata con successo.");
    }

    private void registerTransaction(User user, Vehicle vehicle, LocalDateTime currentTime, Charge newCharge) {
        // Logica per registrare la transazione
        System.out.println("Transazione registrata con successo.");
    }
}
