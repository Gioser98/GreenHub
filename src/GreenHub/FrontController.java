package GreenHub;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FrontController {
    
    private C_UserController userController;
    private C_TransactionController transactionController;
    private C_RewardController rewardController;
    private C_VehicleController vehicleController;
    private C_ReservationController reservationController;
    private C_ChargingStationController chargingStationController;
    private DataManager dataSaver = new DataManager();
    private View view = new View();
    private Reward rewards = new Reward();
    
    public FrontController() {
        this.userController = new C_UserController(dataSaver, view);
        this.transactionController = new C_TransactionController(dataSaver, view);
        this.rewardController = new C_RewardController(rewards, dataSaver, view);
        this.vehicleController = new C_VehicleController(dataSaver, view);
        this.reservationController = new C_ReservationController(dataSaver, view);
        this.chargingStationController = new C_ChargingStationController(dataSaver, view);
    }

    public FrontController(DataManager dataSaver, View view, Reward rewards) {
        this.userController = new C_UserController(dataSaver, view);
        this.transactionController = new C_TransactionController(dataSaver, view);
        this.rewardController = new C_RewardController(rewards, dataSaver, view);
        this.vehicleController = new C_VehicleController(dataSaver, view);
        this.reservationController = new C_ReservationController(dataSaver, view);
        this.chargingStationController = new C_ChargingStationController(dataSaver, view);
    }

    // Metodi di accesso per UserController
    public void registerUser() {
        userController.registerUser();
    }

    public User loginUser() {
        return userController.loginUser();
    }

    public void registerCharge(User user, Vehicle vehicle, ChargingStation cs, LocalDateTime currentTime, Charge newCharge, Time startTime, double discount) {
        chargingStationController.registerCharge(user, vehicle, cs, currentTime, newCharge, startTime, discount);
    }

    // Metodi di accesso per TransactionController
    public String registerTransaction(User user, Vehicle vehicle, ChargingStation station, Charge charge, double amount, PaymentStrategy strategy) {
        return transactionController.registerTransaction(user, vehicle, station, charge, amount, strategy);
    }

    // Metodi di accesso per RewardController
    public void assignGreenPoints(User user, GreenPointsStrategy strategy, int value) {
        rewardController.assignGreenPoints(user, strategy, value);
    }

    public void redeemReward(User user, String rewardType) {
        rewardController.redeemReward(user, rewardType);
    }

    // Metodi di accesso per VehicleController
    public void addVehicle(User owner, Vehicle vehicle) {
        vehicleController.addVehicle(owner, vehicle);
    }

    // Metodi di accesso per ReservationController
    public void reserveSlot(User currentUser, Vehicle currentVehicle, ChargingStation currentCS, int startingSlot, int endingSlot) {
        reservationController.reserveSlot(currentUser, currentVehicle, currentCS, startingSlot, endingSlot);
    }

    public boolean reserveSlotIfAvailable(User user, Vehicle vehicle, ChargingStation station, int startSlot, int endSlot) {
        return reservationController.reserveSlotIfAvailable(user, vehicle, station, startSlot, endSlot);
    }

    public boolean isDiscountCodeValid(String discountCode, User user) {
        return rewardController.isDiscountCodeValid(discountCode, user);
    }

    public List<Reservation> getAllReservations() {
        return reservationController.getAllReservations();
    }

    // Metodi di accesso per ChargingStationController
    public ChargingStation chooseStation(Vehicle vehicle) {
        return chargingStationController.chooseStation(vehicle);
    }

    public double calculateRechargeCost(User user, ChargingStation chargingStation, double discount) {
        return chargingStationController.calculateRechargeCost(user, chargingStation, discount);
    }

    public List<ChargingStation> getNearAvailableStation(User user) {
        return chargingStationController.getNearAvailableStation(user);
    }

    public ArrayList<ChargingRate> getChargingRateList() {
        return dataSaver.getChargingRateList();
    }

    public Map<String, String> getRedeemedRewardsMap() {
        return rewardController.getRedeemedRewardsMap();
    }

    // Metodi per salvare e caricare i dati
    public void saveAll() {
        try {
            dataSaver.saveAll();
        } catch (IOException e) {
            view.showMessage("Errore durante il salvataggio: " + e.getMessage());
        }
    }

    public void readAll() {
        try {
            dataSaver.readAll();
        } catch (IOException e) {
            view.showMessage("Errore durante la lettura: " + e.getMessage());
        }
    }

    // Metodo di debug
    public void printino() {
        view.printChargingRateList(dataSaver.getChargingRateList());
        view.printEnergySupplierList(dataSaver.getEnergySupplierList());
        view.printChargingStationList(dataSaver.getChargingStationList());
        view.printRewardList(dataSaver.getRewardList());
        view.printUserList(dataSaver.getUserList());
        view.printVehicleList(dataSaver.getVehicleList());
        view.printTransactionList(dataSaver.getTransactionList());
        view.printReservationList(dataSaver.getReservationList());
        view.printRedeemedRewardsMap(dataSaver.getRedeemedRewardsMap());
    }
}
