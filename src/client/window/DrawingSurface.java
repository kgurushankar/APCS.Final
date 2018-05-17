package client.window;

import processing.core.PApplet;

/**
 * Window that displays the game
 * 
 * @author kgurushankar
 * @version 18.5.10
 */
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

	public void keyPressed() {
		g.keyPressed(this);
	}
	public void mousePressed() 
	{
		g.click();
	}
}
