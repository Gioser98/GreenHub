package GreenHub;

import java.io.IOException;

public class MMExitStrategy implements MainMenuStrategy {
    @Override
    public void execute(UserInterface view, User user) {
        System.exit(0);
    }
}