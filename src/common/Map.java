package common;

import java.awt.Color;
import java.io.Serializable;

import client.window.Game;
import processing.core.PApplet;
import processing.core.PImage;

public class Map implements Serializable {
	private static final long serialVersionUID = 3637177447616075769L;
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
	 * need to reoptimize this
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
	 *            The pixel width of each cell
	 * @param height
	 *            The pixel height of each cell
	 */
	public void draw(PApplet applet, float x, float y, float width, float height) {
		PImage floor = applet.loadImage("assets/floor.png");
		float w = width;
		float h = height;
		int i = 0;
		int k = 0;
		// while (x < 0) {
		// k++;
		// x += w;
		// }
		// while (y < 0) {
		// i++;
		// y += w;
		// }
		float px = x;
		float py = y;
		while (i < state.length) {// && py < applet.height) {
			int j = k;
			while (j < state[i].length) {// && px < applet.width) {
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

	/**
	 * 
	 * @param x
	 *            the x-coordinate of the pixel on the grid (pre translations and
	 *            whatnot)
	 * @param y
	 *            the y-coordinate of the pixel on the grid (pre translations and
	 *            whatnot)
	 * @return if the pixel can be moved to
	 */
	public boolean canGo(int x, int y) {
		x /= Game.tileSize;
		y /= Game.tileSize;
		return y >= 0 && y < state.length && x >= 0 && x < state[y].length && !state[y][x];
	}

	public int[] spawnPoint() {
		int[] out = new int[2];
		do {
			out[0] = (int) (state.length * Math.random());
			out[1] = (int) (state[out[0]].length * Math.random());
		} while (!canGo(out[0] * Game.tileSize, out[1] * Game.tileSize));
		return out;
	}

	public int getSize() {
		return state.length;
	}
}
