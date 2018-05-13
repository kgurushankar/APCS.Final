package client.window;

import java.io.IOException;

import client.ClientConnection;
import common.State;

public class TestConnection {
	public static void main(String[] args) {
		Game g;
		try {
			ClientConnection cc = new ClientConnection("localhost", 8888) {
				@Override
				public void handleMessage(State s) {
				}
			};
			g = cc.getGame();
			System.out.println(g.getState().getMe());
			new Thread(cc).start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
