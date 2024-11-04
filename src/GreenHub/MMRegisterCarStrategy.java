package GreenHub;

import java.io.IOException;


public class MMRegisterCarStrategy implements MainMenuStrategy {
	@Override
public void execute(UserInterface ui, User user) throws IOException {
    View view = ui.getView();
    Controller controller = ui.getController();

    // Richiede i dati della macchina usando i metodi di View
    String maker = view.getCarMaker();
    String model = view.getCarModel();
    int capacity = view.getCarCapacity();

    // Mostra la lista dei ChargingRate disponibili e chiede all'utente di selezionarne uno
    int selectedRateId = view.selectChargingRate(controller.getChargingRateList());

    // Trova il ChargingRate corrispondente all'ID selezionato
    ChargingRate selectedRate = controller.getChargingRateList().stream()
            .filter(rate -> rate.getId() == selectedRateId)
            .findFirst()
            .orElse(null);

    if (selectedRate == null) {
        view.showInvalidChargingRateMessage();
        return;
    }

    // Usa la posizione dell'utente per il veicolo
    Location location = user.getLocation();

    // Crea un nuovo veicolo e lo aggiunge al controller
    Vehicle vehicle = new Vehicle(0, maker, model, selectedRate, location, capacity);
    controller.addVehicle(user, vehicle);

    // Salva lo stato aggiornato dei dati
    controller.saveAll();
}

}
