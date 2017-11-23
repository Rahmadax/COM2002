package mysql.query;

import java.sql.PreparedStatement;

import mysql.MySQLAccess;

public final class PatientQuery extends QuerySQL {

	protected PatientQuery(MySQLAccess access) {
		super(access);
	}

	public String get(Int ID) throws Exception {
        preparedStatement = connect.prepareStatement(
            "SELECT * FROM Patients WHERE PatientID = ID;");
        resultSet = preparedStatement.executeQuery();
		close();
		return resultSet.getString("FirstName");
	}
	
	public void add(int PatientID, String FirstName, String LastName, String DOB, int ContactNumber, int HouseNumber, String PostCode) throws Exception {
		
		int patID = PatientID;
		String fName = FirstName;
		String lName = LastName;
		String dob = DOB;
		int cNumber = ContactNumber;
		int hNumber = HouseNumber;
		String pCode = PostCode;
		
		try {
			PreparedStatement insert = connect.prepareStatement("INSERT INTO Patient (PatientID, FirstName, LastName, DOB, ContactNumber, HouseNumber, PostCode) "
					+ "VALUES '+patID+', '+fName+', '+lName+', SELECT CONVERT (date, '+dob+'), '+cNumber+', '+hNumber+', '+pCode+')"); 
			insert.executeUpdate();
		} catch (Exception e) {System.out.println(e);}
		finally {
			System.out.println("Insert Completed");
		}
	}

	public void edit() {
		
	}

	public void remove(int PatientID) throws Exception {
		
		int patID = PatientID; 
		
		try {
			PreparedStatement delete = connect.prepareStatement("DELETE FROM Patient"
					+ "WHERE PatientID = ?");
			
			delete.setInt(1, patID);
			
			delete.executeUpdate();
			
		} catch (Exception e) {System.out.println(e);}
		finally {
			System.out.println("Deletion Completed");
		}
	}

}