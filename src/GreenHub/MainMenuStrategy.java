package GreenHub;

import java.io.IOException;

public interface MainMenuStrategy {
	void execute(UserInterface ui, User user) throws IOException, InterruptedException;
}