package common;

import client.window.Game;
import processing.core.PApplet;

public class Player extends Entity {

	private boolean dirChanged;

	public Player(int x, int y, Kind identifier) {
		super(x, y, 0, 0, identifier, Direction.DOWN);
	}

	@Override
	public void act(Map m) {
		// TODO Auto-generated method stub
		int dir = (int) (Math.random() * 4);
		if (dir < 2) {
			this.moveX(dir == 0, m);
		} else {
			this.moveY(dir % 2 == 0, m);
		}
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
			dirChanged = true;
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
			dirChanged = true;
			facing = newFacing;
		}
	}

	public void fire(Map m, State s) {
		int upOrDown = 0;
		int leftOrRight = 0;
		if (facing == Direction.RIGHT)
			leftOrRight = 1;
		else if (facing == Direction.LEFT)
			leftOrRight = -1;
		else if (facing == Direction.UP)
			upOrDown = 1;
		else if (facing == Direction.DOWN)
			upOrDown = -1;
		if (m.canGo((x + leftOrRight), (y + upOrDown)))
			;
		s.getItems().add(new Projectile(x + leftOrRight * Game.tileSize, y + upOrDown * Game.tileSize,
				leftOrRight * Game.tileSize / 15, upOrDown * Game.tileSize / 15, identifier, facing));
	}

	public void draw(PApplet applet) {
		if (identifier == Kind.NINJA || identifier == Kind.SKELETON) {
			super.draw(applet);// make sure animation has been loaded
			Animation current = image[identifier.ordinal()][facing.ordinal()];
			if (dirChanged) {
				current.reset();
				dirChanged = false;
			}
			current.draw(applet, x, y, Game.tileSize, Game.tileSize);
		} else if (identifier == Kind.BULLET) {
			applet.rect(x, y, 10, 10);
		} else if (identifier == Kind.SHURIKEN) {
			applet.ellipse(x, y, 10, 10);

		}
	}
}
