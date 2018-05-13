package client.window;

import java.io.IOException;

import client.ClientConnection;
import common.State;
import processing.core.PApplet;

public class DrawingSurface extends PApplet {
	Game g;

	public DrawingSurface() {
		// g = new Game();
		try {
			ClientConnection cc = new ClientConnection("localhost", 8888) {
				@Override
				public void handleMessage(State s) {
					g.updateState(s);
				}
			};
			g = cc.getGame();
			new Thread(cc).start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void setup() {
		frameRate(30);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void draw() {
		background(0);
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

	public void mousePressed() {
		g.getState().getMe().fire(g.getMap(), g.getState());
	}
}
