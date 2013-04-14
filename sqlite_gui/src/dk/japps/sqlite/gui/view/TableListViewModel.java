package dk.japps.sqlite.gui.view;

import java.util.*;

import javax.swing.event.*;
import javax.swing.tree.*;

public class TableListViewModel implements TreeModel {
	private List<TreeModelListener> treeModelListeners;
	private TreeElement root;

	public TableListViewModel(TreeElement root) {
		this.root = root;
	}

	public List<TreeModelListener> getTreeModelListeners() {
		if (treeModelListeners == null) {
			treeModelListeners = new ArrayList<TreeModelListener>();
		}
		return treeModelListeners;
	}
	
	@Override
	public void addTreeModelListener(TreeModelListener treeModelListener) {
		getTreeModelListeners().add(treeModelListener);
	}

	@Override
	public Object getChild(Object parent, int index) {
		if (((TreeElement) parent).getChildren().size() > index) {
			return ((TreeElement) parent).getChildren().get(index);
		}
		return null;
	}

	@Override
	public int getChildCount(Object parent) {
		return ((TreeElement) parent).getChildren().size();
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		return ((TreeElement) parent).getChildren().indexOf(child);
	}

	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public boolean isLeaf(Object node) {
		return ((TreeElement) node).getChildren().isEmpty();
	}

	@Override
	public void removeTreeModelListener(TreeModelListener treeModelListener) {
		getTreeModelListeners().remove(treeModelListener);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		System.out.println();
	}
	
	public static class TreeElement {
		private TreeElement parent;
		private List<TreeElement> children;
		private String name;
		
		public TreeElement(String name) {
			this.name = name;
		}

		public void addChild(TreeElement child) {
			getChildren().add(child);
		}

		public List<TreeElement> getChildren() {
			if (children == null) {
				children = new ArrayList<TableListViewModel.TreeElement>();
			}
			return children;
		}

		public void setChildren(List<TreeElement> children) {
			this.children = children;
		}

		public TreeElement getParent() {
			return parent;
		}

		public void setParent(TreeElement parent) {
			this.parent = parent;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return getName();
		}
	}
}
