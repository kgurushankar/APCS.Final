package client.window;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.sun.glass.events.KeyEvent;

import client.ClientConnection;
import common.State;
import common.Entity.Direction;
import common.Entity.Kind;
import common.Player;
import common.Projectile;
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
		establishConnection();
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
			// scc.sendData("Mu");
		} else if (key == 'a' || key == 'A' || keyCode == KeyEvent.VK_LEFT && cc != null) {
			// cc.sendData("Ml");
		} else if (key == 's' || key == 'S' || keyCode == KeyEvent.VK_DOWN && cc != null) {
			// cc.sendData("Md");
		} else if (key == 'd' || key == 'D' || keyCode == KeyEvent.VK_RIGHT && cc != null) {
			// cc.sendData("Mr");
		} else if (key == 'e') {
			int[] spawn = g.getMap().spawnPoint();
			g.getState().getItems().add(new Player(spawn[0], spawn[1], Kind.SKELETON, Direction.DOWN));
		} else {
			g.keyPressed(this);
		}
	}

	public void mousePressed() {
		State s = g.getState();
		Projectile p = s.getMe().fire(g.getMap());
		if (p != null) {
			s.getItems().add(p);
		}
		if (cc != null) {
			// cc.sendData("Af");
		}
	}

	private void establishConnection() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = "localhost";
		int port = 8888;
		String op1s = JOptionPane.showInputDialog(frame, "Server address", "localhost");
		if (op1s != null)
			url = op1s;
		String op2s = JOptionPane.showInputDialog(frame, "Server Port", "8888");
		if (op2s != null)
			port = Integer.parseInt(op2s);

		boolean singleplayer = false;
		if (singleplayer) {
			g = new Game();
		} else {
			try {
				cc = new ClientConnection(url, port) {
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
}
