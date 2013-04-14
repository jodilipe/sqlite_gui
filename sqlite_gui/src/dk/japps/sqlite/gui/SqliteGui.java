package dk.japps.sqlite.gui;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import dk.japps.sqlite.gui.view.*;

public class SqliteGui {
	private List<String> openDatabases;
	private String selectedDatabaseName;
	private String selectedTableName;
	private JPanel selectedTablePanel;
	private JFrame mainFrame;
	public static final SqliteGui instance = new SqliteGui();

	public static void main(String[] args) {
		SqliteGui.instance.addOpenDatabase("database 1");
		SqliteGui.instance.addOpenDatabase("database 2");
		SqliteGui.instance.addOpenDatabase("database 3");
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SqliteGui.instance.setMainFrame(new JFrame("Sqlite GUI"));
				SqliteGui.instance.getMainFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				SqliteGui.instance.getMainFrame().setJMenuBar(new MenuView().build());
				SqliteGui.instance.selectedTablePanel = new JPanel();

				JPanel contentPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
				contentPane.add(buildDatabaseTrees());
				contentPane.add(SqliteGui.instance.selectedTablePanel);
				SqliteGui.instance.getMainFrame().getContentPane().add(new JScrollPane(contentPane), BorderLayout.CENTER);

				SqliteGui.instance.getMainFrame().pack();
				// frame.setResizable(false);
				SqliteGui.instance.getMainFrame().setVisible(true);
			}

			private void buildSelectedTable() {
				SqliteGui.instance.selectedTablePanel.removeAll();
				if (SqliteGui.instance.selectedDatabaseName != null && SqliteGui.instance.selectedTableName != null) {
					SqliteGui.instance.selectedTablePanel.add(new TableContentView(SqliteGui.instance.selectedDatabaseName, SqliteGui.instance.selectedTableName).build());
					SqliteGui.instance.selectedTablePanel.validate();
				}
				SqliteGui.instance.getMainFrame().pack();
				SqliteGui.instance.getMainFrame().repaint();
			}

			private JPanel buildDatabaseTrees() {
				JPanel panel = new JPanel();
				panel.setBorder(BorderFactory.createLineBorder(Color.red));
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				for (String openDatabase : SqliteGui.instance.getOpenDatabases()) {
					JTree tableListView = new TableListView(openDatabase).build();
					tableListView.addTreeSelectionListener(new TreeSelectionListener() {
						@Override
						public void valueChanged(TreeSelectionEvent event) {
							SqliteGui.instance.setSelectedDatabaseName(getDatabaseName(event.getPath()));
							if (tableSelected(event.getPath())) {
								SqliteGui.instance.setSelectedTableName(getTableName(event.getPath()));
							} else {
								SqliteGui.instance.setSelectedTableName(null);
							}
							buildSelectedTable();
						}
						
						private String getTableName(TreePath path) {
							return path.getPathComponent(1).toString();
						}
						
						private String getDatabaseName(TreePath path) {
							return path.getPathComponent(0).toString();
						}
						
						private boolean tableSelected(TreePath path) {
							return path.getPathCount() > 1;
						}
					});
					tableListView.setAlignmentX(Component.LEFT_ALIGNMENT);
					panel.add(tableListView);
				}
				return panel;
			}
		});
	}

	public String getSelectedTableName() {
		return selectedTableName;
	}

	public void setSelectedTableName(String selectedTableName) {
		this.selectedTableName = selectedTableName;
		System.out.println(selectedTableName);
	}

	public String getSelectedDatabaseName() {
		return selectedDatabaseName;
	}

	public void setSelectedDatabaseName(String selectedDatabaseName) {
		this.selectedDatabaseName = selectedDatabaseName;
		System.out.println(selectedDatabaseName);
	}
	
	public void addOpenDatabase(String openDatabase) {
		getOpenDatabases().add(openDatabase);
	}

	public List<String> getOpenDatabases() {
		if (openDatabases == null) {
			openDatabases = new ArrayList<String>();
		}
		return openDatabases;
	}

	public void setOpenDatabases(List<String> openDatabases) {
		this.openDatabases = openDatabases;
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
}
