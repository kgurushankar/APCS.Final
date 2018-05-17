package client.window;

import java.io.IOException;

import com.sun.glass.events.KeyEvent;

import client.ClientConnection;
import common.State;
import common.Entity.Kind;
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

	public DrawingSurface() {
		boolean singleplayer = true;
		if (singleplayer) {
			g = new Game();
		} else {
			try {
				cc = new ClientConnection("localhost", 8888) {
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
		g.draw(this);

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
			g.getState().getItems().add(new Player(spawn[0], spawn[1], Kind.SKELETON));
		} else {
			g.keyPressed(this);
		}
	}
	public void mousePressed() 
	{
		g.click();
	}
}
