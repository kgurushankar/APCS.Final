package common;

import java.io.File;
import java.text.DecimalFormat;

import client.window.Game;
import common.Entity.Type;
import processing.core.PApplet;
import processing.core.PImage;

public abstract class Entity {
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

	public enum Kind {
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
	protected Animation[][] image = new Animation[Kind.values().length][Direction.values().length];

	public Entity(int x, int y, double velocityX, double velocityY, Kind identifier, Direction facing) {
		this.x = x;
		this.y = y;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.identifier = identifier;
		this.facing = facing;
	}

	public void setLocation(int x1, int y1) {
		x = x1;
		y = y1;
	}

	public void addToLocation(int x, int y) {
		this.x += x;
		this.y += y;
	}

	public abstract void act(Map m, State s);

	public String toString() {
		return (x + " " + velocityX + " " + y + " " + velocityY + " " + identifier);
	}

	public void draw(PApplet applet) {
		if (image[identifier.ordinal()][facing.ordinal()] == null) {
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
			image[identifier.ordinal()][facing.ordinal()] = new Animation(locations, "png");
		}
	}
	
	public Type getNext(Type t) 
	{
		if (identifier == Type.PIRATE) {// pirate
			return Type.BULLET;
		} else if (identifier == Type.BULLET) {// bullet
			return Type.NINJA;
		} else if (identifier == Type.NINJA) {// ninja
			return Type.SHURIKEN;
		} else  {// shuriken
			return Type.PIRATE;
		}
	}
}
