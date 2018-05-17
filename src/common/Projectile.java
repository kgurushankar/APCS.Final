package common;

/**
 * Projectiles (fired stuff)
 * 
 * @author kgurushankar
 * @version 18.5.10
 */
public class Projectile extends Entity {

	public Projectile(int x, int y, double velocityX, double velocityY, Kind identifier,Direction d) {
		super(x, y, velocityX, velocityY, identifier,d);

	}

	public void act(Map m,State s) {
		int velY = (int) velocityY;
		int velX = (int) velocityX;
		if(velY>0) 
		{
			for (int i = 0; i < velY; i++) {
				if (m.canGo(x, y + 1)) {
					y++;
				} else {
					break;
				}
			}
		}
		else if(velY<0) 
		{
			for (int i = 0; i > velY; i--) {
				if (m.canGo(x, y - 1)) {
					y--;
				} else {
					break;
				}
			}
		}
		else if(velX>0) 
		{
			for (int i = 0; i < velX; i++) {
				if (m.canGo(x+1, y)) {
					x++;
				} else {
					break;
				}
			}
		}
		else 
		{
			for (int i = 0; i > velX; i--) {
				if (m.canGo(x-1, y)) {
					x--;
				} else {
					break;
				}
			}
		}
//		String map = m.toString();
		
		
		
	}
}
