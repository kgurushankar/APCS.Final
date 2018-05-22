package client.window.settings;

import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import common.FileIO;

/**
 * Menu Bar for Client Settings
 * 
 * @author kgurushankar
 * @version 18.5.21
 */
public class MenuBar extends JMenuBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3797198984720998233L;
	private Settings s;
	private JMenuItem openItem, saveItem, exitItem;

	// Constructor
	public MenuBar(Settings s) {
		this.s = s;
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic((int) 'f');

		FileAction fileAction = new FileAction();
		openItem = new JMenuItem("Open...");
		openItem.setMnemonic((int) 'o');
		openItem.addActionListener(fileAction);
		saveItem = new JMenuItem("Save...");
		saveItem.setMnemonic((int) 's');
		saveItem.addActionListener(fileAction);
		exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic((int) 'x');
		exitItem.addActionListener(fileAction);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		add(fileMenu);
	}

	/******************************************************************/
	/*************** Action Listener for the Menu ****************/
	/******************************************************************/

	private class FileAction implements ActionListener {
		private String pathname = System.getProperty("user.dir") + "/";

		public void actionPerformed(ActionEvent e) {
			JMenuItem m = (JMenuItem) e.getSource();
			if (m == openItem) {
				loadText();
			} else if (m == saveItem) {
				saveText();
			} else if (m == exitItem) {
				System.exit(0);
			}
		}

		private void loadText() {
			JFileChooser fileChooser = new JFileChooser(pathname);
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = fileChooser.showOpenDialog(s);
			if (result == JFileChooser.CANCEL_OPTION || result == JFileChooser.ERROR_OPTION)
				return;

			File file = fileChooser.getSelectedFile();
			if (file == null)
				return;

			pathname = file.getAbsolutePath();
			ArrayList<String> lines = FileIO.readFile(pathname);
			for (int i = 0; i < lines.size(); i++) {
				if (lines.get(i).startsWith("#") || lines.get(i).replaceAll(" ", "").isEmpty()) {
					lines.remove(i);
					i--;
				}
			}
			Settings.Data d = new Settings.Data();
			for (String line : lines) {
				line = line.trim();
				String search = null;
				if (line.startsWith(search = "mode")) {
					String val = getValue(line, search);
					if (val.equals("singleplayer")) {
						d.singleplayer = true;
					} else if (val.equals("multiplayer")) {
						d.singleplayer = false;
					} else {
						throw new IllegalArgumentException(val);
					}
				} else if (line.startsWith(search = "ip")) {
					String val = getValue(line, search);
					d.ip = val;
				} else if (line.startsWith(search = "port")) {
					String val = getValue(line, search);
					d.port = Integer.parseInt(val);
				} else if (line.startsWith(search = "mapsize")) {
					String val = getValue(line, search);
					d.mapSize = Integer.parseInt(val);
				} else if (line.startsWith(search = "enemies")) {
					String val = getValue(line, search);
					d.enemies = Integer.parseInt(val);
				} else if (line.startsWith(search = "team")) {
					String val = getValue(line, search);
					if (val.equals("pirate")) {
						d.singleplayer = true;
					} else if (val.equals("ninja")) {
						d.singleplayer = false;
					} else {
						throw new IllegalArgumentException(val);
					}
				}
			}
			if (d.valid()) {
				s.d = d;
				s.refresh();
			} else {
				throw new IllegalArgumentException("Incomplete Config File");
			}

		}

		private String getValue(String line, String search) {
			int loc = line.indexOf(search) + search.length();
			String end = line.substring(loc).trim();
			end = (end.charAt(0) == '=') ? end.substring(1).trim() : end; // trim off '='
			return end;
		}

		private void saveText() {

			JFileChooser fileChooser = new JFileChooser(pathname);
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = fileChooser.showSaveDialog(s);
			if (result == JFileChooser.CANCEL_OPTION || result == JFileChooser.ERROR_OPTION)
				return;

			File file = fileChooser.getSelectedFile();
			if (file != null) {
				pathname = file.getAbsolutePath();
				PrintWriter fileOut;
				try {
					fileOut = new PrintWriter(new FileWriter(file));
				} catch (IOException ex) {
					System.out.println("*** Can't create file ***");
					return;
				}
				if (s.d.singleplayer) {
					fileOut.println();
					fileOut.println("mode=singleplayer");
					fileOut.println("map-size=" + (s.d.mapSize));
					fileOut.println("enemies=" + (s.d.enemies));
					fileOut.println("team=" + ((s.d.pirate) ? "pirate" : "ninja"));
				} else {
					fileOut.println("#Client config file");
					fileOut.println("mode=multiplayer");
					fileOut.println("ip=" + (s.d.ip));
					fileOut.println("port=" + (s.d.port));
					fileOut.println("team=" + ((s.d.pirate) ? "pirate" : "ninja"));
				}
				fileOut.close();
			}
			s.refresh();
		}
	}
}