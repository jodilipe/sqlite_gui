package dk.japps.sqlite.gui.view;

import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import dk.japps.sqlite.gui.*;
import dk.japps.sqlite.gui.logic.*;
import dk.japps.sqlite.gui.model.*;

public class MenuView implements View {

	@Override
	public JMenuBar build() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(createFileMenu());
		menuBar.add(createDatabaseMenu());
		return menuBar;
	}

	private JMenu createDatabaseMenu() {
		JMenu menu = new JMenu("Sql");
		menu.add(createMenuItem("Execute", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sql = SqliteGui.instance.getSql();
				Database selectedDatabase = SqliteGui.instance.getSelectedDatabase();
				if (sql.toLowerCase().startsWith("select")) {
					Table table = DatabaseLogic.instance.executeQuery(selectedDatabase, sql);
					SqliteGui.instance.setSelectedTable(table);
					selectedDatabase = DatabaseLogic.instance.openDatabase(selectedDatabase.getName());
					SqliteGui.instance.repaintMainFrame();
				} else {
					DatabaseLogic.instance.execute(selectedDatabase, sql);
				}
			}
		}));
		return menu;
	}

	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createMenuItem("New", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnVal = fileChooser.showSaveDialog(SqliteGui.instance.getMainFrame());
				fileChooser.setDialogTitle("Create new Sqlite Database");
				if (returnVal == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();
//          System.out.println(file.getAbsolutePath());
          SqliteGui.instance.addOpenDatabase(new DatabaseLogic().openDatabase(file.getAbsolutePath()));
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
          SqliteGui.instance.addOpenDatabase(new DatabaseLogic().openDatabase(file.getAbsolutePath()));
//          System.out.println(file.getAbsolutePath());
				}
			}
		}));
		return menu;
	}
	
	private JMenuItem createMenuItem(String name, ActionListener listener) {
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.addActionListener(listener);
		return menuItem;
	}
}
