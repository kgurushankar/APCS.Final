package server.settings;

import java.awt.event.*;
import javax.swing.*;

import common.FileIO;

import java.io.*;
import java.util.ArrayList;

/**
 * Menu Bar for Server Settings
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

			Settings.Data d = new Settings.Data();
			BufferedReader f;

			String line = null;
			try {
				f = new BufferedReader(new FileReader(file));
				while ((line = f.readLine()) != null) {
					if (line.startsWith("#")) {
						continue;
					} else {
						String search = null;
						if (line.startsWith(search = "max-players")) {
							String val = getValue(line, search);
							d.maxPlayers = Integer.parseInt(val);
						} else if (line.startsWith(search = "map-size")) {
							String val = getValue(line, search);
							d.mapSize = Integer.parseInt(val);
						} else if (line.startsWith(search = "port")) {
							String val = getValue(line, search);
							d.port = Integer.parseInt(val);
						} else if (line.startsWith(search = "server-name")) {
							d.serverName = getValue(line, search);
							// } else if (line.startsWith(search = "gamemode")) {
							// this.gameMode = getValue(line, search);
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (d.valid()) {
				s.d = d;
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
				fileOut.println("map-size=" + (s.d.mapSize));
				fileOut.println("max-players=" + (s.d.maxPlayers));
				fileOut.println("port=" + s.d.port);
				fileOut.println("server-name" + s.d.serverName);
				fileOut.close();
			}
		}
	}
}