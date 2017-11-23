package mysql.query;

import java.sql.SQLException;
import java.sql.Time;

import calendar.Appointment;
import mysql.MySQLAccess;

public class TreatmentQuery extends QuerySQL {

	protected TreatmentQuery(MySQLAccess access) {
		super(access);
	}

	public void get(String date, String startTime, String partner) throws Exception {

	}
	
	public void add() {
		
	}
	
}
