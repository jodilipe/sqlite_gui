package dk.japps.sqlite.gui.logic;

import java.sql.*;
import java.util.*;
import java.util.regex.*;

import dk.japps.sqlite.gui.model.*;

public class DatabaseLogic {
	public static DatabaseLogic instance = new DatabaseLogic();
	
	protected Connection getConnection(String path) {
		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + path);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return connection; 
	}
	
	public Database openDatabase(String path) {
		Database database = new Database(path);
		try {
			Connection connection = getConnection(path);
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet resultSet = metaData.getTables(null,	null, "%", null);
			while (resultSet.next()) {
				database.addTable(new Table(resultSet.getString(3)));
			}
			connection.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return database;
	}

	public Table executeQuery(Database database, String sql) {
		Table table = createTable(sql);
		try {
			Connection connection = getConnection(database.getName());
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				List<String> row = new ArrayList<String>();
				for (String columnName : table.getContent().getColumns()) {
					row.add(resultSet.getObject(columnName).toString());
				}
				table.getContent().addRow(row);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return table;
	}

	private Table createTable(String sql) {
		return new Table(extractTableName(sql));
	}
	
	private String extractTableName(String sql) {
		Pattern p=Pattern.compile("from\\s+(?:\\w+\\.)*(\\w+)(\\s*$|\\s+(WHERE|JOIN|START\\s+WITH|ORDER\\s+BY|GROUP\\s+BY))",Pattern.CASE_INSENSITIVE);
		Matcher m=p.matcher(sql);
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}
	
	public void execute(Database database, String sql) {
		try {
			Connection connection = getConnection(database.getName());
			Statement statement = connection.createStatement();
			statement.execute(sql);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
