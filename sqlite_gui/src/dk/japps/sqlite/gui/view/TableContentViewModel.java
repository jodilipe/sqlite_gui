package dk.japps.sqlite.gui.view;

import java.util.*;

import javax.swing.event.*;
import javax.swing.table.*;

import dk.japps.sqlite.gui.model.*;

public class TableContentViewModel implements TableModel {
	private List<TableModelListener> tableModelListeners;
	private Table table;
	
	public TableContentViewModel(Table table) {
		this.table = table;
	}

	@Override
	public void addTableModelListener(TableModelListener tableModelListener) {
		getTableModelListeners().add(tableModelListener);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (getValueAt(0, columnIndex) != null) {
			return getValueAt(0, columnIndex).getClass();
		}
		return null;
	}

	@Override
	public int getColumnCount() {
		return !table.getRows().isEmpty() ? table.getRows().get(0).size() : 0;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return exists(0, columnIndex) ? getValueAt(0, columnIndex).toString() : null;
	}

	@Override
	public int getRowCount() {
		return table.getRows().size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return exists(rowIndex, columnIndex) ? table.getRows().get(rowIndex).get(columnIndex) : null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener tableModelListener) {
		getTableModelListeners().remove(tableModelListener);
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		if (exists(rowIndex, columnIndex)) {
			table.getRows().get(rowIndex).set(columnIndex, (String) value);
		}
	}
	
	public boolean exists(int rowIndex, int columnIndex) {
		return table.getRows().size() > rowIndex && table.getRows().get(rowIndex).size() > columnIndex;
	}

	public List<TableModelListener> getTableModelListeners() {
		if (tableModelListeners == null) {
			tableModelListeners = new ArrayList<TableModelListener>();
		}
		return tableModelListeners;
	}

	public void setTableModelListeners(List<TableModelListener> tableModelListeners) {
		this.tableModelListeners = tableModelListeners;
	}
}
