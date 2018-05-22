package server;

import java.io.*;
import java.net.*;
import java.rmi.ServerException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import client.window.Game;
import common.Entity;
import common.Entity.Kind;
import common.Sendable;

/**
 * Main class that the server runs off of
 * 
 * @author kgurushankar
 * @version 18.5.16
 */
public class Server implements AutoCloseable {
	private volatile Computor c;
	private volatile List<Server.Connection> connections;
	private volatile ServerSocket s;
	volatile ServerState state;
	private volatile Queue<Message> queue;
	private volatile Config cfg;

	Server(int port) throws IOException {
		s = new ServerSocket(port);
		connections = new Vector<Server.Connection>();
		state = new ServerState(new Config("server.config"));
		System.out.println("ready");
		new Thread(new Accepter(), "Connections").start();
	}

	public Server() {
		try {
			cfg = new Config("server.config");
			s = new ServerSocket(cfg.port);
		} catch (BindException be) { // Server cannot be created, so no use continuing program
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		connections = new Vector<Server.Connection>();
		state = new ServerState(cfg);
		queue = new ConcurrentLinkedQueue<Message>();
		System.out.println("ready");
		new Thread(new Accepter(), "Connections").start();
		c = new Computor(this);
	}

	private class Accepter implements Runnable {
		public void run() {
			while (s.isBound() && !s.isClosed()) {
				try {
					Server.Connection c = new Server.Connection(s.accept());
					Game send = state.addConnection(c, Kind.valueOf(c.readData()));
					c.sendData(send);
					new Thread(c).start(); // fork
					connections.add(c);
					c.sendData(send);
					Thread.yield(); // optional
				} catch (ServerException | SocketException e) { // if this ever runs, the server is busted
					e.printStackTrace();
					System.exit(1);
				} catch (IOException e) {
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
				System.out.println("kill " + this);
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
}
