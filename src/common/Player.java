package common;

import client.window.Game;

public class Player extends Entity {

	public Player(int x, int y, byte identifier) {
		super(x, y, 0, 0, identifier);
		// TODO Auto-generated constructor stub
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
		if (m.canGo(newx / Game.tileSize, y / Game.tileSize)) {
			x = newx;
		}
	}

	public void moveY(boolean positive, Map m) {
		int newy = y + ((positive) ? 1 : -1) * Game.tileSize;
		if (m.canGo(x / Game.tileSize, newy / Game.tileSize)) {
			y = newy;
		}
	}

	public void fire(Map m, int dir) 
	{
		if (dir < 2) {
			
		} else {
			
		}
	}
}
