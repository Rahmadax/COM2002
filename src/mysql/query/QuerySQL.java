package mysql.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import mysql.MySQLAccess;

public abstract class QuerySQL {

	protected Connection connect;
	protected Statement statement;
	protected PreparedStatement preparedStatement;
	protected ResultSet resultSet;

	protected QuerySQL(MySQLAccess access) {
		this.connect = access.getConnection();
	}
	
	protected void close() throws Exception {
		if (resultSet != null) {
			resultSet.close();
        }

        if (statement != null) {
            statement.close();
        }
	}
	
	protected int getRowCount(ResultSet resultSet) throws Exception {
		int rows = 0;
		
		resultSet.last();
		rows = resultSet.getRow();
		resultSet.beforeFirst();
		
		return rows;
	}

}
