package GreenHub;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class C_ChargingStationController {
	private DataManager dataSaver;
	private View view;

	public C_ChargingStationController(DataManager dataSaver, View view) {
		this.dataSaver = dataSaver;
		this.view = view;
	}

	public ChargingStation chooseStation(Vehicle vehicle) {
		int stationId = view.getStationIdFromUser();

		for (ChargingStation cs : dataSaver.getChargingStationList()) {
			if (cs.getId() == stationId && !cs.isMaintenance()) {
				view.showMessage("Hai scelto: " + cs);
				return cs;
			}
		}

		view.showMessage("Stazione non disponibile.");
		return null;
	}

	public void registerCharge(User user, Vehicle vehicle, ChargingStation cs, LocalDateTime currentTime, Charge newCharge, Time startTime, double discount) {
		newCharge.setChargingStation(cs);
		newCharge.setVehicle(vehicle);
		newCharge.setChargingRate(vehicle.getSupportedRate());
		newCharge.setUser(user);

		// Imposta l'ora di inizio ricarica
		startTime.setHour(currentTime.getHour());
		startTime.setMinute(currentTime.getMinute());

		// Calcola il tempo necessario per la ricarica in ore
		double timeToCharge = vehicle.getCapacity() / vehicle.getSupportedRate().getPower();

		// Utilizza il metodo di Time per calcolare l'ora di fine
		Time endTime = startTime.addDuration(timeToCharge);

		// Imposta l'ora di inizio e fine ricarica
		newCharge.setStartTime(startTime);
		newCharge.setEndTime(endTime);

		// Calcola il costo della ricarica
		double cost = calculateRechargeCost(user, cs, discount);
		newCharge.setCost(cost);
		vehicle.setBatteryLevel(100);

		view.showMessage("Ricarica registrata con successo!");
	}

	public double calculateRechargeCost(User user, ChargingStation chargingStation, double discount) {
		double batteryCapacity = user.getPersonalVehicle().getCapacity();
		double currentBatteryLevel = user.getPersonalVehicle().getBatteryLevel();
		double chargingRate = chargingStation.getChargingRateForVehicle(user.getPersonalVehicle());

		double energyToRecharge = batteryCapacity - currentBatteryLevel;
		if (energyToRecharge <= 0) {
			view.showMessage("La batteria è già carica");
			return 0;
		}

		return energyToRecharge * chargingRate * discount;
	}

	public List<ChargingStation> getNearAvailableStation(User user) {
		List<ChargingStation> availableStations = new ArrayList<>();
		Location userLocation = user.getLocation();

		Map<ChargingStation, Double> distanceMap = new HashMap<>();

		for (ChargingStation cs : dataSaver.getChargingStationList()) {
			double distance = userLocation.distance(cs.getLocation());
			distanceMap.put(cs, distance);
		}

		List<Map.Entry<ChargingStation, Double>> sortedStations = new ArrayList<>(distanceMap.entrySet());
		sortedStations.sort(Map.Entry.comparingByValue());

		for (int i = 0; i < Math.min(3, sortedStations.size()); i++) {
			availableStations.add(sortedStations.get(i).getKey());
		}

		if (availableStations.isEmpty()) {
			view.showMessage("Non ci sono stazioni di ricarica disponibili.");
		} else {
			availableStations.forEach(station -> view.showMessage(station.toString()));
		}

		return availableStations;
	}
}
