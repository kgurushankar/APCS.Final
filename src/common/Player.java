package common;

import java.util.Random;
import java.io.Serializable;
import client.window.Game;
import processing.core.PApplet;

public class Player extends Entity implements Serializable {
/**
 * The user's player
 * 
 * @author unkemptherald
 */
 * @version 18.5.10

	private static final long serialVersionUID = 145755016608084977L;
	private static final int MAX_LIVES = 5;
	private boolean dirChanged;
	private int lives;

	public Player(int x, int y, Kind identifier) {
		super(x, y, 0, 0, identifier, Direction.DOWN);
	public Player(int x, int y, Kind identifier,Direction d) {
		super(x, y, 0., 0., identifier,d);
		facing = Direction.DOWN;
		// TODO Auto-generated constructor stub
	}

	public void act(Map m, State s) {
		int dir = (int) (Math.random() * 4);
		if (dir < 2) {
			this.moveX(dir == 0, m);
		} else {
			this.moveY(dir % 2 == 0, m);
		}
		this.fire(m, s);
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public void moveX(boolean positive, Map m) {
		int newx = x + ((positive) ? 1 : -1) * Game.tileSize;
		if (m.canGo(newx, y)) {
			x = newx;
		}
		Direction newFacing = (positive) ? Direction.RIGHT : Direction.LEFT;
		if (newFacing != facing) {
			updateImage = true;
			facing = newFacing;
		}
	}

	public void moveY(boolean positive, Map m) {
		int newy = y + ((positive) ? 1 : -1) * Game.tileSize;
		if (m.canGo(x, newy)) {
			y = newy;
		}
		Direction newFacing = (positive) ? Direction.DOWN : Direction.UP;
		if (newFacing != facing) {
			updateImage = true;
			facing = newFacing;
		}
	}

	public void draw(PApplet applet) {
			super.draw(applet);// make sure animation has been loaded
		if (identifier == Kind.NINJA || identifier == Kind.SKELETON) {
			Animation current = walking[identifier.ordinal()][facing.ordinal()];
			if (dirChanged) {
				current.reset();
				dirChanged = false;
			}
			current.draw(applet, x, y, Game.tileSize, Game.tileSize);
		} else if (identifier == Kind.BULLET) {
		} else if (identifier == Kind.SHURIKEN) {
			applet.rect(x, y, 10, 10);
			applet.ellipse(x, y, 10, 10);
		}

		applet.image(image, x, y, Game.tileSize, Game.tileSize);

	}

	public String toString() {
		return x + "," + y;
	}

	public int getLives() {
		return lives;
	}

	public void hurt() {
		lives--;
	}
	// private void writeObject(ObjectOutputStream os) throws IOException {
	// os.writeInt(x);
	// os.writeInt(y);
	// os.writeInt(identifier.ordinal());
	// os.writeInt(facing.ordinal());
	// }
	//
	// private void readObject(ObjectInputStream is) throws IOException {
	// x = is.readInt();
	// y = is.readInt();
	// identifier = Kind.values()[is.readInt()];
	// facing = Direction.values()[is.readInt()];
	// }

}
