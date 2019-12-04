package maze.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.*;

import maze.Direction;
import maze.controller.Controller;
import maze.controller.GameEventListener;
import maze.controller.events.MoveEvent;
import maze.controller.events.SwitchPanelEvent;
import maze.controller.events.ZoomEvent;
import maze.model.question.QuestionDataSource;
import maze.view.panel.GraphicsPanel;
import maze.view.panel.InventoryPanel;
import maze.view.panel.ItemSelectorPanel;
import maze.view.panel.LoadingPanel;
import maze.view.panel.MainMenuPanel;
import maze.view.panel.MessagePanel;
import maze.view.panel.QuestionEditorPanel;
import maze.view.panel.QuestionMenuPanel;
import maze.view.panel.QuestionPanel;
import maze.view.panel.QuestionSelectorPanel;
import utils.ResultProvider;
import utils.ResultReceiver;

public class MainFrame extends JFrame implements View {
	private GameEventListener listener;
	private QuestionDataSource dataSource;
	private Map<PanelType, Panel> panels = new LinkedHashMap<>();

	public MainFrame(Controller controller) {
		super("DAbuggers Maze");
		this.listener = controller.getEventListener();
		this.dataSource = controller.getDataSource();
		
		panels.put(PanelType.MAIN_MENU, new MainMenuPanel(listener));
		panels.put(PanelType.LOADING, new LoadingPanel());
		panels.put(PanelType.GRAPHICS, new GraphicsPanel(controller));
		panels.put(PanelType.QUESTION, new QuestionPanel(listener));
		panels.put(PanelType.QUESTION_MENU, new QuestionMenuPanel(listener, dataSource));
		panels.put(PanelType.QUESTION_SELECTOR, new QuestionSelectorPanel(dataSource));
		panels.put(PanelType.QUESTION_EDITOR, new QuestionEditorPanel(dataSource));
		panels.put(PanelType.INVENTORY, new InventoryPanel(controller));
		panels.put(PanelType.ITEM_SELECTOR, new ItemSelectorPanel(controller));
		panels.put(PanelType.MESSAGE, new MessagePanel());
		
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.BOTH;
		
		for (JPanel panel : panels.values()) {
			add(panel, gc);
		}
		
		switchToPanel(PanelType.MAIN_MENU);

		bindKeys();
	}

	@Override
	public MapDetailView getMapDetailView() {
		return (GraphicsPanel) panels.get(PanelType.GRAPHICS);
	}

	public void switchToPanel(PanelType panelType) {
		for (Panel panel : panels.values()) {
			panel.setVisible(false);
		}
		panels.get(panelType).display();
	}

	@Override
	public void promptForResult(Class<? extends ResultProvider> resultProvider, ResultReceiver resultReceiver, Object object) {
		for (Panel panel : panels.values()) {
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
	
	private void bindKeys() {
		GraphicsPanel graphicsPanel = (GraphicsPanel) panels.get(PanelType.GRAPHICS);
		
		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_W, "MoveNorth", (e) -> {
			MoveEvent moveEvent = new MoveEvent(Direction.north);
			listener.onGameEvent(moveEvent);
			SwingUtilities.invokeLater(graphicsPanel::repaint);
		});

		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_A, "MoveWest", (e) -> {
			MoveEvent moveEvent = new MoveEvent(Direction.west);
			listener.onGameEvent(moveEvent);
			SwingUtilities.invokeLater(graphicsPanel::repaint);
		});

		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_S, "MoveSouth", (e) -> {
			MoveEvent moveEvent = new MoveEvent(Direction.south);
			listener.onGameEvent(moveEvent);
			SwingUtilities.invokeLater(graphicsPanel::repaint);
		});

		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_D, "MoveEast", (e) -> {
			MoveEvent moveEvent = new MoveEvent(Direction.east);
			listener.onGameEvent(moveEvent);
			SwingUtilities.invokeLater(graphicsPanel::repaint);
		});

		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_ESCAPE, "PauseMenu", (e) -> {
			SwitchPanelEvent event = new SwitchPanelEvent(PanelType.MAIN_MENU);
			listener.onGameEvent(event);
		});
		
		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_ADD, "ZoomIn", (e) -> {
			ZoomEvent event = new ZoomEvent(Zoom.in);
			listener.onGameEvent(event);
		});
		
		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_SUBTRACT, "ZoomOut", (e) -> {
			ZoomEvent event = new ZoomEvent(Zoom.out);
			listener.onGameEvent(event);
		});
		
		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_I, "ShowInventory", (e) -> {
			SwitchPanelEvent event = new SwitchPanelEvent(PanelType.INVENTORY);
			listener.onGameEvent(event);
		});
	}
}
