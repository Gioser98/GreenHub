package GreenHub;

import java.io.IOException;

public class MMExitStrategy implements MainMenuStrategy {
    @Override
    public void execute(UserInterface ui, User user) throws IOException {
        ui.getController().saveAll();
        System.exit(0);
    }
}