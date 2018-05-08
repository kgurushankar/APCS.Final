package common;

public class Projectile extends Entity {

	public Projectile(int x, int y, double velocityX, double velocityY, byte identifier) {
		super(x, y, velocityX, velocityY, identifier);

	}

	public void act() {
		this.y += this.velocityY;
		this.x += this.velocityX;
	}

}
