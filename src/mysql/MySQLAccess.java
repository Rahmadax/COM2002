package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLAccess {
	
	public static final String URL  = "jdbc:mysql://stusql.dcs.shef.ac.uk/team031";
	public static final String USER = "team031";
	public static final String PASS = "f1e07e09";
	
    private Connection connect = null;

    public MySQLAccess() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        DriverManager.setLoginTimeout(5);
        connect = DriverManager.getConnection(URL, USER, PASS);
    }
    
    public Connection getConnection() {
    	return connect;
    }

    public void close() throws SQLException {
        if (connect != null) {
            connect.close();
        }
    }

}