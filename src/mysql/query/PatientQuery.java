package mysql.query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
	
	public void add(String title, String firstName, String lastName, String dob, String contactNumber, int houseNumber, String postCode) {
		
		try {
			preparedStatement = prepareStatement("INSERT INTO Patients (Title, FirstName, LastName, DOB, ContactNumber, HouseNumber, Postcode)"
					+ " VALUES (?,?,?,?,?,?,?)");
			preparedStatement.setString(1, title);
			preparedStatement.setString(2, firstName);
			preparedStatement.setString(3, lastName);
			preparedStatement.setString(4, dob);
			preparedStatement.setString(5, contactNumber);
			preparedStatement.setInt(6, houseNumber);
			preparedStatement.setString(7, postCode);
			
			preparedStatement.executeUpdate();
			close();
		} catch (Exception e) {System.out.println(e);}
	}

	public void remove(int patID) throws Exception {
		
		try {
			PreparedStatement delete = connect.prepareStatement("DELETE FROM Patients"
					+ " WHERE PatientID = " +patID+ ";");
			
			delete.executeUpdate();
			
		} catch (Exception e) {System.out.println(e);}
		
		close();
	}
	
	public ResultSet findWithFirstName(String firstName) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE FirstName LIKE  '%" + firstName + "%'; ";
	     Statement st = connect.createStatement(); 
		 ResultSet rs = st.executeQuery(query);	
		close();
		return rs;				
	}
	public ResultSet findWithLastName(String lastName) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE LastName LIKE  '%" + lastName + "%'; ";
	     Statement st = connect.createStatement(); 
		 ResultSet rs = st.executeQuery(query);	
		close();
		return rs;				
	}
	public ResultSet findWithWholeName(String firstName, String lastName) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (FirstName LIKE  '%" + firstName + "%' AND LastName LIKE '%" + lastName + "%'); ";
	     Statement st = connect.createStatement(); 
		 ResultSet rs = st.executeQuery(query);	
		close();
		return rs;				
	}
	public ResultSet findWithFirstNameDOB(String firstName, String DoB) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (FirstName LIKE  '%" + firstName + "%'AND DOB LIKE '%" + DoB + "%'); ";
	     Statement st = connect.createStatement(); 
		 ResultSet rs = st.executeQuery(query);	
		close();
		return rs;				
	}
	public ResultSet findWithWholeNameDOB(String firstName, String lastName, String DoB) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (FirstName LIKE  '%" + firstName + "%' AND LastName LIKE '%" + lastName + "%' AND DOB LIKE '%" + DoB + "%'); ";
	     Statement st = connect.createStatement(); 
		 ResultSet rs = st.executeQuery(query);	
		close();
		return rs;				
	}
	public ResultSet findWithLastNamePostCode(String lastName, String postCode) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (LastName LIKE '%" + lastName + "%' AND Postcode LIKE '%" + postCode + "%'); ";
	     Statement st = connect.createStatement(); 
		 ResultSet rs = st.executeQuery(query);	
		close();
		return rs;				
	}
	public ResultSet findWithLastNamePostCodeHouseNum(String lastName, String postCode, String houseNum) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (LastName LIKE '%" + lastName + "%' AND Postcode LIKE '%" + postCode + "%' AND HouseNumber LIKE '%" + houseNum + "%'); ";
	     Statement st = connect.createStatement(); 
		 ResultSet rs = st.executeQuery(query);	
		close();
		return rs;				
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
	close();
        return (fn + " " + ln);
    }
}
