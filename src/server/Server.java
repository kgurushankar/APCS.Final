package server;

import java.io.*;
import java.net.*;
import java.rmi.ServerException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import client.window.Game;
import server.settings.Settings;
import server.settings.Settings.Data;
import common.Entity.Kind;
import common.Enemy;
import common.Sendable;

/**
 * Main class that the server runs off of
 * 
 * @author kgurushankar
 * @version 18.5.16
 */
public class Server implements AutoCloseable {
	private volatile List<Server.Connection> connections;
	private volatile ServerSocket s;
	volatile ServerState state;
	private volatile Queue<Message> queue;
	private volatile Data d;

	public Server() {
		try {
			d = Settings.run();
			s = new ServerSocket(d.port);
		} catch (BindException be) { // Server cannot be created, so no use continuing program
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		connections = new Vector<Server.Connection>();
		state = new ServerState(d);
		queue = new ConcurrentLinkedQueue<Message>();
		
		new Thread(new Accepter(), "Connections").start();
		new Computor(this);
	}

	private class Accepter implements Runnable {
		public void run() {
			while (s.isBound() && !s.isClosed()) {
				try {
					while (connections.size() >= d.maxPlayers) { // dont allow more than max players
						Thread.sleep(100);
					}
					Server.Connection c = new Server.Connection(s.accept());
					Game send = state.addConnection(c, Kind.valueOf(c.readData().toUpperCase()));
					for (int i = 0; i < d.pirates; i++) {
						int x = (int) (d.mapSize * Math.random()) * Game.tileSize;
						int y = (int) (d.mapSize * Math.random()) * Game.tileSize;
						if (state.map.canGo(x, y)) {
							state.items.add(new Enemy(x, y, Kind.SKELETON));
						} else {
							i--;
						}
						
					}
					for (int i = 0; i < d.ninjas; i++) {
						int x = (int) (d.mapSize * Math.random()) * Game.tileSize;
						int y = (int) (d.mapSize * Math.random()) * Game.tileSize;
						if (state.map.canGo(x, y)) {
							state.items.add(new Enemy(x, y, Kind.NINJA));
						} else {
							i--;
						}
						
					}
					c.sendData(send);
					new Thread(c).start(); // fork
					connections.add(c);
					Thread.yield(); // optional
				} catch (ServerException | SocketException e) { // if this ever runs, the server is busted
					e.printStackTrace();
					System.exit(1);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void close() throws Exception {
		s.close();
		for (Server.Connection c : connections) {
			c.close();
		}
	}

	ServerState getState() {
		return state;
	}

	Queue<Message> getQueue() {
		return queue;
	}

	public void updateAll() {
		for (Server.Connection c : connections) {
			if (c.isClosed()) {
				connections.remove(c);
				state.removeConnection(c);
			}
			common.State s = state.generateState(c);
			c.sendData(s);
		}
	}

	/**
	 * Socket connection for the server
	 * 
	 * @author kgurushankar
	 * @version 18.5.16
	 */
	public class Connection implements AutoCloseable, Runnable {
		private Socket s;
		private BufferedReader in;
		private BufferedWriter out;

		public Connection(Socket s) throws IOException {
			this.s = s;
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
		}

		public void sendData(Sendable send) {
			try {
				out.write(send.toParseableString());
				out.newLine();
				out.flush();
			} catch (SocketException e) {
				try {
					this.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void handleMessage(String s) {
			queue.add(new Message(s, this));
		}

		public String toString() {
			return s.toString();
		}

		String readData() throws IOException {
			return in.readLine();
		}

		public void run() {
			try {
				String current = null;
				// should probably find a way to remove the cast
				while ((current = in.readLine()) != null && !isClosed()) {
					handleMessage(current);
					Thread.yield();
				}
			} catch (Exception e) {
				
				try {
					connections.remove(this);
					state.removeConnection(this);
				} catch (Exception e0) {
				}

				e.printStackTrace();
			}
		}

		public void close() throws IOException {
			s.close();
		}

		public boolean isClosed() {
			return s.isClosed();
		}

	}

	public void removeConnection(Connection c) {
		connections.remove(c);
	}
}
