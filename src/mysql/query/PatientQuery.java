package mysql.query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import mysql.MySQLAccess;

public final class PatientQuery extends QuerySQL {

	public PatientQuery(MySQLAccess access) {
		super(access);
	}

	public String getPatientName(int ID) throws Exception {
        preparedStatement = connect.prepareStatement(
            "SELECT * FROM Patients WHERE PatientID = ID;");
		preparedStatement.setQueryTimeout(5);
        resultSet = preparedStatement.executeQuery();
		close();
		return resultSet.getString("FirstName");
	}
	
	public void add(int patID, String firstName, String lastName, String dob, int contactNumber, int houseNumber, String postCode) throws Exception {
		
		try {
			PreparedStatement useDB = connect.prepareStatement("USE team049;");
			PreparedStatement insert = connect.prepareStatement("INSERT INTO Patients VALUES ("
					+patID+ ", '" +firstName+ "', '" +lastName+ "', '" +dob+ "', " +contactNumber+ ", " +houseNumber+ ", '" +postCode+ "');");
			
			useDB. executeUpdate();
			insert.executeUpdate();
			    
		} catch (Exception e) {System.out.println(e);}
		finally {
			System.out.println("Insert Completed");
		}
	}

	public void remove(int patID) throws Exception {
		
		try {
			PreparedStatement useDB = connect.prepareStatement("USE team049;");
			PreparedStatement delete = connect.prepareStatement("DELETE FROM Patients"
					+ " WHERE PatientID = " +patID+ ";");
			
			useDB.executeUpdate();
			delete.executeUpdate();
			
		} catch (Exception e) {System.out.println(e);}
		finally {
			System.out.println("Deletion Completed");
		}
	}
	public ResultSet findFirstName(String firstName) throws Exception {		
		 preparedStatement = connect.prepareStatement("SELECT PatientID, FirstName, LastName, DOB, ContactNumber, HouseNumber, Postcode FROM Patient WHERE FirstName LIKE '% " + firstName + " '%");
	     resultSet = preparedStatement.executeQuery();	
		return resultSet;				
       }
	
	public String getFullName (int patientID) throws Exception {
        String fn = null;
        String ln = null;
        preparedStatement = prepareStatement(
                "SELECT FirstName, LastName "
                        + "FROM Patients WHERE PatientID = ?;");
        preparedStatement.setInt(1, patientID);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            fn = resultSet.getString(1);
            ln = resultSet.getString(2);

        }
        return (fn + " " + ln);
    }
}
