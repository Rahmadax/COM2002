package mysql.query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import mysql.MySQLAccess;

public final class PatientQuery extends QuerySQL {

	public PatientQuery(MySQLAccess access) {
		super(access);
	}
	
	public int getLastadded() throws Exception {
		preparedStatement = prepareStatement("SELECT * FROM Patients WHERE PatientID = (SELECT MAX(PatientID) FROM Patients);");
		resultSet = preparedStatement.executeQuery();
		resultSet.next();
		
		close();
		
		return resultSet.getInt(1);
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
			preparedStatement = prepareStatement("DELETE FROM Patients"
					+ " WHERE PatientID = " +patID+ ";");
			
			preparedStatement.executeUpdate();
			
		} catch (Exception e) {System.out.println(e);}
		
		close();
	}
	
	public ResultSet findWithFirstName(String firstName) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE FirstName LIKE  '%" + firstName + "%'; ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithLastName(String lastName) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE LastName LIKE  '%" + lastName + "%'; ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithWholeName(String firstName, String lastName) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (FirstName LIKE  '%" + firstName + "%' AND LastName LIKE '%" + lastName + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithFirstNameDOB(String firstName, String DoB) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (FirstName LIKE  '%" + firstName + "%'AND DOB LIKE '%" + DoB + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithWholeNameDOB(String firstName, String lastName, String DoB) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (FirstName LIKE  '%" + firstName + "%' AND LastName LIKE '%" + lastName + "%' AND DOB LIKE '%" + DoB + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithLastNamePostCode(String lastName, String postCode) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (LastName LIKE '%" + lastName + "%' AND Postcode LIKE '%" + postCode + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithLastNamePostCodeHouseNum(String lastName, String postCode, String houseNum) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (LastName LIKE '%" + lastName + "%' AND Postcode LIKE '%" + postCode + "%' AND HouseNumber LIKE '%" + houseNum + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
		public ResultSet findWithDOB(String DoB) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (DOB LIKE '%" + DoB + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithPostCode(String postCode) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (Postcode LIKE '%" + postCode + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithPostCodeHouseNum(String postCode, String houseNum) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (Postcode LIKE '%" + postCode + "%' AND HouseNumber LIKE '%" + houseNum + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithPostCodeHouseNumDOB(String postCode, String houseNum, String DoB) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (Postcode LIKE  '%" + postCode + "%' AND HouseNumber LIKE '%" + houseNum + "%' AND DOB LIKE '%" + DoB + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithLastNameDOB(String lastName, String DoB) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (LastName LIKE '%" + lastName + "%' AND DOB LIKE '%" + DoB + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithFirstNamePostCode(String firstName, String postCode) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (FirstName LIKE  '%" + firstName + "%' AND Postcode LIKE '%" + postCode + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithWholeNamePostCodeHouseNum(String firstName, String lastName, String postCode, String houseNum) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (FirstName LIKE  '%" + firstName + "%' AND LastName LIKE '%" + lastName + "%' AND Postcode LIKE '%" + postCode + "%' AND HouseNumber LIKE '%" + houseNum + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithFirstNamePostCodeHouseNum(String firstName, String postCode, String houseNum) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (FirstName LIKE  '%" + firstName + "%' AND Postcode LIKE '%" + postCode + "%' AND HouseNumber LIKE '%" + houseNum + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithWholeNamePostCodeHouseNumDOB(String firstName, String lastName, String postCode, String houseNum, String DoB) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (FirstName LIKE  '%" + firstName + "%' AND LastName LIKE '%" + lastName + "%' AND Postcode LIKE '%" + postCode + "%' AND HouseNumber LIKE '%" + houseNum + "%' AND DOB LIKE '%" + DoB + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithPostCodeDOB(String postCode, String DoB) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (Postcode LIKE '%" + postCode + "%' AND DOB LIKE '%" + DoB + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithFirstNamePostCodeHouseNumDOB(String firstName,String postCode, String houseNum, String DoB) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (FirstName LIKE '%" + firstName + "%' AND Postcode LIKE '%" + postCode + "%' AND HouseNumber LIKE '%" + houseNum + "%' AND DOB LIKE '%" + DoB + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithLastNamePostCodeHouseNumDOB(String lastName,String postCode, String houseNum, String DoB) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (LastName LIKE '%" + lastName + "%' AND Postcode LIKE '%" + postCode + "%' AND HouseNumber LIKE '%" + houseNum + "%' AND DOB LIKE '%" + DoB + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithFirstNameHouseNum(String firstName, String houseNum) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (FirstName LIKE '%" + firstName + "%' AND HouseNumber LIKE '%" + houseNum + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithLastNameHouseNum(String lastName, String houseNum) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (LastName LIKE '%" + lastName + "%' AND HouseNumber LIKE '%" + houseNum + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithLastNamePostCodeDOB(String lastName, String postCode, String DoB) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (LastName LIKE '%" + lastName + "%' AND Postcode LIKE '%" + postCode + "%' AND DOB LIKE '%" + DoB + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithFirstNamePostCodeDOB(String firstName, String postCode, String DoB) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (FirstName LIKE '%" + firstName + "%' AND Postcode LIKE '%" + postCode + "%' AND DOB LIKE '%" + DoB + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithWholeNameNamePostCodeDOB(String firstName, String lastName, String postCode, String DoB) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (FirstName LIKE '%" + firstName + "%' AND LastName LIKE '%" + lastName + "%' AND Postcode LIKE '%" + postCode + "%' AND DOB LIKE '%" + DoB + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithWholeNameHouseNum(String firstName, String lastName, String houseNum) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (FirstName LIKE '%" + firstName + "%' AND LastName LIKE '%" + lastName + "%' AND HouseNumber LIKE '%" + houseNum + "%'); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;				
	}
	public ResultSet findWithHouseNum(String houseNum) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (HouseNumber LIKE '%" + houseNum + "%' ); ";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery();	
		return resultSet;			
	}
	public ResultSet findWithHouseNumDOB(String houseNum, String DoB) throws Exception {		
		 String query = "SELECT * FROM Patients WHERE (HouseNumber LIKE '%" + houseNum + "%' AND DOB LIKE '%" + DoB + "%'); ";
	     preparedStatement = prepareStatement(query); 
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
	close();
        return (fn + " " + ln);
    }
}
