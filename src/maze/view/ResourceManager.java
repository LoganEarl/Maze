package maze.view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class ResourceManager {
	private HashMap<String, Image> unscaledImages;
	private HashMap<String, Image> scaledImages;

	private int[] allScales = {16, 24, 32, 48, 64, 96, 128, 192, 256, 384, 512};
	private int scaleIndex = 8;

	public ResourceManager() {
		unscaledImages = new HashMap<>();
		File dir = new File("res/game/");
		File[] dirList = dir.listFiles();
		try {
			if (dirList == null) {
				throw new FileNotFoundException("Could Not Find Resources Files");
			} else {
				for (File file : dirList) {
					try {
						unscaledImages.put(file.getName(), ImageIO.read(file));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			scaleAllImages();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void scaleAllImages() {
		scaledImages = new HashMap<>();
		
		for(HashMap.Entry<String, Image> entry : unscaledImages.entrySet()) {
		    String key = entry.getKey();
		    Image unscaledImage = entry.getValue();
		    Image scaledImage = unscaledImage.getScaledInstance(allScales[scaleIndex], allScales[scaleIndex], Image.SCALE_SMOOTH);
		    scaledImages.put(key, scaledImage);
		}
	}
	
	public void adjustScale(int amount) {
		int newScaleIndex = scaleIndex + amount;
		
		if (newScaleIndex >= allScales.length) {
			newScaleIndex = allScales.length - 1;
		}
		
		if (newScaleIndex < 0) {
			newScaleIndex = 0;
		}

		if (newScaleIndex != scaleIndex) {		
			scaleIndex = newScaleIndex;
			scaleAllImages();
		}
	}
	
	public int getScale() {
		return allScales[scaleIndex];
	}

	public Image getImage(String key) {
		return scaledImages.get(key.toLowerCase());
	}
}
