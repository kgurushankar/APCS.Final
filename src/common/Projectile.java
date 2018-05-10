package common;

/**
 * Projectiles (fired stuff)
 * 
 * @author kgurushankar
 * @version 18.5.10
 */
public class Projectile extends Entity {

	public Projectile(int x, int y, double velocityX, double velocityY, byte identifier) {
		super(x, y, velocityX, velocityY, identifier);

	}

	public void act(Map m) {
		int vel = (int) velocityY;
//		String map = m.toString();
		for (int i = 0; i < vel; i++) {
			if (m.canGo(x, y + 1)) {
				y++;
			} else {
				break;
			}
		}
	}
}
