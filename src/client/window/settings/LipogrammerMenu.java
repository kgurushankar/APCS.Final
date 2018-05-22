package client.window.settings;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuBar extends JMenuBar {
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
			
		}

		private void saveText() {
			
			lipogrammer.refresh();
			JFileChooser fileChooser = new JFileChooser(pathname);
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = fileChooser.showSaveDialog(lipogrammer);
			if (result == JFileChooser.CANCEL_OPTION)
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

				fileOut.print(lipogrammer.getText());
				fileOut.close();
			}
		}
	}
}