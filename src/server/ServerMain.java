package server;

/**
 * Class that runs the entire server
 * 
 * @author kgurushankar
 * @version 18.5.21
 */
public class ServerMain {
	public static void main(String[] args) {
		@SuppressWarnings({ "unused", "resource" }) // I need this to stay open, and all processing happens
													// internally
		Server s = new Server();

	}
}
