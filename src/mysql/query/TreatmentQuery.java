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

    public String getTreatmentName(int treatmentID) throws Exception{
        PreparedStatement get = prepareStatement("SELECT (TreatmentName) FROM Treatments WHERE " +
                "TreatmentID = ?");
        preparedStatement.setInt(1, treatmentID);
        return String.valueOf(preparedStatement.executeQuery());

	}
}
