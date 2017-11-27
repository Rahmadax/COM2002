package mysql.query;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

import calendar.Appointment;
import mysql.MySQLAccess;

// Class for Treatment queries
public class TreatmentQuery extends QuerySQL {

	public TreatmentQuery(MySQLAccess access) {
		super(access);
	}

	// Gets the treatment names of a list of ID
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

    // returns a treatment name with its IDs
    public String getTreatmentName(int treatmentID) throws Exception {
        preparedStatement = prepareStatement("SELECT (TreatmentName) FROM Treatments WHERE " +
                "TreatmentID = ?");
        preparedStatement.setInt(1, treatmentID);
        resultSet = preparedStatement.executeQuery();
        String treatmentName = null;
        if (resultSet.next()) {
            treatmentName = String.valueOf(resultSet.getString(1));
        }
        close();
        return treatmentName;
    }


    // Adds a new treatment from an Appointment
    public void add(String[] treatments, Appointment a) throws SQLException {
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

            preparedStatement = prepareStatement("INSERT INTO TreatmentApp_Linker (TreatmentID, AppointmentDate, StartTime, Partner) " +
                    "VALUES (?,?,?,?)");
            preparedStatement.setInt(1, treatmentID);
            preparedStatement.setDate(2, new java.sql.Date(a.getStartDate().getTime()));
    		preparedStatement.setTime(3, new java.sql.Time(a.getStartDate().getTime()));
    		preparedStatement.setString(4, a.getPartner());
            
            preparedStatement.executeUpdate();

        }
    }
}
