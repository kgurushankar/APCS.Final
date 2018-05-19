package client.window;

import java.util.*;

import processing.core.PApplet;

public class Menu {
	private List<Button> b;

	public Menu() {
		b = new ArrayList<Button>();
	}

	public void draw(PApplet applet) {
		for (Button b : this.b) {
			b.draw(applet);
		}
	}

	public Button mouseMoved(PApplet applet) {
		for (Button b : this.b) {
			if (b.mouseMoved(applet)) {
				return b;
			}
		}
		return null;
	}

	public Button click(PApplet applet) {
		for (Button b : this.b) {
			if (b.click(applet)) {
				return b;
			}
		}
		return null;
	}
}
