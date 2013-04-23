package dk.japps.sqlite.gui.model;

import java.util.*;

public class QueryResult {
	private List<List<String>> rows;
	
	public void addRow(List<String> row) {
		getRows().add(row);
	}

	public List<List<String>> getRows() {
		if (rows == null) {
			rows = new ArrayList<List<String>>();
		}
		return rows;
	}
	
	public void addColumn(String column) {
		getColumns().add(column);
	}

	public List<String> getColumns() {
		if (rows.isEmpty()) {
			rows.add(new ArrayList<String>());
		}
		return rows.get(0);
	}
}
