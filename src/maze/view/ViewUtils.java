package maze.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

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
}