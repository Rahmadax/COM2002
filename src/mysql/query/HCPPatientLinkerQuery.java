package mysql.query;

import mysql.MySQLAccess;


import java.sql.ResultSet;
import java.sql.Statement;

public class HCPPatientLinkerQuery extends QuerySQL {
	
	public HCPPatientLinkerQuery(MySQLAccess access) {
		super(access);
	}
	public int getHCPID(int patientID) throws Exception {
		String query = "SELECT HCPID FROM Patients WHERE PatientID =" + patientID + ";";		 
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery(query);
		 int returnInt = resultSet.getInt(1);		 
		close();
		return returnInt;
	}

	
}
