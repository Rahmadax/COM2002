package mysql.query;

import java.sql.PreparedStatement;

import mysql.MySQLAccess;

public class AddressQuery extends QuerySQL {
	
	protected AddressQuery(MySQLAccess access) {
		super(access);
	}
	
	public void add(int houseNumber, String postCode, String streetName, String districtName, String cityName) {
		
		try {
			PreparedStatement useDB = connect.prepareStatement("USE team031;");
			PreparedStatement insert = connect.prepareStatement("INSERT INTO Address VALUES ("
					+houseNumber+ ", '" +postCode+ "', '" +streetName+ "', '" +districtName+ "', '" +cityName+ "');");
			
			useDB.executeUpdate();
			insert.executeUpdate();
			
		} catch(Exception e) {System.out.println(e);}
	}

}
