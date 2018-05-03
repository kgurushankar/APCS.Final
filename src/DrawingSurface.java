
import processing.core.PApplet;
import processing.event.MouseEvent;

public class DrawingSurface extends PApplet {

	private Map board;
	private int length = 500;

	public DrawingSurface() {
		board = MapGenerator.generateMap(500);
	}

	// The statements in the setup() function
	// execute once when the program begins
	public void setup() {
		// size(0,0,PApplet.P3D);
	}

	// The statements in draw() are executed until the
	// program is stopped. Each statement is executed in
	// sequence and after the last line is read, the first
	// line is executed again.
	public void draw() {
		background(255); // Clear the screen with a white background
		fill(0);
		textAlign(LEFT);
		textSize(12);

		if (board != null) {
			board.renderPreview(this, 0, 0, length, length);
		}

	}

	public void mouseWheel(MouseEvent event) {
		int num = event.getCount();
		length -= num * 10;
	}
	/*
	 * public void mousePressed() { if (mouseButton == LEFT) { Point click = new
	 * Point(mouseX, mouseY); float dimension = height; Point cellCoord =
	 * board.clickToIndex(click, 0, 0, dimension, dimension); if (cellCoord != null)
	 * { board.toggleCell(cellCoord.x, cellCoord.y); prevToggle = cellCoord; } } }
	 * 
	 * public void mouseDragged() { if (mouseButton == LEFT) { Point click = new
	 * Point(mouseX, mouseY); float dimension = height; Point cellCoord =
	 * board.clickToIndex(click, 0, 0, dimension, dimension); if (cellCoord != null
	 * && !cellCoord.equals(prevToggle)) { board.toggleCell(cellCoord.x,
	 * cellCoord.y); prevToggle = cellCoord; } } }
	 * 
	 * public void keyPressed() { if (keyCode == KeyEvent.VK_SPACE) { if (runCount
	 * >= 0) runCount = -1; else runCount = 0; } else if (keyCode ==
	 * KeyEvent.VK_DOWN) { speed = Math.min(MAX_SPEED, speed * 2); } else if
	 * (keyCode == KeyEvent.VK_UP) { speed = Math.max(MIN_SPEED, speed / 2);
	 * runCount = Math.min(runCount, speed); } else if (keyCode ==
	 * KeyEvent.VK_ENTER) { board.step(); } else if (keyCode == KeyEvent.VK_C) {
	 * board = new Life(); }
	 * 
	 * }
	 */
}