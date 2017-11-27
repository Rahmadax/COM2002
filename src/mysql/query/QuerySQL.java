package mysql.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mysql.MySQLAccess;

public abstract class QuerySQL {
	
	public static final int QUERY_TIMEOUT = 5;

	protected Connection connect;
	protected Statement statement;
	protected PreparedStatement preparedStatement;
	protected ResultSet resultSet;

	protected QuerySQL(MySQLAccess access) {
		this.connect = access.getConnection();
	}
	
	protected void close() throws SQLException {
		if (resultSet != null) {
			resultSet.close();
        }

        if (statement != null) {
            statement.close();
        }
	}
	
	protected Statement createStatement() throws SQLException {
		Statement statement = connect.createStatement();
		statement.setQueryTimeout(QUERY_TIMEOUT);
		
		return statement;
	}
	
	protected PreparedStatement prepareStatement(String query) throws SQLException {
		PreparedStatement statement = connect.prepareStatement(query);
		statement.setQueryTimeout(QUERY_TIMEOUT);
		
		return statement;
	}
	
	protected int getRowCount(ResultSet resultSet) throws Exception {
		int rows = 0;
		
		resultSet.last();
		rows = resultSet.getRow();
		resultSet.beforeFirst();
		
		return rows;
	}

}
