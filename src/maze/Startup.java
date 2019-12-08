package maze;

import maze.controller.MazeController;
import maze.view.MainFrame;

import javax.swing.*;

public class Startup {
	public static void main(String[] args) {	
		SwingUtilities.invokeLater(() -> {
			MazeController controller = new MazeController();
			MainFrame mainFrame = new MainFrame(controller);
			controller.setView(mainFrame);

			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainFrame.setVisible(true);
		});
	}
}
