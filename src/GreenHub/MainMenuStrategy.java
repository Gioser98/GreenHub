package GreenHub;

import java.io.IOException;

public interface MainMenuStrategy {
	void execute(UserInterface view, User user) throws IOException;
}