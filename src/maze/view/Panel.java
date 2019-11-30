package maze.view;

import javax.swing.JPanel;

public abstract class Panel extends JPanel {
	private PanelType panelType;
	protected Panel(PanelType type){
		this.panelType = type;
	}

	final PanelType getPanelType(){
		return this.panelType;
	}
}
