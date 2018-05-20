package server;

/**
 * Message wrapper for the server
 * 
 * @author kgurushankar
 * @version 18.5.16
 */
public class Message {
	private String s;
	private Server.Connection sender;

	public Message(String s, Server.Connection sender) {
		this.s = s;
		this.sender = sender;
	}

	public String getData() {
		return s;
	}

	public Server.Connection getSender() {
		return sender;
	}

	public String toString() {
		return s;
	}
}