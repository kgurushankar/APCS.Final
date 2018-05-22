package client.window;

import java.io.IOException;

import com.sun.glass.events.KeyEvent;

import client.ClientConnection;
import client.window.settings.Settings;
import common.State;
import common.Entity.Kind;
import common.Enemy;
import common.Entity;
import common.Player;
import common.Projectile;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Window that displays the game
 * 
 * @author kgurushankar
 * @version 18.5.10
 */
public class DrawingSurface extends PApplet {
	Game g;
	ClientConnection cc;
	private volatile State latest;
	boolean ready;
	private Settings.Data d;
	private boolean gameLost;

	public DrawingSurface() {
		if (d == null) {
			d = Settings.run();
		}
		if (d.singleplayer) {
			g = new Game((d.pirate) ? Kind.SKELETON : Kind.NINJA, d.mapSize);
			latest = g.state;
			ready = true;
			for (int i = 0; i < d.enemies; i++) {
				int x = (int) (d.mapSize * Math.random()) * Game.tileSize;
				int y = (int) (d.mapSize * Math.random()) * Game.tileSize;
				if (g.getMap().canGo(x, y)) {
					if (latest.me.getKind() == Kind.NINJA) {
						latest.items.add(new Enemy(x, y, Kind.SKELETON));
					} else {
						latest.items.add(new Enemy(x, y, Kind.NINJA));
					}
				} else {
					i--;
				}
			}
		} else {
			try {
				cc = new ClientConnection(d.ip, d.port, (d.pirate) ? Kind.SKELETON : Kind.NINJA) {
					@Override
					public void handleMessage(State s) {
						// g = new Game(g.getMap(), s);
						latest = s;
						ready = true;
					}
				};
				g = cc.getGame();
				new Thread(cc).start();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public void setData(Settings.Data d) {
		this.d = d;
	}

	private PImage heart;

	public void setup() {
		frameRate(30);
		heart = loadImage("assets/heart.png", "png");
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void draw() {
		if (gameLost) {
			lose();
		} else {
			mainScreen();
		}
	}

	public void keyPressed() {
		if (cc != null) {
			if (key == 'w' || key == 'W' || keyCode == KeyEvent.VK_UP) {
				cc.sendData("Mu");
			} else if (key == 'a' || key == 'A' || keyCode == KeyEvent.VK_LEFT) {
				cc.sendData("Ml");
			} else if (key == 's' || key == 'S' || keyCode == KeyEvent.VK_DOWN) {
				cc.sendData("Md");
			} else if (key == 'd' || key == 'D' || keyCode == KeyEvent.VK_RIGHT) {
				cc.sendData("Mr");
			}
		} else if (key == 'e') {
			int[] spawn = g.getMap().spawnPoint();
			g.state.items.add(new Player(spawn[0], spawn[1], Kind.SKELETON));
		}
		g.keyPressed(this);
	}

	public void mousePressed() {
		Projectile p = latest.me.fire(g.getMap());
		if (p != null) {
			latest.items.add(p);
		}
		if (cc != null) {
			cc.sendData("Af");
		}
	}

	public int countRemainingEnemies() {
		Kind target = (latest.me.getKind() == Kind.SKELETON) ? Kind.NINJA : Kind.SKELETON;
		int out = 0;
		for (int i = 0; i < latest.items.size(); i++) {
			if (latest.items.get(i).getKind() == target) {
				out++;
			}
		}
		return d.enemies - out;
	}

	private int kills;

	private void lose() {
		if (kills == 0) {
			kills = countRemainingEnemies();
		}
		background(0);
		fill(255);
		textSize(20);
		textAlign(PApplet.CENTER, PApplet.CENTER);
		text("You Lose \nEnemies Killed: " + kills, width / 2, height / 2);
	}

	private void mainScreen() {
		background(0);
		if (ready) {
			pushMatrix();

			float mx = -latest.me.getX() + width / 2;
			float my = -latest.me.getY() + height / 2;
			if (Game.cornerLock) {
				translate((mx >= 0) ? 0 : mx, (my >= 0) ? 0 : my);
			} else {
				translate(mx, my);
			}

			g.getMap().draw(this, 0, 0, Game.tileSize, Game.tileSize);
			for (int i = 0; i < latest.items.size(); i++) {
				Entity e = latest.items.get(i);
				e.draw(this);
				if (e.collide(latest.me)) {
					if ((e.getKind() == Kind.SHURIKEN && latest.me.getKind() == Kind.SKELETON)
							|| (e.getKind() == Kind.BULLET && latest.me.getKind() == Kind.NINJA)) {
						latest.me.hurt();
						if (latest.me.getLives() <= 0) {
							gameLost = true;
							if (cc != null) {
								try {
									cc.close();
								} catch (IOException e1) {

								}
							}
						}
					}
				}

				for (int j = 0; j < latest.items.size(); j++) {
					Entity e0 = latest.items.get(j);
					if (e.collide(e0)) {
						if ((e.getKind() == Kind.SHURIKEN && e0.getKind() == Kind.SKELETON)
								|| (e.getKind() == Kind.BULLET && e0.getKind() == Kind.NINJA)) {
							((Player) e0).hurt();
							System.out.println("ow");
							if (((Player) e0).getLives() <= 0) {
								latest.items.remove(e0);
							}
							latest.items.remove(e);
						}
					}
				}
				e.act(g.getMap(), latest);
			}
			latest.me.draw(this);

			popMatrix();
			for (int i = 0; i < latest.me.getLives(); i++) {
				image(heart, 8 + i * 20, 8, 16, 16);
			}
		}
	}
}
