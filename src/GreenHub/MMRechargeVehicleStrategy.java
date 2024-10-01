package GreenHub;

import java.io.IOException;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;

public class MMRechargeVehicleStrategy implements MainMenuStrategy {
    


    @Override
    public void execute(UserInterface ui, User user) throws IOException {
        Vehicle currentVehicle = user.getPersonalVehicle();
        if (currentVehicle == null || currentVehicle.getType() != 0) {  // Verifica che l'utente abbia un veicolo elettrico
        System.out.println("Funzione non disponibile! Devi avere un veicolo elettrico.");
        return;
}

        List<ChargingStation> chargingStationList = ui.getController().getChargingStationList();  

        ChargingStation.getNearAvailableStation(user, chargingStationList);  // Mostra le stazioni vicine
        ChargingStation currentCS = ChargingStation.chooseStation(currentVehicle, chargingStationList);  // Seleziona la stazione

        LocalDateTime currentTime = LocalDateTime.now();  // Tempo corrente per la registrazione
        Charge newCharge = new Charge(currentCS, currentVehicle, currentTime);
       
        // LocalDateTime startTime = currentTime;  // Assumiamo l'inizio immediato
        
        // Estrai ora e minuto da LocalDateTime per creare un oggetto Time
        Time startTime = new Time(currentTime.getHour(), currentTime.getMinute());

        
        ui.getController().registerCharge(user, currentVehicle, currentCS, currentTime, newCharge, startTime);
        ui.getController().registerTransaction(user, currentVehicle, currentTime, newCharge);
    }
}
