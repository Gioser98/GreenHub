package GreenHub;

import java.util.Random;

public class C_UserController {
    private DataManager dataSaver;
    private View view;

    public C_UserController(DataManager dataSaver, View view) {
        this.dataSaver = dataSaver;
        this.view = view;
    }

    public void registerUser() {
        String username;
        while (true) {
            username = view.getInputUsername();
            if (getUserByUsername(username) != null) {
                view.showMessage("Username già esistente. Scegline un altro.");
            } else {
                break;
            }
        }
        String name = view.getInputName();
        String surname = view.getInputSurname();
        Location location = Location.generateRandomLocation();
        User user = new User(username, 0, name, surname, location);

        addUser(user);
        view.showMessage("Utente registrato correttamente!");
    }

    public User loginUser() {
        String username = view.getInputUsername();
        User user = getUserByUsername(username);
        if (user != null) {
            user.setLocation(Location.generateRandomLocation());
            if (user.getPersonalVehicle() != null) {
                int newBatteryLevel = generateRandomBatteryLevel(user);
                user.getPersonalVehicle().setBatteryLevel(newBatteryLevel);
            } else {
                view.showMessage("Non hai un veicolo associato. Puoi aggiungerne uno successivamente.");
            }
            return user;
        } else {
            view.showMessage("Utente non trovato.");
            return null;
        }
    }

    private void addUser(User user) {
        if (getUserByUsername(user.getUsername()) == null) {
            dataSaver.getUserList().add(user);
        } else {
            view.showMessage("Username già esistente.");
        }
    }

    private User getUserByUsername(String username) {
        return dataSaver.getUserList().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    private int generateRandomBatteryLevel(User user) {
        int maxBatteryLevel = (int) user.getPersonalVehicle().getCapacity();
        return new Random().nextInt(maxBatteryLevel + 1);
    }
}
