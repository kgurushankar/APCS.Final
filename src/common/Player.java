package common;

import client.window.Game;
import processing.core.PApplet;

public class Player extends Entity {
	String imageLoc = "assets/skeleton/128/Front - Idle/Front - Idle_000.png";
	private boolean updateImage;

	public Player(int x, int y, byte identifier) {
		super(x, y, 0, 0, identifier);
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
		String newImageLoc = (positive) ? "assets/skeleton/128/Right - Idle/Right - Idle_000.png"
				: "assets/skeleton/128/Left - Idle/Left - Idle_000.png";
		if (!newImageLoc.equals(imageLoc)) {
			updateImage = true;
			imageLoc = newImageLoc;
		}
	}

	public void moveY(boolean positive, Map m) {
		int newy = y + ((positive) ? 1 : -1) * Game.tileSize;
		if (m.canGo(x / Game.tileSize, newy / Game.tileSize)) {
			y = newy;
		}
		String newImageLoc = (positive) ? "assets/skeleton/128/Front - Idle/Front - Idle_000.png"
				: "assets/skeleton/128/Back - Idle/Back - Idle_000.png";
		if (!newImageLoc.equals(imageLoc)) {
			updateImage = true;
			imageLoc = newImageLoc;
		}
	}

	public void fire(Map m, int dir) {
		if (dir < 2) {

		} else {

		}
	}

	public void draw(PApplet applet) {
		if (image == null || updateImage) {
			if (identifier == 1) {// pirate
				image = applet.loadImage(imageLoc, "png");

			} else if (identifier == 2) {// bullet
				applet.ellipse(x, y, 10, 10);
			} else if (identifier == 3) {// ninja
				image = applet.loadImage("assets/ninja/128/Front - Idle/Front - Idle_000.png", "png");
			} else if (identifier == 4) {// shuriken
				applet.rect(x, y, 10, 10);
			}
			updateImage = false;
		} else {
			applet.image(image, x, y, Game.tileSize, Game.tileSize);
		}
	}

}
