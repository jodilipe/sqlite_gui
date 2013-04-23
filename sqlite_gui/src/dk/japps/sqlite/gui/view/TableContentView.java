package dk.japps.sqlite.gui.view;

import javax.swing.*;

import dk.japps.sqlite.gui.model.*;

public class TableContentView implements View {
	private Table table;
	
	public TableContentView(Table table) {
		this.table = table;
	}
	
	@Override
	public JTable build() {
		return new JTable(new TableContentViewModel(table));
	}
}
