package maze.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import maze.Direction;
import maze.Zoom;
import maze.controller.Controller;
import maze.controller.events.MoveEvent;
import maze.controller.events.SwitchPanelEvent;
import maze.controller.events.ZoomEvent;
import maze.view.panel.GraphicsPanel;
import maze.view.panel.LoadingPanel;
import maze.view.panel.MainMenuPanel;
import maze.view.panel.QuestionEditorPanel;
import maze.view.panel.QuestionMenuPanel;
import maze.view.panel.QuestionPanel;
import maze.view.panel.QuestionSelectorPanel;

public class MainFrame extends JFrame implements ResultPrompter {
	private Controller controller;
	private LinkedList<Panel> panels = new LinkedList<>();

	public MainFrame(Controller controller) {
		super("DAbuggers Maze");
		this.controller = controller;
		
		panels.add(new MainMenuPanel(controller));
		panels.add(new LoadingPanel());
		panels.add(new GraphicsPanel(controller));
		panels.add(new QuestionPanel(controller));
		panels.add(new QuestionMenuPanel(controller));
		panels.add(new QuestionSelectorPanel(controller));
		panels.add(new QuestionEditorPanel(controller));

		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.BOTH;
		
		for (JPanel panel : panels) {
			add(panel, gc);
		}
		
		switchToPanel(PanelType.MAIN_MENU);

		bindKeys();
	}
	
	public JPanel getPanel(PanelType panelType) {
		JPanel returnPanel = null;		
		for (Panel panel : panels) {
			if (panel.getPanelType() == panelType) {
				returnPanel = panel;
			}
		}
		return returnPanel;
	}


	public void switchToPanel(PanelType panelType) {
		for (Panel panel : panels) {
			panel.setVisible(panel.getPanelType() == panelType);
		}
	}

	@Override
	public void promptForResult(Class<? extends ResultProvider> resultProvider, ResultReceiver resultReceiver, Object object) {
		for (Panel panel : panels) {
			if (panel.getClass() == resultProvider) {
				if (panel instanceof ResultProvider) {
					switchToPanel(panel.getPanelType());
					ResultProvider rp = (ResultProvider) panel;
					rp.getResult(resultReceiver, object);
					break;
				}
			}
		}	
	}
	
	public void bindKeys() {
		GraphicsPanel graphicsPanel = (GraphicsPanel) getPanel(PanelType.GRAPHICS);
		
		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_W, "MoveNorth", (e) -> {
			MoveEvent moveEvent = new MoveEvent(Direction.north);
			controller.onGameEvent(moveEvent);
		});

		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_A, "MoveWest", (e) -> {
			MoveEvent moveEvent = new MoveEvent(Direction.west);
			controller.onGameEvent(moveEvent);
		});

		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_S, "MoveSouth", (e) -> {
			MoveEvent moveEvent = new MoveEvent(Direction.south);
			controller.onGameEvent(moveEvent);
		});

		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_D, "MoveEast", (e) -> {
			MoveEvent moveEvent = new MoveEvent(Direction.east);
			controller.onGameEvent(moveEvent);
		});

		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_ESCAPE, "PauseMenu", (e) -> {
			SwitchPanelEvent event = new SwitchPanelEvent(PanelType.MAIN_MENU);
			controller.onGameEvent(event);
		});
		
		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_ADD, "ZoomIn", (e) -> {
			ZoomEvent event = new ZoomEvent(Zoom.in);
			controller.onGameEvent(event);
		});
		
		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_SUBTRACT, "ZoomOut", (e) -> {
			ZoomEvent event = new ZoomEvent(Zoom.out);
			controller.onGameEvent(event);
		});
	}
}
