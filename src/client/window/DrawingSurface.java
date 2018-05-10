package client.window;

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
		g.run();
		g.draw(this);
		
	}

	public void keyPressed() {
		if (key == 'R') {
			g = new Game();
		} else if (key == 'r') {
			g.respawn();
		} else {
			g.keyPressed(this);
		}
	}
	public void mousePressed() 
	{
		g.getState().getMe().fire(g.getMap(), g.getState());
	}
}
