package maze.view.panel;

import maze.view.Panel;
import utils.ResultProvider;
import utils.ResultReceiver;
import maze.view.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ControlsPanel extends Panel implements ActionListener, ResultProvider {
	private ResultReceiver resultReceiver;
	private GridBagConstraints gc;

	private JButton buttonMainMenu  = new JButton("Close");
	
	public ControlsPanel() {
		super(PanelType.CONTROLS);
		setBackground(ViewUtils.blackColor);

		setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.insets = new Insets(10, 10, 00, 10);

		ViewUtils.insertComponent(this, gc, new JLabel(),		0, 1, 2, 1, 800,  700);
		ViewUtils.insertComponent(this, gc, buttonMainMenu, 	1, 2, 1, 1, 800,  80);

		buttonMainMenu.setFocusPainted(false);
		ViewUtils.componentSetFont(buttonMainMenu, 24);
		ViewUtils.componentColorBorder(buttonMainMenu, ViewUtils.blueColor);
		buttonMainMenu.addActionListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(ViewUtils.backgroundColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		File file = new File("res/controls.png");
		try {
			Image image = ImageIO.read(file);
			g.drawImage(image, (this.getWidth() - image.getWidth(null)) / 2, (getHeight() - image.getHeight(null)) / 2 - 50, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
    	resultReceiver.processResult(null);  	
	}

	@Override
	public void getResult(ResultReceiver resultReceiver, Object object) {
		this.resultReceiver = resultReceiver;
	}
}