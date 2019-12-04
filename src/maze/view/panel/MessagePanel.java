package maze.view.panel;

import maze.view.Panel;
import maze.view.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessagePanel extends Panel implements ActionListener, ResultProvider {
	private ResultReceiver resultReceiver;
	private GridBagConstraints gc;

	private JLabel labelHeading = new JLabel("");
	private JButton buttonOK  = new JButton("OK");
	
	public MessagePanel() {
		super(PanelType.MESSAGE);
		setBackground(ViewUtils.backgroundColor);

		setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.insets = new Insets(10, 10, 50, 10);

		ViewUtils.insertComponent(this, gc, labelHeading,		0, 0, 2, 1, 800,  60);
		ViewUtils.insertComponent(this, gc, buttonOK, 			1, 2, 1, 1, 800,  80);
	
		ViewUtils.componentSetFont(labelHeading, 48);
		labelHeading.setHorizontalAlignment(SwingConstants.CENTER);
		labelHeading.setForeground(ViewUtils.blueColor);

		buttonOK.setFocusPainted(false);
		ViewUtils.componentSetFont(buttonOK, 24);
		ViewUtils.componentColorBorder(buttonOK, ViewUtils.blueColor);
		buttonOK.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
    	resultReceiver.processResult(ResultGeneric.ACCEPT);
	}

	@Override
	public void getResult(ResultReceiver resultReceiver, Object object) {
		this.resultReceiver = resultReceiver;
		if (object instanceof String) {
			labelHeading.setText((String) object);
		}
	}
}