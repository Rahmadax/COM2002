package mysql.query;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

import calendar.Appointment;
import mysql.MySQLAccess;

public class TreatmentQuery extends QuerySQL {

	protected TreatmentQuery(MySQLAccess access) {
		super(access);
	}

    public String[] getTreatmentName(int[] treatmentIDList) throws Exception {
        String[] treatmentNameList = new String[treatmentIDList.length];
        for (int i = 0; i < treatmentIDList.length; i++) {
            PreparedStatement get = prepareStatement("SELECT (TreatmentName) FROM Treatments WHERE " +
                    "TreatmentID = ?");
            preparedStatement.setInt(1, treatmentIDList[i]);


            treatmentNameList[i] = String.valueOf(preparedStatement.executeQuery());

        } return treatmentNameList;
    }
}
