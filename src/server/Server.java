package server;

import java.io.*;
import java.net.*;
import java.rmi.ServerException;
import java.util.*;

import client.window.Game;
import common.State;

public class Server implements AutoCloseable {
	private volatile List<ServerConnection> connections;
	private volatile ServerSocket s;
	private volatile ServerState state;
	private volatile Queue<Message> queue;
	private volatile Config c;

	Server(int port) throws IOException {
		s = new ServerSocket(port);
		connections = new Vector<ServerConnection>();
		state = new ServerState(new Config("server.config"));
		System.out.println("ready");
		new Thread(new Accepter(), "Connections").start();
	}

	public Server() {
		try {
			c = new Config("server.config");
			s = new ServerSocket(c.port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connections = new Vector<ServerConnection>();
		state = new ServerState(c);
		System.out.println("ready");
		new Thread(new Accepter(), "Connections").start();
	}

	private class Accepter implements Runnable {
		public void run() {
			while (s.isBound() && !s.isClosed()) {
				try {
					ServerConnection c = new ServerConnection(s.accept()) {
						@Override
						public void handleMessage(String s) {
							queue.add(new Message(s, this));
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

	private class Message {
		private String s;
		private ServerConnection sender;

		public Message(String s, ServerConnection sender) {
			this.s = s;
			this.sender = sender;
		}

		public String getData() {
			return s;
		}

		public ServerConnection getSender() {
			return sender;
		}
	}

	@Override
	public void close() throws Exception {
		s.close();
		for (ServerConnection c : connections) {
			c.close();
		}
	}
}
