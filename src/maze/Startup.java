package maze;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import maze.controller.Controller;
import view.MainFrame;

public class Startup {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Controller controller = new Controller();
				MainFrame mainFrame = new MainFrame(controller);	
				controller.setMainFrame(mainFrame);
				
				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainFrame.setSize(1280, 900);
				mainFrame.setVisible(true);
			}
		});
	}

}
