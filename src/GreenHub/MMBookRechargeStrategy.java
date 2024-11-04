package GreenHub;

import java.util.Scanner;

public class MMBookRechargeStrategy implements MainMenuStrategy {
    @Override
    public void execute(UserInterface ui, User user) {
        ChargingStation currentCS;
        Vehicle currentVehicle = user.getPersonalVehicle();
        Scanner in = ui.getScanner();

        ui.getController().getNearAvailableStation(user);
        currentCS = ui.getController().chooseStation(currentVehicle);
        
        // Ottieni lo stato degli slot dalla stazione di ricarica selezionata e visualizzalo tramite View
        if (currentCS != null) {
            var timeSlotStatusList = currentCS.getTimeSlotStatus();
            ui.getView().printTimeTableWithTimeSlots(timeSlotStatusList, currentCS.getId());
        } else {
            ui.getView().showMessage("Stazione non trovata o non disponibile.");
            return;
        }

        ui.getView().showMessage("\nQuali slot vuoi prenotare? Inseriscili nella forma 14-18: ");
        String[] slot = in.next().split("-");
        int startingSlot = Integer.parseInt(slot[0]);
        int endingSlot = Integer.parseInt(slot[1]);

        // Verifica disponibilità e prenotazione tramite il controller
        boolean slotAvailable = ui.getController().reserveSlotIfAvailable(user, currentVehicle, currentCS, startingSlot, endingSlot);

        if (slotAvailable) {
            // **Assegnazione Green Points per la prenotazione**
            GreenPointsStrategy gpStrategy = new GPReservationStrategy();
            int value = 1;  // Punteggio fisso per la prenotazione
            int greenPoints = gpStrategy.calculatePoints(value);
            
            ui.getController().assignGreenPoints(user, gpStrategy, value);
            ui.getView().showMessage("Prenotazione completata. Hai guadagnato " + greenPoints + " Green Point!");
        } else {
            ui.getView().showMessage("Gli slot selezionati non sono disponibili. Riprova con orari diversi.");
        }

        ui.getController().saveAll();
    }
}
