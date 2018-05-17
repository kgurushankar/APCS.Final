package client.window;

import java.util.Vector;

import com.sun.glass.events.KeyEvent;

import common.Entity;
import common.Map;
import common.Player;
import common.State;
import processing.core.PApplet;
import server.MapGenerator;

/**
 * For rendering the actual game and controlling all interclass communication
 * 
 * @author kgurushankar
 * @version 18.5.10
 */
public class Game {
	public static final int mapSize = 100;
	public static final int tileSize = 64;
	private Map map;
	private Vector<Entity> items;
	private Player me;

	public Game() {
		map = MapGenerator.generateMap(mapSize);
		respawn();
		items = new Vector<Entity>();
	}

	public void draw(PApplet applet) {
		applet.pushMatrix();
		applet.translate(-me.getX() + applet.width / 2, -me.getY() + applet.height / 2);
		map.draw(applet, 0, 0, tileSize * mapSize, tileSize * mapSize);
		for (Entity e : items) {
			e.draw(applet);
		}
		me.draw(applet);

		applet.popMatrix();
	}

	public void keyPressed(PApplet applet) {
		if (applet.key == 'w' || applet.key == 'W' || applet.keyCode == KeyEvent.VK_UP) {
			me.moveY(false, map);
		} else if (applet.key == 'a' || applet.key == 'A' || applet.keyCode == KeyEvent.VK_LEFT) {
			me.moveX(false, map);
		} else if (applet.key == 's' || applet.key == 'S' || applet.keyCode == KeyEvent.VK_DOWN) {
			me.moveY(true, map);
		} else if (applet.key == 'd' || applet.key == 'D' || applet.keyCode == KeyEvent.VK_RIGHT) {
			me.moveX(true, map);
		}
	}

	public void click() 
	{
		me.fire(map, new State(items,me));
		me.act(map, new State(items, me));
	}
	public void respawn() {
		int[] spawn = map.spawnPoint();
		me = new Player(spawn[0] * tileSize, spawn[1] * tileSize, (byte) 1);
	}
}
