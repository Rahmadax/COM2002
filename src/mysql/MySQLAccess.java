package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLAccess {
	
	public static final int LOGIN_TIMEOUT = 5;
	
	public static final String URL  = "jdbc:mysql://stusql.dcs.shef.ac.uk/team049";
	public static final String USER = "team049";
	public static final String PASS = "efb1faf5";
	
    private Connection connect = null;

    public MySQLAccess() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        DriverManager.setLoginTimeout(LOGIN_TIMEOUT);
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