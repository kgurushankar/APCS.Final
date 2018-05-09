package common;

import java.util.Vector;

public class State {
	private Vector<Entity> items;
	private Player me;

	public State(Vector<Entity> items, Player me) {
		this.items = items;
		this.me = me;
	}

	public Vector<Entity> getItems() {
		return items;
	}

	public Player getMe() {
		return me;
	}
}
