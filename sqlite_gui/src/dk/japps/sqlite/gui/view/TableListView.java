package dk.japps.sqlite.gui.view;

import javax.swing.*;

import dk.japps.sqlite.gui.logic.*;
import dk.japps.sqlite.gui.model.*;

public class TableListView implements View {
	Database database;
	
	public TableListView(Database database) {
		this.database = database;
	}
	
	@Override
	public JTree build() {
		return new JTree(new TableListViewModel(new TableListLogic().buildTableListRoot(database)));
	}
}
