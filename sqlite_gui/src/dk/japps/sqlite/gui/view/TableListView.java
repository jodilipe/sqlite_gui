package dk.japps.sqlite.gui.view;

import javax.swing.*;

import dk.japps.sqlite.gui.logic.*;

public class TableListView implements View {
	String databaseName;
	
	public TableListView(String databaseName) {
		this.databaseName = databaseName;
	}
	
	@Override
	public JTree build() {
		return new JTree(new TableListViewModel(new TableListLogic().buildTableListRoot(new DatabaseLogic().openDatabase(databaseName))));
	}
}
