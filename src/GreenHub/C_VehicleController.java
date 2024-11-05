package GreenHub;

import java.util.Random;

public class C_VehicleController {
	private DataManager dataSaver;
	private View view;

	public C_VehicleController(DataManager dataSaver, View view) {
		this.dataSaver = dataSaver;
		this.view = view;
	}

	public void addVehicle(User owner, Vehicle vehicle) {
		if (owner == null || vehicle == null) {
			throw new IllegalArgumentException("Il proprietario o il veicolo non possono essere nulli!");
		}

		vehicle.setOwner(owner);
		dataSaver.getVehicleList().add(vehicle);

		if (owner.getPersonalVehicle() == null) {
			owner.setPersonalVehicle(vehicle);
		}
		int newBatteryLevel = generateRandomBatteryLevel(owner);
		vehicle.setBatteryLevel(newBatteryLevel);
		view.showMessage("Veicolo registrato correttamente!");
	}

	private int generateRandomBatteryLevel(User user) {
		int maxBatteryLevel = (int) user.getPersonalVehicle().getCapacity();
		return new Random().nextInt(maxBatteryLevel + 1);
	}

}