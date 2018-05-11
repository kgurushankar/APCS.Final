package common;

public class Projectile extends Entity {
	private boolean exists;

	public Projectile(int x, int y, double velocityX, double velocityY, Kind identifier, Direction direction) {
		super(x, y, velocityX, velocityY, identifier, direction);
		exists = true;

	}

	public void act(Map m) {
		int velY = (int) velocityY;
		int velX = (int) velocityX;
		for (int i = 0; i < velY; i++) {
			if (m.canGo(x, y + 1)) {
				y++;
			} else {
				exists = false;
				return;
			}
			// vel -= Game.tileSize;

		}
		for (int i = 0; i < velX; i++) {
			if (m.canGo(x + 1, y)) {
				y++;
			} else {
				break;
			}
		}
		for (int i = 0; i > velY; i++) {
			if (m.canGo(x, y + 1)) {
				y++;
			} else {
				break;
			}
		}
		for (int i = 0; i > velX; i++) {
			if (m.canGo(x + 1, y)) {
				y++;
			} else {
				break;
			}
		}

	}

	public boolean destroy() {
		return !exists;
	}
}
