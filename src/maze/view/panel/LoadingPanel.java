package maze.view.panel;

import javax.swing.*;
import java.awt.*;

import maze.view.Panel;
import maze.view.PanelType;
import maze.view.ViewUtils;

public class LoadingPanel extends Panel {
	public LoadingPanel() {
		super(PanelType.LOADING);
		setBackground(ViewUtils.backgroundColor);
		
		JLabel loadingLabel  = new JLabel("Loading");
		loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		Font font = new Font(loadingLabel.getFont().getFamily(), Font.BOLD, 60);
		loadingLabel.setFont(font);
		loadingLabel.setForeground(new Color(100,100,100));

		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		gc.gridy = 0;
		gc.gridx = 0;
		gc.weightx = 1;
		gc.weighty = 1;
		//gc.fill = GridBagConstraints.BOTH;
		add(loadingLabel, gc);		
	}
}
