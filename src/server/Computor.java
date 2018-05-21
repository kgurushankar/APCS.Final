package server;

import java.util.concurrent.*;

import common.Map;
import common.Player;

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
					}
					s.state.players.put(c, p);
				}
			}
		}
	}

	private class Act implements Runnable {

		@Override
		public void run() {
			s.updateAll();
			
			for(int i = 0; i<s.state.players.size();i++) 
			{
				for(int a = 0; a<s.state.items.size();a++) 
				{
					if(s.state.players.get(i).getX()==s.state.items.get(a).getX()) 
					{
						if(s.state.players.get(i).getY()==s.state.items.get(a).getY())
						{
							s.state.players.get(i).hurt();
							s.state.items.get(a).destroy();
						}
					}
				}
			}
		}
	}

}
