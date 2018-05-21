package common;

import client.window.Game;

/***
 * Projectile that can be fired
 * 
 * @author unkemptherald
 * @version 18.5.16
 */
public class Projectile extends Entity {

	public Projectile(int x, int y, double velocityX, double velocityY, Kind identifier, Direction direction) {
		super(x, y, velocityX, velocityY, identifier, direction);
		exists = true;

	}

	public Projectile(String parseable) {
		super(Integer.parseInt(parseable.split(" ")[1]), Integer.parseInt(parseable.split(" ")[2]), 0, 0,
				Kind.values()[Integer.parseInt("" + parseable.split(" ")[3].charAt(0))],
				Direction.values()[Integer.parseInt("" + parseable.split(" ")[3].charAt(1))]);
		if (!parseable.startsWith("Projectile")) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public String toParseableString() {
		StringBuffer sb = new StringBuffer("Projectile ");
		sb.append(x + " ");
		sb.append(y + " ");
		sb.append(identifier.ordinal());
		sb.append(facing.ordinal());
		return sb.toString();
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
		}
		if (velY < 0) {
			for (int i = 0; i > velY; i--) {
				if (m.canGo(x, y - Game.tileSize)) {
					y -= Game.tileSize;
				} else {
					exists = false;
					return;
				}
			}
		} else if (velX < 0) {
			for (int i = 0; i > velX; i--) {
				if (m.canGo(x + Game.tileSize, y)) {
					x -= Game.tileSize;
				} else {
					exists = false;
					return;
				}
			}
		}

		else {
			for (int i = 0; i < velX; i++) {
				if (m.canGo(x + Game.tileSize, y)) {
					x += Game.tileSize;
				} else {
					exists = false;
					return;
				}
			}
		}
	}

}
