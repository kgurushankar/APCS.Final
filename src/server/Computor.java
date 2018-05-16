package server;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.*;

import common.Entity;
import common.Map;
import common.Player;

public class Computor implements Runnable {
	public static final int FPS = 30;

	private Server s;

	public Computor(Server s) {
		this.s = s;
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(new Act(), 0, 1000 / FPS, TimeUnit.MILLISECONDS);
		new Thread(new QueueControl()).start();
	}

	@Override
	public void run() {
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
					}
				}
			}
		}
	}

	private class Act implements Runnable {

		@Override
		public void run() {
			s.updateAll();
			System.out.println(s.getState());
		}
	}

}
