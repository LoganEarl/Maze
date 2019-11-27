package maze.view.panel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import maze.view.Panel;
import maze.view.PanelType;
import maze.view.ViewUtils;

public class LoadingPanel extends Panel {
	public LoadingPanel() {
		setBackground(ViewUtils.backgroundColor);
		
		JLabel loadingLabel  = new JLabel("Loading");
		loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		Font font = new Font(loadingLabel.getFont().getFamily(), Font.BOLD, 60);
		loadingLabel.setFont(font);
		loadingLabel.setForeground(new Color(100,100,100));

		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		//ViewUtils.insertComponent(this, gc, loadingLabel, 0, 0, 1, 1, 500, 200);
		gc.gridy = 0;
		gc.gridx = 0;
		gc.weightx = 1;
		gc.weighty = 1;
		//gc.fill = GridBagConstraints.BOTH;
		add(loadingLabel, gc);		
	}

	@Override
	public PanelType getPanelType() {
		return PanelType.LOADING;
	}
}
