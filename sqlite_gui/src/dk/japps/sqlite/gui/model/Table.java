package dk.japps.sqlite.gui.model;


public class Table {
	private String name;
	private QueryResult content;

	public Table(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public QueryResult getContent() {
		return content;
	}

	public void setContent(QueryResult content) {
		this.content = content;
	}
}
