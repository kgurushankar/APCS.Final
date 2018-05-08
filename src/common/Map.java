package common;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PImage;

public class Map {
	/** isWall */
	private boolean[][] state;

	public Map(boolean[][] state) {
		this.state = state;
	}

	public String toString() {
		StringBuffer s = new StringBuffer();
		for (boolean[] i : state) {
			for (boolean j : i) {
				s.append((j) ? 1 : 0);
				s.append(' ');
			}
			s.append('\n');
		}
		return s.toString();
	}

	/**
	 * Renders a preview of the map (in black and white tiles, rather than using the
	 * images)
	 *
	 * @param applet
	 *            The PApplet used for drawing.
	 * @param x
	 *            The x pixel coordinate of the upper left corner of the grid
	 *            drawing.
	 * @param y
	 *            The y pixel coordinate of the upper left corner of the grid
	 *            drawing.
	 * @param width
	 *            The pixel width of the grid drawing.
	 * @param height
	 *            The pixel height of the grid drawing.
	 */
	public void renderPreview(PApplet applet, float x, float y, float width, float height) {
		float w = width / state[0].length;
		float h = height / state.length;
		int i = 0;
		int k = 0;
		while (x < 0) {
			k++;
			x += w;
		}
		while (y < 0) {
			i++;
			y += w;
		}
		float px = x;
		float py = y;
		while (i < state.length && py < applet.width) {
			int j = k;
			while (j < state[i].length && px < applet.width) {
				if (!state[i][j]) {
					applet.fill(Color.WHITE.getRGB());
					applet.rect(px, py, w, h);
				} else {
					applet.fill(Color.BLACK.getRGB());
					applet.rect(px, py, w, h);
				}
				px += w;
				j++;
			}
			py += h;
			px = x;
			i++;
		}
	}

	/**
	 * Draws the map using black tiles for empty space and the image for legal
	 * walking area
	 *
	 * @param applet
	 *            The PApplet used for drawing.
	 * @param x
	 *            The x pixel coordinate of the upper left corner of the grid
	 *            drawing.
	 * @param y
	 *            The y pixel coordinate of the upper left corner of the grid
	 *            drawing.
	 * @param width
	 *            The pixel width of the grid drawing.
	 * @param height
	 *            The pixel height of the grid drawing.
	 */
	public void draw(PApplet applet, float x, float y, float width, float height) {
		PImage floor = applet.loadImage("assets/floor.png");
		float w = width / state[0].length;
		float h = height / state.length;
		int i = 0;
		int k = 0;
		while (x < 0) {
			k++;
			x += w;
		}
		while (y < 0) {
			i++;
			y += w;
		}
		float px = x;
		float py = y;
		while (i < state.length && py < applet.width) {
			int j = k;
			while (j < state[i].length && px < applet.width) {
				if (!state[i][j]) {
					applet.image(floor, px, py, w, h);
				} else {
					applet.fill(Color.BLACK.getRGB());
					applet.rect(px, py, w, h);
				}
				px += w;
				j++;
			}
			py += h;
			px = x;
			i++;
		}
	}

	public boolean canGo(int x, int y) {
		return state[y][x];
	}

	public int[] spawnPoint() {
		int[] out = new int[2];
		do {
			out[0] = (int) (state.length * Math.random());
			out[1] = (int) (state[out[0]].length * Math.random());
		} while (!canGo(out[0], out[1]));
		return out;
	}
}
