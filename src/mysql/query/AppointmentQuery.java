package mysql.query;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import calendar.Appointment;
import mysql.MySQLAccess;

public class AppointmentQuery extends QuerySQL {

	public AppointmentQuery(MySQLAccess access) {
		super(access);
	}
	
	public boolean isValidTimeSlot(HashMap<String, Object> map) throws Exception {
		String appDate = (String) map.get("AppointmentDate");
		String startTime = (String) map.get("StartTime");
		String partner = (String) map.get("Partner");
		String endTime = (String) map.get("EndTime");
		
		preparedStatement = prepareStatement("SELECT * FROM Appointments WHERE "
				+ "AppointmentDate = (SELECT STR_TO_DATE(?, '%Y-%m-%d')) AND Partner = ? AND "
				+ "((StartTime >= (SELECT STR_TO_DATE(?, '%h:%i %p')) AND StartTime <= (SELECT STR_TO_DATE(?, '%h:%i %p'))) OR "
				+ "(EndTime >= (SELECT STR_TO_DATE(?, '%h:%i %p')) AND EndTime <= (SELECT STR_TO_DATE(?, '%h:%i %p'))));"); 
		preparedStatement.setString(1, appDate);
		preparedStatement.setString(2, partner);
		preparedStatement.setString(3, startTime);
		preparedStatement.setString(4, startTime);
		preparedStatement.setString(5, endTime);
		preparedStatement.setString(6, endTime);
		
		resultSet = preparedStatement.executeQuery();
		
		return !resultSet.next();
	}

	public Appointment[] get(Date date) throws Exception {
		preparedStatement = prepareStatement(
				"SELECT StartTime, Partner, EndTime, PatientID "
				+ "FROM Appointments WHERE AppointmentDate = ?;");
		preparedStatement.setDate(1, new java.sql.Date(date.getTime()));
		resultSet = preparedStatement.executeQuery();
		
		int rows = getRowCount(resultSet);
		Appointment[] appointments = new Appointment[rows];
		
		while (resultSet.next()) {
			int currRow = resultSet.getRow() - 1;
			
			Time startTime = resultSet.getTime(1);
			String partner = resultSet.getString(2);
			Time endTime = resultSet.getTime(3);
			int patientID = resultSet.getInt(4);

			appointments[currRow] = new Appointment(
					createDate(date, startTime), createDate(date, endTime), 
					partner, patientID);
		}
		
		return appointments;
	}

	public void add(HashMap<String, Object> map) throws Exception {
		String appDate = (String) map.get("AppointmentDate");
		String startTime = (String) map.get("StartTime");
		String partner = (String) map.get("Partner");
		String endTime = (String) map.get("EndTime");
		int patID = (int) map.get("PatientID");
		
		preparedStatement = prepareStatement("INSERT INTO Appointments (AppointmentDate, StartTime, Partner, EndTime, PatientID) "
				+ "VALUES ((SELECT STR_TO_DATE(?, '%Y-%m-%d')), (SELECT STR_TO_DATE(?, '%h:%i %p')), ?, (SELECT STR_TO_DATE(?, '%h:%i %p')), ?)"); 
		preparedStatement.setString(1, appDate);
		preparedStatement.setString(2, startTime);
		preparedStatement.setString(3, partner);
		preparedStatement.setString(4, endTime);
		preparedStatement.setInt(5, patID);
		
		preparedStatement.executeUpdate();
	}

	public void remove(Appointment a) throws Exception {
		preparedStatement = prepareStatement("DELETE FROM Appointments WHERE "
				+ "AppointmentDate = ? AND StartTime = ? AND Partner = ?;");
		preparedStatement.setDate(1, new java.sql.Date(a.getStartDate().getTime()));
		preparedStatement.setTime(2, new java.sql.Time(a.getStartDate().getTime()));
		preparedStatement.setString(3, a.getPartner());
		
    	preparedStatement.executeUpdate();
	}
	
	private Date createDate(Date date, Time time) {
		Calendar timeC = Calendar.getInstance(Locale.UK);
		timeC.setTime(time);
		
		Calendar calendar = Calendar.getInstance(Locale.UK);
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, timeC.get(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, timeC.get(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, timeC.get(Calendar.SECOND));

		return calendar.getTime();
	}
	
}
