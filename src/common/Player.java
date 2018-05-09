package common;

import client.window.Game;

public class Player extends Entity {
		byte facing;
	public Player(int x, int y, byte identifier) {
		super(x, y, 0, 0, identifier);
		facing = 0;
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

	public void fire(Map m, State s) 
	{
		int upOrDown = 0;
		int leftOrRight = 0;
		if(facing%2 == 1) 
		{
			if(facing == 3) 
			{
				leftOrRight = 1;
			}
			else 
			{
				leftOrRight = -1;
			}
		}
		else 
		{
			if(facing == 2)
			{upOrDown = 1;}
			else {upOrDown = -1;}
		}
		if(m.canGo((x+leftOrRight)*Game.tileSize,(y+upOrDown)*Game.tileSize));
			s.getItems().add(new Projectile(x+leftOrRight*Game.tileSize,y+upOrDown*Game.tileSize,leftOrRight*Game.tileSize/15,upOrDown*Game.tileSize/15, identifier));
	}
}
