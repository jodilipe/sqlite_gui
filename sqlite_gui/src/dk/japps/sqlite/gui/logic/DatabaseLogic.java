package dk.japps.sqlite.gui.logic;

import dk.japps.sqlite.gui.model.*;

public class DatabaseLogic {
	public Database openDatabase(String path) {
		Database database = new Database("test bd");
		database.addTable(new Table("table 1"));
		database.addTable(new Table("table 2"));
		database.addTable(new Table("table 3"));
		return database;
	}

	public Table getTableContent(String databaseName, String tableName) {
		if (databaseName != null && tableName != null) {
			Table table = new Table("test table");
			table.addRow("col 1", "col 2", "col 3", "col 4", "col 5");
			table.addRow("val 1", "val 2", "val 3", "val 4", "val 5");
			return table;
		}
		return null;
	}
}
