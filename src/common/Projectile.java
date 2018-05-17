package common;

import client.window.Game;

/***
 * Projectile that can be fired
 * 
 * @author unkemptherald
 * @version 18.5.16
 */
public class Projectile extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8778019732879813118L;
	private boolean exists;

	public Projectile(int x, int y, double velocityX, double velocityY, Kind identifier, Direction direction) {
		super(x, y, velocityX, velocityY, identifier, direction);
		exists = true;

	}

	public void act(Map m, State s) {
		int velY = (int) velocityY;
		int velX = (int) velocityX;
		for (int i = 0; i < velY; i++) {
			if (m.canGo(x, y + Game.tileSize)) {
				y += Game.tileSize;
			} else {
				exists = false;
				return;
			}
			// vel -= Game.tileSize;

		}
		for (int i = 0; i < velY; i++) {
			if (m.canGo(x, y + Game.tileSize)) {
				y += Game.tileSize;
			} else {
				exists = false;
				return;
			}
		}
		for (int i = 0; i > velY; i++) {
			if (m.canGo(x, y - Game.tileSize)) {
				y -= Game.tileSize;
			} else {
				exists = false;
				return;
			}
		}
		for (int i = 0; i > velX; i++) {
			if (m.canGo(x + Game.tileSize, y)) {
				x += Game.tileSize;
			} else {
				exists = false;
				return;
			}
		}

	}

	public boolean destroy() {
		return !exists;
	}

}
