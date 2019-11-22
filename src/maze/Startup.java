package maze;

import javax.swing.SwingUtilities;

import maze.controller.Controller;
import maze.view.MainFrame;

public class Startup {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Controller controller = new Controller();
			MainFrame mainFrame = new MainFrame(controller);
			controller.setView(mainFrame);
		});
	}

}
