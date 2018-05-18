package client.window;

import java.io.Serializable;
import java.util.Vector;

import com.sun.glass.events.KeyEvent;

import common.*;
import common.Entity.Direction;
import common.Entity.Kind;
import processing.core.PApplet;
import server.MapGenerator;

/**
 * For rendering the actual game and controlling all interclass communication
 * 
 * @author kgurushankar
 * @version 18.5.10
 */
public class Game implements Runnable, Serializable {
	private static final long serialVersionUID = -21065957596326209L;
	public static final int mapSize = 100;
	private Map map;
	private transient State state;
	private transient static final boolean cornerLock = false;
	public transient static final int tileSize = 64;

	public Game(Map map, State state) {
		this.map = map;
		this.state = state;
	}

	public Game() {
		map = MapGenerator.generateMap(100);
		try {
			respawn();
		} catch (NullPointerException e) {
			System.out.print("Game Start!");
		}
		state = new State(new Vector<Entity>(), null);
		respawn();
	}

	public void updateState(State state) {
		this.state = state;
	}

	public void draw(PApplet applet) {
		applet.pushMatrix();
		float mx = -state.getMe().getX() + applet.width / 2;
		float my = -state.getMe().getY() + applet.height / 2;
		System.out.println(state.getMe());
		if (cornerLock) {
			applet.translate((mx >= 0) ? 0 : mx, (my >= 0) ? 0 : my);
		} else {
			applet.translate(mx, my);
		}

		map.draw(applet, 0, 0, tileSize, tileSize);
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
		state = new State(state.getItems(),
				new Player(spawn[0] * tileSize, spawn[1] * tileSize, Kind.NINJA, Direction.DOWN));
	}

	public State getState() {
		return state;
	}

	public void run() {
		// for (Entity e : state.getItems()) {
		// e.act(map, state);
		// }
	}

	public Map getMap() {
		return map;
	}

	public String toString() {
		return map.toString();
	}

}
