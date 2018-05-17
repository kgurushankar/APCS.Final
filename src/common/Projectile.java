package common;

import client.window.Game;

public class Projectile extends Entity {
	private boolean exists;
	
	public Projectile(int x, int y, double velocityX, double velocityY, Kind identifier, Direction direction) {
		super(x, y, velocityX, velocityY, identifier, direction);
		exists = true;

	}

	public void act(Map m, State s) {
		int velY = (int) velocityY;
		int velX = (int) velocityX;
		
	
		if(velY>0) {
		for (int i = 0; i < velY; i++) {
			if (m.canGo(x, y+Game.tileSize)) {
				y+= Game.tileSize;
			} else {
				exists = false;
				return;
			}
		}
		}
		else if(velY<0) {
		for (int i = 0; i > velY; i--) {
			if (m.canGo(x, y -Game.tileSize)) {
				y-=Game.tileSize;
			} else {
				exists = false;
				return;
			}
		}
		}
		else if(velX<0) {
		for (int i = 0; i > velX; i--) {
			if (m.canGo(x +Game.tileSize, y)) {
				x-=Game.tileSize;
			} else {
				exists = false;
				return;
			}
		}
		}
		else {
			for (int i = 0; i > velX; i++) {
				if (m.canGo(x +Game.tileSize, y)) {
					x+=Game.tileSize;
				} else {
					exists = false;
					return;
				}
			}
			}
	}

	public boolean destroy() {
		return !exists;
	}

	
}
