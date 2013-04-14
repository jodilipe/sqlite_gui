package dk.japps.sqlite.gui.logic;

import dk.japps.sqlite.gui.model.*;
import dk.japps.sqlite.gui.view.TableListViewModel.TreeElement;

public class TableListLogic {

	public TreeElement buildTableListRoot(Database database) {
		TreeElement root = new TreeElement(database.getName());
		for (Table table : database.getTables()) {
			root.addChild(new TreeElement(table.getName()));
		}
		return root;
	}
}
