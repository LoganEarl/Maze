package maze.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import maze.model.Item;

public class ViewUtils {
	public static final Color backgroundColor = new Color(33, 33, 33);
	public static final Color unselectedColor = new Color(100, 100, 100);
	public static final Color selectedColor = new Color(80, 180, 80);
	
	public static final Color whiteColor = new Color(255, 255, 255);
	public static final Color blackColor = new Color(0, 0, 0);
	public static final Color blueColor = new Color(60, 120, 180);
	public static final Color redColor = new Color(210, 60, 60);
	
	public static void componentSetFont(JComponent Component, float fontSize) {
		Component.setFont(Component.getFont().deriveFont(fontSize));
	}
	
	public static void componentColorBorder(JComponent Component, Color color) {
		Component.setBorder(BorderFactory.createLineBorder(color, 5));
		Component.setForeground(color);
	}
	
	public static void componentSetSize(JComponent Component, int sizex, int sizey) {
		Component.setPreferredSize(new Dimension(sizex, sizey));
	}
	
	public static void insertComponent(JPanel panel, GridBagConstraints gc, JComponent component, int gridx, int gridy, int spanx, int spany, int sizex, int sizey) {
		gc.gridx = gridx;
		gc.gridy = gridy;
		gc.gridwidth = spanx;
		gc.gridheight = spany;
		panel.add(component, gc);
		componentSetSize(component, sizex, sizey);
	}
	
	
	public static JTable getTableItems(Set<Item> usableItems) {	
		Map<String, Integer> items = new HashMap<>();
		
		if (usableItems != null) {
			for (Item item : usableItems) {
				if (items.containsKey(item.getName())) {
					items.put(item.getName(), items.get(item.getName()) + 1);
				} else {
					items.put(item.getName(), 1);
				}
			}
		}
		
		String[] columnNames = {"Quantity", "Item Name"};
		String[][] arrItems = new String[items.size()][2];
		
		int i = -1;
		for (String key : items.keySet()) {
			i++;
			arrItems[i][0] = "" + items.get(key);
			arrItems[i][1] = key;
		}
		
		JTable tableItems = new JTable(arrItems, columnNames);
		tableItems.setRowHeight(36);
		tableItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableItems.setDefaultEditor(Object.class, null);
        tableItems.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableItems.getColumnModel().getColumn(1).setPreferredWidth(650);
		ViewUtils.componentSetFont(tableItems, 24);	
        ViewUtils.componentSetFont(tableItems.getTableHeader(), 24);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableItems.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        
		return tableItems;
	}
}