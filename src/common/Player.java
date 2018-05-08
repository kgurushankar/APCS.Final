package common;

import client.window.Game;

public class Player extends Entity {

	public Player(int x, int y, byte identifier) {
		super(x, y, 0, 0, identifier);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub

	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public void moveX(boolean positive, Map m) {
		x += ((positive) ? 1 : -1) * Game.tileSize;
		if (!m.canGo(x / Game.tileSize, y / Game.tileSize)) {
			x += ((!positive) ? 1 : -1) * Game.tileSize;
		}
	}

	public void moveY(boolean positive, Map m) {
		y += ((positive) ? 1 : -1) * Game.tileSize;
		if (!m.canGo(x / Game.tileSize, y / Game.tileSize)) {
			y += ((!positive) ? 1 : -1) * Game.tileSize;
		}
	}

}
