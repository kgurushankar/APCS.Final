package common;

public class projectile extends Entity{

	public projectile(int x, int y, double velocityX, double velocityY, byte identifier) {
		super(x, y, velocityX, velocityY, identifier);
	
	}

	public void act(Map m) {
		int vel = (int)velocityY;
		String map = m.toString();
		for(int i = 0; i<vel; i++)
		{
			if(map.can) 
			{
				y++;
			}
			else {
				break;
				}
		}
		
	}

}
