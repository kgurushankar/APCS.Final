package client;

import java.io.*;
import java.net.Socket;

import client.window.Game;
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
	private ObjectInputStream in;
	private BufferedWriter out;
	private boolean paused;
	private Game g;

	public ClientConnection(String address, int port) throws IOException {
		this.s = new Socket(address, port);
		in = new ObjectInputStream(s.getInputStream());
		out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
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
			State current = null;
			// should probably find a way to remove the cast
			while ((current = (State) in.readObject()) != null) {
				if (paused)
					continue;
				handleMessage(current);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Error during serialization, check your version");
			e.printStackTrace();
		}
	}

	public void close() throws IOException {
		s.close();
	}

	private Game initGame() throws ClassNotFoundException, IOException {
		Object o = in.readObject();
		Game game = (Game) o;
		return game;
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
