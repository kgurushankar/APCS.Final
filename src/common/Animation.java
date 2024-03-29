package common;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * An animateble series of images
 * 
 * @author kgurushankar
 * @version 18.5.16
 */
public class Animation {
	private PImage[] images;
	private int currentFrame;
	private String[] location;
	private String extension;
	private boolean complete;

	public Animation(String location[], String extension) {
		this.location = location;
		this.extension = extension;
	}

	public void draw(PApplet applet, int x, int y, int width, int height) {
		if (images == null) {
			images = new PImage[location.length];
			for (int i = 0; i < location.length; i++) {
				images[i] = applet.loadImage(location[i], extension);
			}
		}
		applet.image(images[currentFrame], x, y, width, height);
		nextFrame();
	}

	private void nextFrame() {
		if (currentFrame + 1 > images.length) {
			complete = true;
		}
		currentFrame = (1 + currentFrame) % images.length;
	}

	public void reset() {
		currentFrame = 0;
	}

	public boolean complete() {
		if (complete) {
			complete = false;
			return true;
		} else {
			return false;
		}
	}
}
