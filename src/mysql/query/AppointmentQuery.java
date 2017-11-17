package mysql.query;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import calendar.Appointment;
import mysql.MySQLAccess;

public class AppointmentQuery extends QuerySQL {

	public AppointmentQuery(MySQLAccess access) {
		super(access);
	}
	
	public Appointment[] get(Date date) throws Exception {
		preparedStatement = connect.prepareStatement(
				"SELECT * FROM Appointments WHERE AppointmentDate = ?;");
		preparedStatement.setDate(1, new java.sql.Date(date.getTime()));
		resultSet = preparedStatement.executeQuery();
		
		int rows = getRowCount(resultSet);
		Appointment[] appointments = new Appointment[rows];
		
		while (resultSet.next()) {
			int currRow = resultSet.getRow() - 1;
			
			Time startTime = resultSet.getTime(2);
			String partner = resultSet.getString(3);
			Time endTime = resultSet.getTime(4);
			int patientID = resultSet.getInt(6);

			appointments[currRow] = new Appointment(
					createDate(date, startTime), createDate(date, endTime), 
					partner, patientID);
		}

		close();
		
		return appointments;
	}

	public void add() {
		
	}
	
	public void remove() {
		
	}
	
	private Date createDate(Date date, Time time) {
		Calendar timeC = Calendar.getInstance(Locale.UK);
		timeC.setTime(time);
		
		Calendar calendar = Calendar.getInstance(Locale.UK);
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, timeC.get(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, timeC.get(Calendar.MINUTE));
		
		return calendar.getTime();
	}
	
}
