package common;

import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;

import processing.core.PApplet;

/**
 * Entity in the game
 * 
 * @author unkemptherald
 * @version 18.5.16
 */
public abstract class Entity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2314898847524274672L;

	protected static enum Direction {
		UP, RIGHT, DOWN, LEFT;
		public String toString() {
			if (this == UP)
				return "Back";
			else if (this == RIGHT)
				return "Right";
			else if (this == DOWN)
				return "Front";
			else // if (this == LEFT)
				return "Left";
		}
	};

	public static enum Kind {
		SKELETON, BULLET, NINJA, SHURIKEN;
		public String toString() {
			return super.toString().toLowerCase();
		}
	}

	protected int x, y;
	protected double velocityX, velocityY;
	protected Kind identifier;
	protected Direction facing;
	/**
	 * first index corresponds to type <br>
	 * second index is direction (if only one direction, assume 0)
	 */
	protected Animation[][] walking = new Animation[Kind.values().length][Direction.values().length];
	protected Animation[][] hurt = new Animation[Kind.values().length][Direction.values().length];

	public Entity(int x, int y, double velocityX, double velocityY, Kind identifier, Direction facing) {
		this.x = x;
		this.y = y;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.identifier = identifier;
		this.facing = facing;
	}

	// protected Entity() {
	// }

	public void setLocation(int x1, int y1) {
		x = x1;
		y = y1;
	}

	public void addToLocation(int x, int y) {
		this.x += x;
		this.y += y;
	}

	public Kind getKind() {
		return identifier;
	}

	public abstract void act(Map m, State s);

	public String toString() {
		return (x + " " + velocityX + " " + y + " " + velocityY + " " + identifier);
	}

	public void draw(PApplet applet) {
		if (walking[identifier.ordinal()][facing.ordinal()] == null) {
			String motion = "Walking";
			String folder = "assets/" + identifier + "/128/" + facing + " - " + motion + "/";
			File f = new File(folder);
			int frames = f.listFiles().length;
			String[] locations = new String[frames];
			DecimalFormat end = new DecimalFormat("000");
			for (int i = 0; i < frames; i++) {
				String num = end.format(i);
				locations[i] = folder + facing + " - " + motion + "_" + num + ".png";
			}
			walking[identifier.ordinal()][facing.ordinal()] = new Animation(locations, "png");
		}
		if (hurt[identifier.ordinal()][facing.ordinal()] == null) {
			String motion = "Hurt";
			String folder = "assets/" + identifier + "/128/" + facing + " - " + motion + "/";
			File f = new File(folder);
			int frames = f.listFiles().length;
			String[] locations = new String[frames];
			DecimalFormat end = new DecimalFormat("000");
			for (int i = 0; i < frames; i++) {
				String num = end.format(i);
				locations[i] = folder + facing + " - " + motion + "_" + num + ".png";
			}
			hurt[identifier.ordinal()][facing.ordinal()] = new Animation(locations, "png");
		}
	}

	public Kind getNext(Kind t) {
		return Kind.values()[(t.ordinal() + 1) % Kind.values().length];
	}
}