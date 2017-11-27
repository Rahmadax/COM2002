package mysql.query;

import mysql.MySQLAccess;


import java.sql.ResultSet;
import java.sql.Statement;

public class HCPPatientLinkerQuery extends QuerySQL {
	
	public HCPPatientLinkerQuery(MySQLAccess access) {
		super(access);
	}
	public int[] getHCPDetails(int patientID) throws Exception {
		String query = "SELECT HCPID FROM HCPPatient_Linker WHERE PatientID =" + patientID + ";";
	     preparedStatement = prepareStatement(query); 
		 resultSet = preparedStatement.executeQuery(query);
         int hcpID = 0;
		 while (resultSet.next())
		 	hcpID = resultSet.getInt(1);

		preparedStatement = prepareStatement("SELECT * FROM HCPs WHERE "
				+ "HCPID = ?;");
		preparedStatement.setInt(1, hcpID);
		resultSet = preparedStatement.executeQuery();
		int[] list = new int[4];
		while(resultSet.next()){
			list[0] = resultSet.getInt(1);
			list[1] = resultSet.getInt(4);
			list[2] = resultSet.getInt(5);
			list[3] = resultSet.getInt(6);
		}
		close();
		return list;
		}

	
}
