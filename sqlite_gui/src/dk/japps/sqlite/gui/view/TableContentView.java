package dk.japps.sqlite.gui.view;

import javax.swing.*;

import dk.japps.sqlite.gui.logic.*;

public class TableContentView implements View {
	private String databaseName;
	private String tableName;
	
	public TableContentView(String databaseName, String tableName) {
		this.databaseName = databaseName;
		this.tableName = tableName;
	}
	
	@Override
	public JComponent build() {
		return new JTable(new TableContentViewModel(new DatabaseLogic().getTableContent(databaseName, tableName)));
	}
}
