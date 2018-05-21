package client.window;

import java.io.IOException;

import com.sun.glass.events.KeyEvent;

import client.ClientConnection;
import client.window.settings.Settings;
import common.State;
import common.Entity.Kind;
import common.Entity;
import common.Player;
import common.Projectile;
import processing.core.PApplet;

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

	public DrawingSurface() {
		if (d == null) {
			d = Settings.run();
		}
		if (d.singleplayer) {
			g = new Game((d.pirate) ? Kind.SKELETON : Kind.NINJA);
			latest = g.state;
			ready = true;
			for(int i = 0; i<d.enemies; i++) {
			int x = (int)(g.mapSize*Math.random())*g.tileSize;
			int y = (int)(g.mapSize*Math.random())*g.tileSize;
			if(g.getMap().canGo(x, y)) 
			{
				if(g.state.me.getKind() == Kind.NINJA) 
				{
					g.state.items.add(new Player(x,y,Kind.SKELETON));
					
					
				}
				else 
				{
					g.state.items.add(new Player(x,y,Kind.NINJA));
				}
				
				
			}
			else 
			{
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

	public void setup() {
		frameRate(30);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void draw() {
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
			
			for (int i = 0; i<latest.items.size(); i++) {
				Entity e = latest.items.get(i);
				e.draw(this);
				
				e.act(g.getMap(), latest);
			}
			latest.me.draw(this);

			popMatrix();
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
}
