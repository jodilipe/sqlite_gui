package dk.japps.sqlite.gui.model;

import java.util.*;

public class Table {
	private String name;
	private List<List<String>> rows;

	public Table(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addRow(String...values) {
		List<String> row = new ArrayList<String>();
		for (int i = 0; i < values.length; i++) {
			row.add(values[i]);
		}
		getRows().add(row);
	}

	public List<List<String>> getRows() {
		if (rows == null) {
			rows = new ArrayList<List<String>>();
		}
		return rows;
	}
}
