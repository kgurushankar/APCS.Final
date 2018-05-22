package client;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Vector;

import client.window.Game;
import common.Entity;
import common.Entity.Kind;
import common.Map;
import common.Player;
import common.State;

/**
 * Client's connection to the server, intended to send strings and receive state
 * objects
 * 
 * @author kgurushankar
 * @version 18.5.12
 */
public abstract class ClientConnection implements AutoCloseable, Runnable {
	private Socket s;
	private BufferedReader in;
	private BufferedWriter out;
	private Game g;

	public ClientConnection(String ip, int port, Kind identifier) throws IOException {
		this.s = new Socket(ip, port);
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
		sendData(identifier.toString());
	}

	public void sendData(String s) {
		try {
			out.write(s);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract void handleMessage(State s);

	public String toString() {
		return s.toString();
	}

	public void run() {
		try {
			String current = null;
			// should probably find a way to remove the cast
			while ((current = in.readLine()) != null) {
				State d = new State(in.readLine());
				handleMessage(d);
				Thread.yield();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() throws IOException {
		s.close();
	}

	private Game initGame() throws ClassNotFoundException, IOException {
		String s = in.readLine();
		return new Game(s);
	}

	public Game getGame() throws ClassNotFoundException, IOException {
		if (g != null)
			return g;
		else {
			g = initGame();
			return g;
		}
	}

}
