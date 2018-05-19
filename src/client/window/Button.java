package client.window;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import processing.core.PApplet;

public class Button {
	private float x, y, width, height;
	private Color background, border, text, hover, click, current;
	private String word;

	public Button(float x, float y, float width, float height, String word) {
		this(x, y, width, height, Color.WHITE, Color.BLACK, Color.BLUE, Color.LIGHT_GRAY, Color.GRAY, word);
	}

	public Button(float x, float y, float width, float height, Color background, Color border, Color text, Color hover,
			Color click, String word) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.background = background;
		this.border = border;
		this.text = text;
		this.hover = hover;
		this.click = click;
		this.word = word;
		this.current = this.background;
	}

	public void draw(PApplet applet) {
		applet.stroke(border.getRGB());
		applet.fill(current.getRGB());
		applet.rect(x, y, width, height);

		applet.fill(text.getRGB());
		float h = 24f;
		applet.textSize(h);
		float w = applet.textWidth(word);
		applet.textAlign(PApplet.LEFT, PApplet.TOP);
		if (width - w >= 0) {
			float d = (width - w) / 2 + x;
			applet.text(word, d, y + height / 2 - h / 2);
		} else {
			int lines = PApplet.ceil(w / width);
			if (height >= h * lines) {
				for (int i = 0; i < lines; i++) {
					float d = (width - w) / 2 + x;
					float b = y + height / 2 - (lines - lines / 2) * h + ((lines % 2 == 0) ? 0 : -h / 2);
					applet.text(word, d, b);
				}
			} else {
				applet.textAlign(PApplet.CENTER, PApplet.CENTER);
				applet.text(word, x, y + height / 2 - h / 2);
			}
		}
	}

	public boolean inside(float x, float y) {
		return new Rectangle2D.Float(x, y, width, height).contains(x, y);
	}

	public boolean mouseMoved(PApplet applet) {
		if (inside(applet.mouseX, applet.mouseY)) {
			current = hover;
			return true;
		} else {
			current = background;
			return false;
		}
	}

	public boolean click(PApplet applet) {
		if (inside(applet.mouseX, applet.mouseY)) {
			current = click;
			return true;
		} else {
			current = background;
			return false;
		}
	}

	public String toString() {
		return word;
	}
}
