package dk.japps.sqlite.gui.model;

import java.util.*;

public class Database {
	private List<Table> tables;
	private String name;

	public Database(String name) {
		this.name = name;
	}

	public void addTable(Table table) {
		getTables().add(table);
	}
	
	public List<Table> getTables() {
		if (tables == null) {
			tables = new ArrayList<Table>();
		}
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
