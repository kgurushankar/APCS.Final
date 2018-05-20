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
	private static final long serialVersionUID = -4844537372379836683L;
	/** Contains anything that isnt being actively controlled */
	public volatile Vector<Entity> items;
	public volatile Player me;

	public State(Vector<Entity> items, Player me) {
		this.items = items;
		this.me = me;
	}

	public String toString() {
		return me.toString() + " " + items.toString();
	}
}
