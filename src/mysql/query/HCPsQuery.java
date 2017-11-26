package mysql.query;

import mysql.MySQLAccess;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HCPsQuery extends QuerySQL {
    protected HCPsQuery(MySQLAccess access) {super(access);}
    
	public void HCPUpdate(int hCPID, int checkUpsLeft, int hygieneLeft, int repairsLeft) throws Exception{
		 String query = "UPDATE Appointments SET CheckupsLeft = " + checkUpsLeft  + ", HygieneLeft = " + hygieneLeft + ", RepairsLeft = " + repairsLeft +" WHERE HCPID = " + hCPID + "; ";
	     Statement st = connect.createStatement(); 
		 st.executeQuery(query);			 
		 close();						
	}
    public void removeHCP(int patientID) throws Exception {
        preparedStatement = prepareStatement("SELECT (HCPID) FROM HCPPatient_Linker WHERE "
                + "PatientID = ?;");
        preparedStatement.setInt(1, patientID);
        resultSet = preparedStatement.executeQuery();
        int hcpID = 0;
        while(resultSet.next()){
           hcpID = resultSet.getInt(1);
        }

        preparedStatement = prepareStatement("DELETE FROM HCPPatient_Linker WHERE "
                + "HCPID = ?;");
        preparedStatement.setInt(1, hcpID);
        preparedStatement.executeUpdate();

        preparedStatement = prepareStatement("DELETE FROM HCPs WHERE "
                + "HCPID = ?;");
        preparedStatement.setInt(1, hcpID);
        preparedStatement.executeUpdate();
    }

    public void addHCP(String hcpName, int patientID) throws Exception {

        PreparedStatement ps = prepareStatement ("SELECT * FROM HCPStore WHERE HCPName = ?;");
        int[] hcp = new int[3];
        double cost = 0;
        ps.setString(1, hcpName);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            cost = rs.getDouble(2);
            hcp[0] = rs.getInt(3);
            hcp[1] = rs.getInt(4);
            hcp[2] = rs.getInt(5);
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();

        preparedStatement = prepareStatement("INSERT INTO HCPs (HCPName, MonthlyPayment, CheckupsLeft, HygieneLeft, RepairsLeft, SignUpDate)"
                + "VALUES (?,?,?,?,?,?);");
        preparedStatement.setString(1, hcpName);
        preparedStatement.setDouble(2, cost);
        preparedStatement.setInt(3, hcp[0]);
        preparedStatement.setInt(4, hcp[1]);
        preparedStatement.setInt(5, hcp[2]);
        preparedStatement.setString(6, dtf.format(localDate));
        preparedStatement.execute();

        preparedStatement = prepareStatement("SELECT * FROM HCPs WHERE HCPID=(SELECT MAX(HCPID) FROM HCPs);");
        resultSet = preparedStatement.executeQuery();
        int hcpID = 0;
        while(resultSet.next()){
            hcpID = resultSet.getInt(1);
        }

        preparedStatement = prepareStatement("INSERT INTO HCPPatient_Linker (PatientID, HCPID)" +
                " VALUES (?,?);");
        preparedStatement.setInt(1, patientID);
        preparedStatement.setInt(2, hcpID);
        preparedStatement.executeUpdate();
    }
    
    public int[] get(int hcpID) throws Exception  {
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
        return list;
    }
}
