package dk.japps.sqlite.gui;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import dk.japps.sqlite.gui.model.*;
import dk.japps.sqlite.gui.view.*;

public class SqliteGui {
	private List<Database> openDatabases;
	private Database selectedDatabase;
	private Table selectedTable;
	private JPanel databaseTreesPanel;
	private JPanel selectedTablePanel;
	private JTextArea sqlTextArea;
	private JTextArea logTextArea;
	private JFrame mainFrame;
	private int mainFrameWidth = 800;
	private int mainFrameHeight = (mainFrameWidth / 16) * 9;
	public static final SqliteGui instance = new SqliteGui();
	
	public SqliteGui() {
		mainFrame = new JFrame("Sqlite GUI");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setJMenuBar(new MenuView().build());
		mainFrame.setSize(mainFrameWidth, mainFrameHeight);
		mainFrame.setBackground(Color.WHITE);
		
		databaseTreesPanel = new JPanel();
		databaseTreesPanel.setLayout(new BoxLayout(databaseTreesPanel, BoxLayout.Y_AXIS));
//		databaseTreesPanel.setBorder(BorderFactory.createLineBorder(Color.blue));
		selectedTablePanel = new JPanel();
//		selectedTablePanel.setBorder(BorderFactory.createLineBorder(Color.red));
		sqlTextArea = new JTextArea();
		logTextArea = new JTextArea();

		JSplitPane tableAndLogSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(selectedTablePanel), new JScrollPane(logTextArea));
		tableAndLogSplitPane.setDividerLocation(mainFrameHeight / 3);
		JSplitPane verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(sqlTextArea), tableAndLogSplitPane);
		verticalSplitPane.setDividerLocation(mainFrameHeight / 3);
		JSplitPane horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(databaseTreesPanel), verticalSplitPane);
		horizontalSplitPane.setDividerLocation(mainFrameWidth / 3);
		mainFrame.getContentPane().add(horizontalSplitPane);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SqliteGui.instance.repaintMainFrame();
			}
		});
		SqliteGui.instance.buildDatabaseTrees();
	}
	
	public void buildSelectedTable() {
		selectedTablePanel.removeAll();
		if (selectedDatabase != null && selectedTable != null) {
			JTable table = new TableContentView(selectedTable).build();
			table.setAlignmentY(Component.TOP_ALIGNMENT);
			selectedTablePanel.add(table);
		}
		repaintMainFrame();
	}

	public void buildDatabaseTrees() {
		databaseTreesPanel.removeAll();
//		databaseTreesPanel.setBorder(BorderFactory.createLineBorder(Color.red));
		for (Database openDatabase : getOpenDatabases()) {
			JTree databaseTree = new TableListView(openDatabase).build();
			databaseTree.addTreeSelectionListener(new TreeSelectionListener() {
				@Override
				public void valueChanged(TreeSelectionEvent event) {
					setSelectedDatabase(getDatabase(event.getPath()));
					JTree selectedTree = (JTree) event.getSource();
					for (Component currentTree : databaseTreesPanel.getComponents()) {
						if (!currentTree.equals(selectedTree)) {
							((JTree) currentTree).clearSelection();
						}
					}
					if (tableSelected(event.getPath())) {
						setSelectedTable(selectedDatabase.getTable(event.getPath().getPathComponent(1).toString()));
					} else {
						setSelectedTable(null);
					}
					buildSelectedTable();
				}
				
				private Database getDatabase(TreePath path) {
					for (Database database : getOpenDatabases()) {
						if (database.getName().equals(path.getPathComponent(0).toString())) {
							return database;
						}
					}
					return null;
				}
				
				private boolean tableSelected(TreePath path) {
					return path.getPathCount() > 1;
				}
			});
			databaseTree.setAlignmentX(Component.LEFT_ALIGNMENT);
			databaseTreesPanel.add(databaseTree);
		}
		repaintMainFrame();
	}
	
	public String getSql() {
		if (sqlTextArea.getSelectedText() != null) {
			return sqlTextArea.getSelectedText();
		}
		return sqlTextArea.getText();
	}

	public Table getSelectedTable() {
		return selectedTable;
	}

	public void setSelectedTable(Table selectedTable) {
		this.selectedTable = selectedTable;
	}

	public Database getSelectedDatabase() {
		return selectedDatabase;
	}

	public void setSelectedDatabase(Database selectedDatabase) {
		this.selectedDatabase = selectedDatabase;
	}
	
	public void addOpenDatabase(Database openDatabase) {
		getOpenDatabases().add(openDatabase);
		setSelectedDatabase(openDatabase);
		buildDatabaseTrees();
		buildSelectedTable();
	}
	
	public void removeOpenDatabase(String openDatabase) {
		getOpenDatabases().remove(openDatabase);
	}

	public List<Database> getOpenDatabases() {
		if (openDatabases == null) {
			openDatabases = new ArrayList<Database>();
		}
		return openDatabases;
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}
	
	public void repaintMainFrame() {
//		mainFrame.pack();
		mainFrame.setVisible(true);
	}
}
