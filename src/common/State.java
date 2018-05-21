package common;

import java.io.Serializable;
import java.util.Collection;
import java.util.Vector;

/**
 * State of the game stored on the client end
 * 
 * @author kgurushankar
 * @version 18.5.16
 */
public class State implements Sendable {
	/** Contains anything that isnt being actively controlled */
	public volatile Vector<Entity> items;
	public volatile Player me;

	public State(Vector<Entity> items, Player me) {
		this.items = items;
		this.me = me;
	}

	public State(Entity[] current, Player p) {
		this.items = new Vector<Entity>();
		for (Entity e : current) {
			items.add(e);
		}
		this.me = p;
	}

	public State(String parseableString) {
		String[] s = parseableString.split("\\|");
		this.items = new Vector<Entity>();
		for (int i = 0; i < s.length - 1; i++) {
			try {
				items.add(new Projectile(s[i]));
			} catch (IllegalArgumentException | StringIndexOutOfBoundsException e) {
				items.add(new Player(s[i]));
			}

		}
		me = new Player(s[s.length - 1]);
	}

	public String toString() {
		return me.toString() + " " + items.toString();
	}

	public String toParseableString() {
		String out = "";
		for (Entity e : items) {
			out += e.toParseableString() + "|";
		}
		out += me.toParseableString();
		return out;
	}
}
