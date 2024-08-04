package GreenHub;

public class RechargeVehicleStrategy implements MainMenuStrategy {
	@Override
	public void execute(View view, User user) {       
		if (user.getType() != 0) {
			System.out.println("Funzione non disponibile! Devi avere un veicolo elettrico");
			break;
		}

		
		ChargingStation.getNearAvailableStation(currentUser, chargingStationList);
		currentCS = ChargingStation.chooseStation(currentVehicle, chargingStationList);
		registerCharge(currentUser, currentVehicle, currentCS, currentTime, newCharge, startTime);
		registerTransaction(currentUser, currentVehicle, currentTime, newCharge, newTransaction);

		break;
	}
}