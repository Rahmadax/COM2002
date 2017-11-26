package mysql.query;

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
}
