package maze.view.panel;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.controller.events.SwitchPanelEvent;
import maze.model.Player;
import maze.model.World;
import maze.view.Panel;
import maze.view.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryPanel extends Panel implements ActionListener {
	private Controller controller;
	private GridBagConstraints gc;

	private JLabel labelHeading = new JLabel("Inventory");
	private JTable tableItems;
	private JScrollPane scrollPane;
	private JButton buttonClose  = new JButton("Close");
	
	public InventoryPanel(Controller controller) {
		super(PanelType.INVENTORY);
		setBackground(ViewUtils.backgroundColor);

		this.controller = controller;
		buttonClose.setFocusPainted(false);

		setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.insets = new Insets(10, 10, 10, 10);

		ViewUtils.insertComponent(this, gc, labelHeading,		0, 0, 2, 1, 800,  60);
		ViewUtils.insertComponent(this, gc, buttonClose, 		1, 2, 1, 1, 800,  80);
	
		ViewUtils.componentSetFont(labelHeading, 48);
		labelHeading.setHorizontalAlignment(SwingConstants.CENTER);
		labelHeading.setForeground(ViewUtils.blueColor);

		for (Component component : getComponents()) {
			if (component instanceof JButton) {
				JButton button = (JButton) component;			
				button.setFocusPainted(false);
				ViewUtils.componentSetFont(button, 24);
				ViewUtils.componentColorBorder(button, ViewUtils.blueColor);
				button.addActionListener(this);
			}
		}
	}
	
	@Override
	public void display() {
		setVisible(true);
		World world = controller.getWorld();
		Player player = world.getPlayer();
		
		if (scrollPane != null) remove(scrollPane);
		tableItems = ViewUtils.getTableItems(player.getItems());
		scrollPane = new JScrollPane(tableItems);
		ViewUtils.insertComponent(this, gc, scrollPane, 0, 1, 2, 1, 800, 600);
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
    	GameEvent gameEvent = new SwitchPanelEvent(PanelType.GRAPHICS);
		controller.getEventListener().onGameEvent(gameEvent);
	}
}