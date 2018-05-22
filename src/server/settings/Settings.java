package server.settings;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Settings Dialog Box
 * 
 * @author kgurushankar
 * @version 18.5.21
 */
public class Settings extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1046195028760178727L;
	private static boolean selecting;
	private JTextField name;
	private JLabel Players, Mapsize, Port, Name, Pirates, Ninjas;
	private JSpinner players, mapsize, port, pirates, ninjas;
	private JButton start;
	Data d;

	/**
	 * Settings Data
	 * 
	 * @author kgurushankar
	 * @version 18.5.21
	 */
	public static class Data {
		public int maxPlayers;
		public int mapSize;
		public int port;
		public String serverName;
		public int pirates;
		public int ninjas;

		public boolean valid() {
			return maxPlayers != 0 && mapSize != 0 && port != 0 && serverName != null;
		}
	}

	// Constructor
	public Settings() {
		super("Settings");
		d = new Data();
		selecting = true;
		setJMenuBar(new MenuBar(this));
		setupGui();

	}

	// Called when the Refresh button is clicked
	public void actionPerformed(ActionEvent e) {
		refresh();
		if (e.getSource() == start) {
			selecting = false;
		}
	}

	private void refresh() {
		d.maxPlayers = (int) players.getValue();
		d.port = (int) port.getValue();
		d.mapSize = (int) mapsize.getValue();
		d.pirates = (int) pirates.getValue();
		d.ninjas = (int) ninjas.getValue();

	}

	// ********************** GUI setup ********************************

	private void setupGui() {

		port = new JSpinner();
		port.setValue(8888);
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(port, "#");
		port.setEditor(editor);

		mapsize = new JSpinner();
		mapsize.setValue(100);

		players = new JSpinner();
		players.setValue(20);

		name = new JTextField("Server", 20);

		pirates = new JSpinner();
		pirates.setValue(0);
		pirates.setEnabled(false);

		ninjas = new JSpinner();
		ninjas.setValue(0);
		ninjas.setEnabled(false);

		start = new JButton("START");
		start.addActionListener(this);

		Players = new JLabel("Max Players");
		Name = new JLabel("Server Name");
		Port = new JLabel("Port");
		Mapsize = new JLabel("Map Size");
		Pirates = new JLabel("Enemy Pirates");
		Ninjas = new JLabel("Enemy Ninjas");

		Box b[] = new Box[7];
		b[0] = Box.createHorizontalBox();
		b[0].add(Name);
		b[0].add(name);
		b[1] = Box.createHorizontalBox();
		b[1].add(Players);
		b[1].add(players);
		b[2] = Box.createHorizontalBox();
		b[2].add(Port);
		b[2].add(port);
		b[3] = Box.createHorizontalBox();
		b[3].add(Mapsize);
		b[3].add(mapsize);
		b[4] = Box.createHorizontalBox();
		b[4].add(Pirates);
		b[4].add(pirates);
		b[5] = Box.createHorizontalBox();
		b[5].add(Ninjas);
		b[5].add(ninjas);
		b[6] = Box.createHorizontalBox();
		b[6].add(start);

		Box b0 = Box.createVerticalBox();
		for (Box a : b) {
			b0.add(a);
		}

		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		c.add(b0);
	}

	public static void main(String[] args) {
		run();
	}

	public static Data run() {
		Settings window = new Settings();
		window.setBounds(100, 100, 360, 240);
		window.setDefaultCloseOperation(EXIT_ON_CLOSE);
		window.setVisible(true);
		while (selecting) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		window.setVisible(false);
		Data d = window.d;
		window = null;
		return d;
	}
}