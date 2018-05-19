package server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import client.window.Game;
import common.Entity;
import common.Map;
import common.Player;
import common.State;
import common.Entity.Direction;
import common.Entity.Kind;

/**
 * State storage on the server end
 * 
 * @author kgurushankar
 * @version 18.5.16
 */
public class ServerState {
	public Map map;
	public Vector<Entity> items; // NPC and projectiles
	public HashMap<ServerConnection, Player> players;

	public ServerState(Config c) {
		this.items = new Vector<Entity>();
		this.players = new HashMap<ServerConnection, Player>();
		this.map = MapGenerator.generateMap(c.mapSize);
	}

	public Game addConnection(ServerConnection sc) {
		int[] spawn = map.spawnPoint();
		Player me = new Player(spawn[0] * Game.tileSize, spawn[1] * Game.tileSize, Kind.NINJA, Direction.DOWN);
		System.out.println(Arrays.toString(spawn));
		players.put(sc, me);
		State o = generateState(sc);
		return new Game(map, o);
	}

	public void removeConnection(ServerConnection sc) {

	}

	/**
	 * 
	 * @param c
	 * @return
	 */
	public State generateState(ServerConnection c) {
		if (!players.containsKey(c)) {
			return null;
		} else {
			Vector<Entity> items = new Vector<Entity>(this.items);
			Player p = null;
			for (ServerConnection sc : players.keySet()) {
				if (c != sc) {
					items.add(players.get(sc));
				} else {
					p = players.get(sc);
				}
			}
			return new State(items, p);
		}
	}

	public Map getMap() {
		return map;
	}

	public String toString() {
		return players.toString();
	}

	public void updateEntities() {
		for (Entity e : items) {
			if (e.destroy()) {
				items.remove(e);
			}
			for (ServerConnection sc : players.keySet()) {
				Player p = players.get(sc);
				// TODO collision detection here
			}
		}
	}
}
