package client.window;

import common.*;
import processing.core.PApplet;

public class DrawingSurface extends PApplet {
	Game g;

	public DrawingSurface() {
		g = new Game();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void draw() {
		background(255);

		g.draw(this);
	}
}
