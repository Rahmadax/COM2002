package mysql.query;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

import calendar.Appointment;
import mysql.MySQLAccess;

public class TreatmentQuery extends QuerySQL {

	public TreatmentQuery(MySQLAccess access) {
		super(access);
	}

    public String[] getTreatmentName(int[] treatmentIDList) throws Exception {
        String[] treatmentNameList = new String[treatmentIDList.length];
        for (int i = 0; i < treatmentIDList.length; i++) {
        	preparedStatement = prepareStatement("SELECT (TreatmentName) FROM Treatments WHERE " +
                    "TreatmentID = ?");
            preparedStatement.setInt(1, treatmentIDList[i]);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            	treatmentNameList[i] = String.valueOf(resultSet.getString(1));
            }
        } 
        
        close();
        
        return treatmentNameList;
    }

    public void add(String[] treatments, Date date, Time startTime, String partner) throws SQLException {
	    for (int i = 0; i < treatments.length; i++){

            preparedStatement = prepareStatement("INSERT INTO Treatments (TreatmentName) " +
                    "VALUES (?);");
            preparedStatement.setString(1, treatments[i]);
            preparedStatement.executeUpdate();

            preparedStatement = prepareStatement("SELECT * FROM Treatments WHERE TreatmentID=(SELECT MAX(TreatmentID) FROM Treatments);");
            resultSet = preparedStatement.executeQuery();
            int treatmentID = 0;
            while(resultSet.next()){
                treatmentID = resultSet.getInt(1);
            }

            preparedStatement = prepareStatement("INSERT INTO TreatmentApp_Linker (TreatmentsID, AppointmentDate, StartTime, Partner) " +
                    "VALUES (?,?,?,?)");
            preparedStatement.setInt(1, treatmentID);
            preparedStatement.setDate(2, date);
            preparedStatement.setTime(3, startTime);
            preparedStatement.setString(4, partner);
            preparedStatement.executeUpdate();

        }
