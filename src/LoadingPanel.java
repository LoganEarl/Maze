import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class LoadingPanel extends JPanel {
	public LoadingPanel() {
		Color color = new Color(100,100,100);
		setBackground(color);
		
		JLabel loadingLabel  = new JLabel("Loading");
		loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		Font font = new Font(loadingLabel.getFont().getFamily(), Font.BOLD, 60);
		loadingLabel.setFont(font);

		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridy = 0;
		gc.gridx = 0;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.BOTH;
		add(loadingLabel, gc);		
	}
}
