package GreenHub;

import java.io.IOException;

public class ExitStrategy implements MainMenuStrategy {
    @Override
    public void execute(View view, User user) {
        System.exit(0);
    }
}