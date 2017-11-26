package mysql.query;

import mysql.MySQLAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HCPsQuery extends QuerySQL {
    public HCPsQuery(MySQLAccess access) {super(access);}

    public void removeHCP(int patientID) throws Exception {
        preparedStatement = prepareStatement("DELETE FROM HCPs WHERE "
                + "PatientID = ?;");
        preparedStatement.setInt(1, patientID);
        preparedStatement.executeUpdate();
    }

    public void addHCP(String hcpName) throws Exception {

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

        preparedStatement = prepareStatement("INSERT INTO HCPs (HCPName, MonthlyPayment, CheckupsLeft, HygieneLeft, RepairsLeft)"
                + "VALUES (?,?,?,?,?);");
        preparedStatement.setString(1, hcpName);
        preparedStatement.setDouble(2, cost);
        preparedStatement.setInt(3, hcp[0]);
        preparedStatement.setInt(4, hcp[1]);
        preparedStatement.setInt(5, hcp[2]);
        preparedStatement.execute();
    }
}
