package client.window;

import java.io.IOException;

import client.ClientConnection;
import common.Entity.Kind;
import common.State;

public class TestConnection {
	public static void main(String[] args) {
		Game g;
		try {
			ClientConnection cc = new ClientConnection("localhost", 8888,
					(Math.random() > 0.5) ? Kind.NINJA : Kind.SKELETON) {
				@Override
				public void handleMessage(State s) {
				}
			};
			g = cc.getGame();
			System.out.println(g.state.me);
			new Thread(cc).start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
