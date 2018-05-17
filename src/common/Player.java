package common;

import java.util.Random;

import client.window.Game;
import processing.core.PApplet;

/**
 * The user's player
 * 
 * @author unkemptherald
 * @version 18.5.10
 */
public class Player extends Entity {
	
	
	private Direction facing;// make this an enum??
	private boolean updateImage;

	public Player(int x, int y, Kind identifier,Direction d) {
		super(x, y, 0., 0., identifier,d);
		facing = Direction.DOWN;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void act(Map m,State s) {
		
		this.facing=Direction.values()[(int)(Math.random()*4)];
		updateImage = true;
		if(facing == Direction.DOWN) 
		{
			moveY(true,m);
		}
		else if(facing == Direction.LEFT) 
		{
			moveX(false,m);
		}
		else if(facing == Direction.UP) 
		{
			moveY(false,m);
		}
		else if(facing == Direction.RIGHT) 
		{
			moveX(true,m);
		}
		this.fire(m, s);
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
		Direction newFacing = (positive) ? Direction.RIGHT : Direction.LEFT;
		if (newFacing != facing) {
			updateImage = true;
			facing = newFacing;
		}
	}

	public void moveY(boolean positive, Map m) {
		int newy = y + ((positive) ? 1 : -1) * Game.tileSize;
		if (m.canGo(x / Game.tileSize, newy / Game.tileSize)) {
			y = newy;
		}
		Direction newFacing = (positive) ? Direction.DOWN : Direction.UP;
		if (newFacing != facing) {
			updateImage = true;
			facing = newFacing;
		}
	}

	public void fire(Map m, State s) {
		int upOrDown = 0;
		int leftOrRight = 0;
		if (facing == Direction.RIGHT)
			leftOrRight = 1;
		else if (facing == Direction.LEFT)
			leftOrRight = -1;
		else if (facing == Direction.UP)
			upOrDown = -1;
		else if (facing == Direction.DOWN)
			upOrDown = 1;
		if (m.canGo((x + leftOrRight) * Game.tileSize, (y + upOrDown) * Game.tileSize))
			;
		s.getItems().add(new Projectile(x + leftOrRight * Game.tileSize, y + upOrDown * Game.tileSize,
				leftOrRight * Game.tileSize / 15, upOrDown * Game.tileSize / 15, Kind.SHURIKEN,Direction.DOWN));
	}

	public void draw(PApplet applet) {
		if (image == null || updateImage) {
			String imageLoc = "assets/";

			String suffix = "";
			if (facing == Direction.RIGHT)
				suffix = "Right";
			else if (facing == Direction.LEFT)
				suffix = "Left";
			else if (facing == Direction.UP)
				suffix = "Back";
			else if (facing == Direction.DOWN)
				suffix = "Front";

			if (identifier == Kind.SKELETON) {// pirate
				imageLoc += "skeleton/128/";
				image = applet.loadImage(imageLoc + suffix + " - Idle/" + suffix + " - Idle_000.png", "png");

			} else if (identifier == 2) {// bullet
				applet.ellipse(x, y, 10, 10);
			} else if (identifier == 3) {// ninja
				imageLoc += "ninja/128/";
				image = applet.loadImage(imageLoc + suffix + " - Idle/" + suffix + " - Idle_000.png", "png");
			} else if (identifier == 4) {// shuriken
				applet.rect(x, y, 10, 10);
			}
			updateImage = false;
		}

		applet.image(image, x, y, Game.tileSize, Game.tileSize);

	}
}
