package common;

import java.util.Vector;

import processing.core.PApplet;
import server.MapGenerator;

public class Game {
	private Map map;
	private Vector<Entity> items;
	private Player me;

	public Game() {
		map = MapGenerator.generateMap(100);
		int[] spawn = map.spawnPoint();
		me = new Player(spawn[0], spawn[1], (byte) 1);
		items = new Vector<Entity>();
	}

	public void draw(PApplet applet) {
		applet.pushMatrix();
		applet.translate(me.x - applet.height / 2, me.y - applet.width / 2);
		for (Entity e : items) {
			e.draw(applet);
		}
		me.draw(applet);
		map.draw(applet, 0, 0, 6400, 6400);
	}
}
