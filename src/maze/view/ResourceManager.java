package maze.view;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ResourceManager {
	private HashMap<String, Image> unscaledImages;
	private HashMap<String, Image> scaledImages;

	private int scale = 256;

	public ResourceManager() throws FileNotFoundException {
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
			
		}
	}

	public void scaleAllImages() {
		scaledImages = new HashMap<>();
		
		for(HashMap.Entry<String, Image> entry : unscaledImages.entrySet()) {
		    String key = entry.getKey();
		    Image unscaledImage = entry.getValue();
		    Image scaledImage = unscaledImage.getScaledInstance(scale, scale, Image.SCALE_SMOOTH);
		    scaledImages.put(key, scaledImage);
		}
	}
	
	public void adjustScale(int amount) {
		int newScale = scale + amount;
		if (newScale > 640) {
			newScale = 640;
		}
		if (newScale < 64) {
			newScale = 64;
		}
		if (newScale != scale) {		
			scale = newScale;
			scaleAllImages();
		}
	}
	
	public int getScale() {
		return scale;
	}

	public Image getImage(String key) {
		return scaledImages.get(key.toLowerCase());
	}
}
