import java.awt.Color;

import processing.core.PApplet;

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
	 * Renders a preview of the map
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

		float px = x;
		float py = y;
		float ix = width / state.length;
		float iy;
		for (int i = 0; i < state.length; i++) {
			iy = height / state[i].length;
			for (int j = 0; j < state[i].length; j++) {
				if (!state[i][j]) {
					applet.fill(Color.WHITE.getRGB());
				} else {
					applet.fill(Color.BLACK.getRGB());

				}
				applet.rect(px, py, ix, iy);
				px += ix;
			}
			py += iy;
			px = x;
		}
	}

	// use some kind of renderdistance variable to find optimal size of maps
	public Map getSection(int x, int y, int width, int height) {
		boolean[][] n = new boolean[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				n[i][j] = state[x + i][y + j];
			}
		}
		return new Map(n);
	}

}
