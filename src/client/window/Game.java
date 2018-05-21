package client.window;

import java.io.Serializable;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

import com.sun.glass.events.KeyEvent;

import common.*;
import common.Entity.Kind;
import processing.core.PApplet;
import server.MapGenerator;

/**
 * For rendering the actual game and controlling all interclass communication
 * 
 * @author kgurushankar
 * @version 18.5.10
 */
public class Game implements Runnable, Sendable {

	public transient static final int mapSize = 100;
	public static final boolean cornerLock = false;
	public transient static final int tileSize = 64;
	private volatile Map map; // really the only thing sent
	volatile State state;

	public Game(Map map, State state) {
		this.map = map;
		this.state = state;
	}

	public Game() {
		map = MapGenerator.generateMap(100);
		state = new State(new Vector<Entity>(), null);
		respawn();
	}

	public Game(String parseable) {
		String s[] = parseable.split("::");
		map = new Map(s[0]);
		state = new State(s[1]);
	}

	@Override
	public String toParseableString() {
		return map.toParseableString() + "::" + state.toParseableString();
	}

	public synchronized void updateState(State state) {
		this.state = state;
	}

	public void draw(PApplet applet) {

		this.run();
		applet.pushMatrix();
		float mx = -state.me.getX() + applet.width / 2;
		float my = -state.me.getY() + applet.height / 2;
		if (cornerLock) {
			applet.translate((mx >= 0) ? 0 : mx, (my >= 0) ? 0 : my);
		} else {
			applet.translate(mx, my);
		}

		map.draw(applet, 0, 0, tileSize, tileSize);
		for (Entity e : state.items) {
			e.draw(applet);
		}
		state.me.draw(applet);

		applet.popMatrix();
	}

	public void keyPressed(PApplet applet) {
		if (applet.key == 'w' || applet.key == 'W' || applet.keyCode == KeyEvent.VK_UP) {
			state.me.moveY(false, map);
		} else if (applet.key == 'a' || applet.key == 'A' || applet.keyCode == KeyEvent.VK_LEFT) {
			state.me.moveX(false, map);
		} else if (applet.key == 's' || applet.key == 'S' || applet.keyCode == KeyEvent.VK_DOWN) {
			state.me.moveY(true, map);
		} else if (applet.key == 'd' || applet.key == 'D' || applet.keyCode == KeyEvent.VK_RIGHT) {
			state.me.moveX(true, map);
		}
	}

	public void respawn() {
		int[] spawn = map.spawnPoint();
		state = new State(state.items, new Player(spawn[0] * tileSize, spawn[1] * tileSize, Kind.NINJA));
	}

	public AtomicReference<State> getState() {
		return new AtomicReference<State>(state);
	}

	public void run() {

		for (Entity e : state.items) {
			/*
			 * if(e.destroy()) { state.getItems().remove(e); }
			 */
			e.act(map, state);
		}
	}

	public Map getMap() {
		return map;
	}

	public String toString() {
		return map.toString();
	}

}
