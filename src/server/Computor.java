package server;

import java.io.IOException;
import java.util.concurrent.*;

import common.Entity;
import common.Entity.Kind;
import common.Map;
import common.Player;
import common.Projectile;
import server.Server.Connection;

/**
 * Computes the changes made to the game and sends back states periodically
 * 
 * @author kgurushankar
 * @version 18.5.16
 */
public class Computor {
	public static final int FPS = 30;

	private volatile Server s;

	public Computor(Server s) {
		this.s = s;
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(new Act(), 0, 1000000000 / FPS, TimeUnit.NANOSECONDS);
		new Thread(new QueueControl()).start();
	}

	private class QueueControl implements Runnable {
		@Override
		public void run() {
			while (true) {
				Message curr = s.getQueue().poll();
				if (curr != null) {
					String d = curr.getData();
					Server.Connection c = curr.getSender();
					Player p = s.state.players.get(c);
					if (d.startsWith("M")) {
						char dir = d.charAt(1);
						Map m = s.getState().map;
						switch (dir) {
						case 'u':
							p.moveY(false, m);
							break;
						case 'l':
							p.moveX(false, m);
							break;
						case 'd':
							p.moveY(true, m);
							break;
						case 'r':
							p.moveX(true, m);
							break;
						}
					} else if (d.startsWith("A")) {
						char dir = d.charAt(1);
						Map m = s.getState().map;
						switch (dir) {
						case 'f':
							Projectile pr = p.fire(m);
							if (pr != null) {
								s.state.items.add(pr);
							}
						}
					}
				}
			}
		}
	}

	private class Act implements Runnable {

		@Override
		public void run() {
			s.updateAll();

			for (int i = 0; i < s.state.items.size(); i++) {
				Entity e = s.state.items.get(i);
				e.act(s.state.map, null);
				if (e.destroy()) {
					s.state.items.remove(i);
					i--;
					continue;
				}
				for (Connection c : s.state.players.keySet()) {
					Player p = s.state.players.get(c);
					if (e.collide(p)) {
						if ((e.getKind() == Kind.SHURIKEN && p.getKind() == Kind.SKELETON)
								|| (e.getKind() == Kind.BULLET && p.getKind() == Kind.NINJA)) {
							p.hurt();
							if (p.getLives() <= 0) {
								try {
									c.close();
									s.state.removeConnection(c);
									s.removeConnection(c);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
						s.state.items.remove(i);
					}
				}
			}
		}
	}

}
