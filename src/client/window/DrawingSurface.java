package client.window;

import java.io.IOException;

import com.sun.glass.events.KeyEvent;

import client.ClientConnection;
import common.State;
import common.Entity.Kind;
import common.Entity;
import common.Player;
import processing.core.PApplet;

/**
 * Window that displays the game
 * 
 * @author kgurushankar
 * @version 18.5.10
 */
public class DrawingSurface extends PApplet {
	Game g;
	ClientConnection cc;
	private volatile State latest;
	boolean ready;

	public DrawingSurface() {
		boolean singleplayer = false;
		if (singleplayer) {
			g = new Game();
			ready = true;
		} else {
			try {
				cc = new ClientConnection("localhost", 8888) {
					@Override
					public void handleMessage(State s) {
						// g = new Game(g.getMap(), s);
						latest = s;
						ready = true;
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
		if (ready) {
			pushMatrix();
			float mx = -latest.me.getX() + width / 2;
			float my = -latest.me.getY() + height / 2;
			if (Game.cornerLock) {
				translate((mx >= 0) ? 0 : mx, (my >= 0) ? 0 : my);
			} else {
				translate(mx, my);
			}

			g.getMap().draw(this, 0, 0, Game.tileSize, Game.tileSize);
			for (Entity e : latest.items) {
				e.draw(this);

			}
			latest.me.draw(this);

			popMatrix();
		}
	}

	public void keyPressed() {
		if (key == 'R') {
			g = new Game();
		} else if (key == 'r') {
			g.respawn();
		} else if (key == 'w' || key == 'W' || keyCode == KeyEvent.VK_UP && cc != null) {
			cc.sendData("Mu");
		} else if (key == 'a' || key == 'A' || keyCode == KeyEvent.VK_LEFT && cc != null) {
			cc.sendData("Ml");
		} else if (key == 's' || key == 'S' || keyCode == KeyEvent.VK_DOWN && cc != null) {
			cc.sendData("Md");
		} else if (key == 'd' || key == 'D' || keyCode == KeyEvent.VK_RIGHT && cc != null) {
			cc.sendData("Mr");
		} else if (key == 'e') {
			int[] spawn = g.getMap().spawnPoint();
			g.state.items.add(new Player(spawn[0], spawn[1], Kind.SKELETON));
		}
		g.keyPressed(this);
	}

	public void mousePressed() {

		g.state.me.fire(g.getMap());
		if (cc != null) {
			cc.sendData("Af");
		}
	}
}
