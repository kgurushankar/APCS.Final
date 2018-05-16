package server;

import java.io.*;
import java.net.*;
import java.rmi.ServerException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import client.window.Game;

public class Server implements AutoCloseable {
	private Computor c;
	private volatile List<ServerConnection> connections;
	private volatile ServerSocket s;
	private volatile ServerState state;
	private volatile Queue<Message> queue;
	private volatile Config cfg;

	Server(int port) throws IOException {
		s = new ServerSocket(port);
		connections = new Vector<ServerConnection>();
		state = new ServerState(new Config("server.config"));
		System.out.println("ready");
		new Thread(new Accepter(), "Connections").start();
	}

	public Server() {
		try {
			cfg = new Config("server.config");
			s = new ServerSocket(cfg.port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connections = new Vector<ServerConnection>();
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
					ServerConnection c = new ServerConnection(s.accept()) {
						@Override
						public void handleMessage(String s) {
							queue.add(new Message(s, this));
							System.out.println(Arrays.toString(queue.toArray()));
						}
					};
					Game send = state.addConnection(c);
					c.sendData(send);
					System.out.println("sent game");
					new Thread(c).start(); // fork
					connections.add(c);
					state.addConnection(c);
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
		for (ServerConnection c : connections) {
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
		for (ServerConnection c : connections) {
			if (c.isClosed()) {
				connections.remove(c);
			}
			c.sendData(state.generateState(c));
		}
	}
}
