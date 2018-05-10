package client.window;

import java.util.Vector;

import com.sun.glass.events.KeyEvent;

import common.*;
import processing.core.PApplet;
import server.MapGenerator;

public class Game {
	public static final int mapSize = 100;
	public static final int tileSize = 64;
	private Map map;
	private State state;

	private Game() {
		map = MapGenerator.generateMap(mapSize);
		respawn();
		state = new State(new Vector<Entity>(), null);
		respawn();
	}

	public void updateState(State state) {
		this.state = state;
	}

	public void draw(PApplet applet) {
		applet.pushMatrix();
		applet.translate(-state.getMe().getX() + applet.width / 2, -state.getMe().getY() + applet.height / 2);
		map.draw(applet, 0, 0, tileSize * mapSize, tileSize * mapSize);
		for (Entity e : state.getItems()) {
			e.draw(applet);
		}
		state.getMe().draw(applet);

		applet.popMatrix();
	}

	public void keyPressed(PApplet applet) {
		if (applet.key == 'w' || applet.key == 'W' || applet.keyCode == KeyEvent.VK_UP) {
			state.getMe().moveY(false, map);
		} else if (applet.key == 'a' || applet.key == 'A' || applet.keyCode == KeyEvent.VK_LEFT) {
			state.getMe().moveX(false, map);
		} else if (applet.key == 's' || applet.key == 'S' || applet.keyCode == KeyEvent.VK_DOWN) {
			state.getMe().moveY(true, map);
		} else if (applet.key == 'd' || applet.key == 'D' || applet.keyCode == KeyEvent.VK_RIGHT) {
			state.getMe().moveX(true, map);
		}
	}

	public void respawn() {
		int[] spawn = map.spawnPoint();
		state = new State(state.getItems(), new Player(spawn[0] * tileSize, spawn[1] * tileSize, (byte) 1));
	}
	public Vector<Entity> getItems() 
	{
		return items;
	}
}
