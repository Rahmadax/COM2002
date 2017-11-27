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
		
		close();
	
		return resultSet;
	}

	public String[] get (int patientID) throws Exception {

        MySQLAccess access = new MySQLAccess();
        PatientQuery q = new PatientQuery(access);
        String[] houseDetails = q.getHouseDetails(patientID);
		String[] addressDetails = new String[5];

		preparedStatement = prepareStatement("SELECT * FROM Address WHERE "
				+ "HouseNumber = ? AND Postcode = ?;");
        preparedStatement.setString(1, houseDetails[0]);
        preparedStatement.setString(2, houseDetails[1]);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            addressDetails[0] = resultSet.getString(1);
            addressDetails[1] = resultSet.getString(2);
            addressDetails[2] = resultSet.getString(3);
            addressDetails[3] = resultSet.getString(4);
            addressDetails[4] = resultSet.getString(5);
        }
        access.close();
		return addressDetails;
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

