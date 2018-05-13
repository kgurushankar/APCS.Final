package server;

import java.io.*;

/**
 * 
 * @author kgurushankar
 * @version 18.5.12
 */
public class Config {
	// am i better off using a hash map??
	public int maxPlayers;
	public int mapSize;
	public int port;
	public String serverName;
	// private String gameMode;

	public Config(String filename) throws FileNotFoundException {
		this(filename, true);
	}

	private Config(String filename, boolean fixIfBadFormat) throws FileNotFoundException {
		File cfg = new File(filename);
		BufferedReader f = new BufferedReader(new FileReader(cfg));
		String line = null;
		try {
			while ((line = f.readLine()) != null) {
				if (line.startsWith("#")) {
					continue;
				} else {
					String search = null;
					if (line.startsWith(search = "max-players")) {
						String val = getValue(line, search);
						this.maxPlayers = Integer.parseInt(val);
					} else if (line.startsWith(search = "map-size")) {
						String val = getValue(line, search);
						this.mapSize = Integer.parseInt(val);
					} else if (line.startsWith(search = "port")) {
						String val = getValue(line, search);
						this.port = Integer.parseInt(val);
					} else if (line.startsWith(search = "server-name")) {
						this.serverName = getValue(line, search);
						// } else if (line.startsWith(search = "gamemode")) {
						// this.gameMode = getValue(line, search);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!allfieldsPopulated()) {
			if (fixIfBadFormat) { // something bad in formatting
				String newData = "#Server configuration file\nmax-players=20\nmap-size=100\nport=8888\nserver-name=Server";
				try (BufferedWriter fo = new BufferedWriter(new FileWriter(cfg));) {
					fo.write(newData);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			maxPlayers = (maxPlayers == 0) ? 20 : maxPlayers;
			mapSize = (mapSize == 0) ? 100 : mapSize;
			port = (port == 0) ? 8888 : port;
			serverName = (serverName == null) ? "Server" : serverName;
		}
	}

	private String getValue(String line, String search) {
		int loc = line.indexOf(search) + search.length();
		String end = line.substring(loc).trim();
		end = (end.charAt(0) == '=') ? end.substring(1).trim() : end; // trim off '='
		return end;
	}

	private boolean allfieldsPopulated() {
		return maxPlayers != 0 && mapSize != 0 && port != 0 && serverName != null;
	}

	public String toString() {
		return "maxPlayers: " + this.maxPlayers + "\nmapSize: " + this.mapSize + "\nport: " + this.port + "\nname: "
				+ this.serverName;
	}

	public static void main(String[] args) {
		try {
			System.out.println(new Config("server.config"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
