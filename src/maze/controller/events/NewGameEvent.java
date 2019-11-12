package maze.controller.events;

import javax.swing.SwingUtilities;

import maze.controller.Controller;
import maze.controller.GameEvent;
import view.MainFrame;
import view.Panel;
import maze.model.Player;
import maze.model.StubbedStaticWorldBuilder;
import maze.model.World;

public class NewGameEvent implements GameEvent {
	@Override
	public void resolveTo(Controller controller, MainFrame mainFrame, Player player, World world) {
		mainFrame.switchToPanel(Panel.LOADING);
		
		StubbedStaticWorldBuilder worldBuilder = new StubbedStaticWorldBuilder();
		world =  worldBuilder.build();
	
		player = new Player(world.getEntryRoom());
		
		controller.setPlayer(player);
		controller.setWorld(world);
		
	    SwingUtilities.invokeLater(new Runnable() {
	        @Override
	        public void run() {
	        	mainFrame.switchToPanel(Panel.GRAPHICS);
	        }
	    });
		
	}
}
