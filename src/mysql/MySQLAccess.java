package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;

public class MySQLAccess {
	
	public static final String URL  = "";
	public static final String USER = "";
	public static final String PASS = "";
	
    private Connection connect = null;

    public MySQLAccess() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(URL, USER, PASS);
    }
    
    public Connection getConnection() {
    	return connect;
    }

    private void close() throws SQLException {
        if (connect != null) {
            connect.close();
        }
    }

}