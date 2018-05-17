package common;

import java.io.Serializable;
import java.util.Vector;

/**
 * State of the game stored on the client end
 * 
 * @author kgurushankar
 * @version 18.5.16
 */
public class State implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1743699953255618703L;
	/** Contains anything that isnt being actively controlled */
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

	public String toString() {
		return me.toString();
	}
}
