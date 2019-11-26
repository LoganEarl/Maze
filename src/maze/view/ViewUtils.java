package maze.view;

import javax.swing.*;
import java.awt.*;

public class ViewUtils {
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
}