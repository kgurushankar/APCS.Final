package common;

public class projectile extends Entity{

	public projectile(int x, int y, double velocityX, double velocityY, byte identifier) {
		super(x, y, velocityX, velocityY, identifier);
	
	}

	public void act() {
		this.y+=this.velocityY;
		
	}

}
