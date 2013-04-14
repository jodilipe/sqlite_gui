package dk.japps.sqlite.gui.view;

import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import dk.japps.sqlite.gui.*;

public class MenuView implements View {

	@Override
	public JMenuBar build() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		menu.add(createMenuItem("New", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnVal = fileChooser.showSaveDialog(SqliteGui.instance.getMainFrame());
				fileChooser.setDialogTitle("Create new Sqlite Database");
				if (returnVal == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();
          System.out.println(file.getAbsolutePath());
				}
			}
		}));
		menu.add(createMenuItem("Open", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnVal = fileChooser.showOpenDialog(SqliteGui.instance.getMainFrame());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();
          System.out.println(file.getAbsolutePath());
				}
			}
		}));
		return menuBar;
	}
	
	private JMenuItem createMenuItem(String name, ActionListener listener) {
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.addActionListener(listener);
		return menuItem;
	}
}
