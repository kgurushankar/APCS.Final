package client.window.settings;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Client Settings Dialog Box
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
	private JTextField ip;
	private JLabel Mode, Ip, Port, Mapsize, Enemies, Team;
	private JSpinner port, mapsize, enemies;
	private JButton start;
	private JComboBox<String> team, mode;
	Data d;

	/**
	 * Client Settings Data
	 * 
	 * @author kgurushankar
	 * @version 18.5.21
	 */
	public static class Data {
		public boolean singleplayer;
		public boolean pirate;
		public String ip;
		public int port;
		public int mapSize;
		public int enemies;

		public boolean valid() {
			if (singleplayer) {
				return mapSize > 0 && enemies > 0;
			} else {
				return (ip != null || !ip.isEmpty()) && port != 0;
			}
		}
	}

	// Constructor
	public Settings() {
		super("Settings");
		d = new Data();
		selecting = true;
		setJMenuBar(new MenuBar(this));
		setupGui();

		refresh();
	}

	public void refresh() {
		if ("Singleplayer".equals(mode.getSelectedItem())) {
			port.setEnabled(false);
			ip.setEnabled(false);
			mapsize.setEnabled(true);
			enemies.setEnabled(true);
			mode.setEnabled(true);
			team.setEnabled(true);
		} else {
			port.setEnabled(true);
			ip.setEnabled(true);
			mapsize.setEnabled(false);
			enemies.setEnabled(false);
			mode.setEnabled(true);
			team.setEnabled(true);
		}
		d.singleplayer = "Singleplayer".equals(mode.getSelectedItem());
		d.ip = ip.getText();
		d.port = (int) port.getValue();
		d.mapSize = (int) mapsize.getValue();
		d.enemies = (int) enemies.getValue();
		d.pirate = "Pirate".equals(team.getSelectedItem());
	}

	// Called when the Refresh button is clicked
	public void actionPerformed(ActionEvent e) {
		refresh();
		if (e.getSource() == start) {
			selecting = false;
		}
	}

	// ********************** GUI setup ********************************

	private void setupGui() {

		port = new JSpinner();
		port.setValue(4444);
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(port, "#");
		port.setEditor(editor);

		mapsize = new JSpinner();
		mapsize.setValue(100);

		enemies = new JSpinner();
		enemies.setValue(20);

		ip = new JTextField();
		ip.setText("localhost");

		mode = new JComboBox<String>(new String[] { "Singleplayer", "Multiplayer" });
		mode.addActionListener(this);

		team = new JComboBox<String>(new String[] { "Ninja", "Pirate" });
		team.addActionListener(this);

		start = new JButton("START");
		start.addActionListener(this);

		Mode = new JLabel("Mode");
		Ip = new JLabel("IP");
		Port = new JLabel("Port");
		Mapsize = new JLabel("Map Size");
		Enemies = new JLabel("Enemies");
		Team = new JLabel("Team");

		Box b[] = new Box[7];
		b[0] = Box.createHorizontalBox();
		b[0].add(Mode);
		b[0].add(mode);
		b[1] = Box.createHorizontalBox();
		b[1].add(Ip);
		b[1].add(ip);
		b[2] = Box.createHorizontalBox();
		b[2].add(Port);
		b[2].add(port);
		b[3] = Box.createHorizontalBox();
		b[3].add(Mapsize);
		b[3].add(mapsize);
		b[4] = Box.createHorizontalBox();
		b[4].add(Enemies);
		b[4].add(enemies);
		b[5] = Box.createHorizontalBox();
		b[5].add(Team);
		b[5].add(team);
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

	public static Data run() {
		Settings window = new Settings();
		window.setBounds(100, 100, 240, 240);
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