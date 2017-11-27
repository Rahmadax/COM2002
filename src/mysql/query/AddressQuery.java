package mysql.query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import mysql.MySQLAccess;

public class AddressQuery extends QuerySQL {
	
	public AddressQuery(MySQLAccess access) {
		super(access);
	}
	
	public ResultSet get(int houseNumber, String postCode) throws Exception {
		
		preparedStatement = prepareStatement("SELECT HouseNumber, Postcode FROM Address WHERE "
					+ "HouseNumber = " +houseNumber+ " and Postcode = '" +postCode+ "';");
			
		resultSet = preparedStatement.executeQuery();
			
		return resultSet;
	}
	
	
	public void add(int houseNumber, String postCode, String streetName, String districtName, String cityName) {
		try {
			preparedStatement = prepareStatement("INSERT INTO Address VALUES ("
					+houseNumber+ ", '" +postCode+ "', '" +streetName+ "', '" +districtName+ "', '" +cityName+ "');");
			
			preparedStatement.executeUpdate();
			close();
		} catch(Exception e) {System.out.println(e);}
		
	}
	
	
}
