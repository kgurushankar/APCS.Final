package server;

import java.util.concurrent.*;

import common.Map;
import common.Player;
import common.Projectile;

/**
 * Computes the changes made to the game and sends back states periodically
 * 
 * @author kgurushankar
 * @version 18.5.16
 */
public class Computor {
	public static final int FPS = 30;

	private Server s;

	public Computor(Server s) {
		this.s = s;
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(new Act(), 0, 1000 / FPS, TimeUnit.MILLISECONDS);
		new Thread(new QueueControl()).start();
	}

	private class QueueControl implements Runnable {
		@Override
		public void run() {
			while (true) {
				Message curr = s.getQueue().poll();
				if (curr != null) {
					String d = curr.getData();
					ServerConnection c = curr.getSender();
					Player p = s.getState().players.get(c);
					System.out.println(p);
					Map m = s.getState().map;
					if (d.startsWith("M")) {
						char dir = d.charAt(1);
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
						char action = d.charAt(1);
						switch (action) {
						case 'f':
							Projectile i = p.fire(m);
							if (i != null)
								s.getState().items.add(i);
						}
					}
				}
			}
		}
	}

	private class Act implements Runnable {

		@Override
		public void run() {
			s.getState().updateEntities();
			s.updateAll();
			System.out.println(s.getState());
		}
	}

}
