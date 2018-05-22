package server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import client.window.Game;
import common.Enemy;
import common.Entity;
import common.Map;
import common.Player;
import common.State;
import server.settings.Settings.Data;
import common.Entity.Kind;

/**
 * State storage on the server end
 * 
 * @author kgurushankar
 * @version 18.5.16
 */
public class ServerState {
	public volatile Map map;
	public volatile Vector<Entity> items; // NPC and projectiles
	public volatile HashMap<Server.Connection, Player> players;

	public ServerState(Data d) {
		this.items = new Vector<Entity>();
		this.players = new HashMap<Server.Connection, Player>();
		this.map = MapGenerator.generateMap(d.mapSize);
	}

	public Game addConnection(Server.Connection sc, Kind kind) {
		int[] spawn = map.spawnPoint();
		Player me = new Player(spawn[0] * Game.tileSize, spawn[1] * Game.tileSize, kind);
		
		players.put(sc, me);
		State o = generateState(sc);
		return new Game(map, o);
	}

	public void removeConnection(Server.Connection sc) {
		players.remove(sc);
	}

	public State generateState(Server.Connection c) {
		if (!players.containsKey(c)) {
			return null;
		} else {
			Vector<Entity> items = new Vector<Entity>(this.items);
			Player p = null;
			for (Server.Connection sc : players.keySet()) {
				if (c != sc) {
					items.add(players.get(sc));
				} else {
					p = players.get(sc);
				}
			}
			State s = new State(items, p);
			return s;
		}
	}

	public Map getMap() {
		return map;
	}

	public String toString() {
		return players.toString();
	}
}
