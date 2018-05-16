package server;

import java.io.*;
import java.net.*;

public abstract class ServerConnection implements AutoCloseable, Runnable {
	private Socket s;
	private BufferedReader in;
	private ObjectOutputStream out;

	public ServerConnection(Socket s) throws IOException {
		this.s = s;
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new ObjectOutputStream(s.getOutputStream());
	}

	public void sendData(Object send) {
		try {
			out.writeObject(send);
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

	public abstract void handleMessage(String s);

	public String toString() {
		return s.toString();
	}

	public void run() {
		try {
			String current = null;
			// should probably find a way to remove the cast
			while ((current = in.readLine()) != null && !isClosed()) {
				handleMessage(current);
			}
		} catch (IOException e) {
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