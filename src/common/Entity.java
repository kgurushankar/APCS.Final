package common;

import processing.core.PApplet;
import processing.core.PImage;

public abstract class Entity {

	protected int x, y;
	protected double velocityX, velocityY;
	protected byte identifier;
	protected PImage image;

	public Entity(int x, int y, double velocityX, double velocityY, byte identifier) {
		this.x = x;
		this.y = y;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.identifier = identifier;
	}

	public void setLocation(int x1, int y1) {
		x = x1;
		y = y1;
	}

	public void addToLocation(int x, int y) {
		this.x += x;
		this.y += y;
	}

	public abstract void act();

	public String toString() {
		return (x + " " + velocityX + " " + y + " " + velocityY + " " + identifier);
	}

	public void draw(PApplet applet) {
		if (image == null) {
			if (identifier == 1) {// pirate
				applet.loadImage("assets/skeleton/128/Front - Idle/Front - Idle_000.png");
			} else if (identifier == 2) {// bullet
				applet.ellipse(x, y, 10, 10);
			} else if (identifier == 3) {// ninja
				applet.loadImage("assets/ninja/128/Front - Idle/Front - Idle_000.png");
			} else if (identifier == 4) {// shuriken
				applet.rect(x, y, 10, 10);
			}
		} else {
			applet.image(image, x, y);
		}
	}
}
