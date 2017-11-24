package mysql.query;

import java.sql.PreparedStatement;
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

		close();
		
		return appointments;
	}

	public void add(HashMap<String, String> map) throws Exception {
		String appDate = (String) map.get("AppointmentDate");
		String startTime = (String) map.get("StartTime");
		String partner = (String) map.get("Partner");
		String endTime = (String) map.get("EndTime");
		int patID = Integer.parseInt(map.get("PatientID"));
		
		preparedStatement = prepareStatement("INSERT INTO Appoitment (AppointmentDate, StartTime, Partner, EndTime, Empty, PatientID) "
				+ "VALUES (SELECT CONVERT(date, ?), SELECT CONVERT(time, ?), '+partner+', SELECT CONVERT(time, ?), ?, ?)"); 
		preparedStatement.setString(1, appDate);
		preparedStatement.setString(2, startTime);
		preparedStatement.setString(3, partner);
		preparedStatement.setString(4, endTime);
		preparedStatement.setInt(5, patID);
		
		preparedStatement.executeUpdate();
		
		close();
	}
	
	public void remove(int patID, String appDate) throws Exception {
		
		try {
			PreparedStatement useDB = connect.prepareStatement("USE team049;");
			PreparedStatement delete = connect.prepareStatement("DELETE FROM Appointments"
					+ "WHERE PatientID = "+patID+ " and AppointmentDate = '"+appDate+"');");
			
			useDB.executeUpdate();
			delete.executeUpdate();
			
		} catch (Exception e) {System.out.println(e);}
		finally {
			System.out.println("Deletion Completed");
		}
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
