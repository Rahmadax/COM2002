package mysql.query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import mysql.MySQLAccess;

public class AddressQuery extends QuerySQL {
	
	public AddressQuery(MySQLAccess access) {
		super(access);
	}
	
	public String get(int houseNumber, String postCode) throws Exception {
		PreparedStatement get = prepareStatement("SELECT HouseNumer, Postcode FROM Address WHERE "
				+ "HouseNumber = ? and Postcode = ?;");
		preparedStatement.setInt(1, houseNumber);
		preparedStatement.setString(2, postCode);
		
		resultSet = preparedStatement.executeQuery();
		
		int hn = 0;
		String pc = null;
		
		while (resultSet.next()) {
			hn = resultSet.getInt(1);
			pc = resultSet.getString(2);
		}
		return hn.toString() + pc;
		
	}
	public void add(int houseNumber, String postCode, String streetName, String districtName, String cityName) {
		
		try {
			PreparedStatement insert = connect.prepareStatement("INSERT INTO Address VALUES ("
					+houseNumber+ ", '" +postCode+ "', '" +streetName+ "', '" +districtName+ "', '" +cityName+ "');");
			
			insert.executeUpdate();
			
		} catch(Exception e) {System.out.println(e);}
	}
	
	
}
