package maze.view.panel;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.controller.events.ResultEvent;
import maze.model.Item;
import maze.model.Player;
import maze.model.World;
import maze.model.question.Question;
import maze.view.Panel;
import utils.ResultGeneric;
import utils.ResultProvider;
import utils.ResultReceiver;
import maze.view.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class ItemSelectorPanel extends Panel implements ResultProvider, ResultReceiver, ActionListener {
	private Controller controller;
	private ResultReceiver resultReceiver;
	private GridBagConstraints gc;

	private JLabel labelHeading = new JLabel("Select Item To Use");
	private JTable tableItems;
	private JScrollPane scrollPane;
	private JButton buttonUse  = new JButton("Use Item");
	private JButton buttonCancel  = new JButton("Cancel");
	
	public ItemSelectorPanel(Controller controller) {
		super(PanelType.ITEM_SELECTOR);
		setBackground(ViewUtils.backgroundColor);

		this.controller = controller;

		setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.insets = new Insets(10, 10, 10, 10);

		ViewUtils.insertComponent(this, gc, labelHeading,		0, 0, 2, 1, 800,  60);
		ViewUtils.insertComponent(this, gc, buttonUse, 			0, 2, 1, 1, 390,  80);
		ViewUtils.insertComponent(this, gc, buttonCancel, 		1, 2, 1, 1, 390,  80);
	
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
	public void actionPerformed(ActionEvent e) {
	    JButton buttonClicked = ((JButton) e.getSource());
	    
	    if (buttonClicked == buttonUse) {
	    	if (tableItems.getSelectedRow() >= 0) {
	    		World world = controller.getWorld();
				Player player = world.getPlayer();
				
	    		String itemName = (String) tableItems.getValueAt(tableItems.getSelectedRow(), 1);
	    		Item itemSelected = null;
	    		
				for (Item item : player.getItems()) {
					if (item.getName().contentEquals(itemName)) {
						itemSelected = item;
					}
				}
				
				resultReceiver.processResult(itemSelected);
	    	}	
	    } else if (buttonClicked == buttonCancel) {
	    	resultReceiver.processResult(ResultGeneric.CANCEL);
	    }
	}

	@Override
	public void getResult(ResultReceiver resultReceiver, Object object) {
		this.resultReceiver = resultReceiver;
		
		if (object instanceof Question) {
			Question question = (Question) object;
			World world = controller.getWorld();
			Player player = world.getPlayer();
			Set<Item> usableItems = new HashSet<>();
			
			for (Item item : player.getItems()) {
				if (item.answersQuestion(question)) {
					usableItems.add(item);
				}
			}
			
			if (usableItems.size() > 0) {
				if (scrollPane != null) remove(scrollPane);
				tableItems = ViewUtils.getTableItems(usableItems);
				scrollPane = new JScrollPane(tableItems);
				ViewUtils.insertComponent(this, gc, scrollPane, 0, 1, 2, 1, 800, 600);
				revalidate();
			} else {
		    	GameEvent event = new ResultEvent(MessagePanel.class, this, "No Applicable Items In Inventory");
				controller.getEventListener().onGameEvent(event);
			}
		}
	}

	@Override
	public void processResult(Object object) {
		resultReceiver.processResult(ResultGeneric.CANCEL);
	}
}