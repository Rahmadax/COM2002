package mysql.query;

import mysql.MySQLAccess;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;

import calendar.Appointment;

public class TreatmentApp_LinkerQuery extends QuerySQL {
    public TreatmentApp_LinkerQuery(MySQLAccess access) { super(access); }
    public int[] getIDs(Appointment a) throws Exception{
    	java.sql.Date appointmentDate = new java.sql.Date(a.getStartDate().getTime());		
		java.sql.Time startTime = new java.sql.Time(a.getStartDate().getTime());
		String partner = a.getPartner();
    	
		preparedStatement = prepareStatement("SELECT TreatmentID FROM TreatmentApp_Linker WHERE " +
                "AppointmentDate = ? AND StartTime = ? AND Partner = ?");
        preparedStatement.setDate(1, appointmentDate);
        preparedStatement.setTime(2, startTime);
        preparedStatement.setString(3, partner);
        resultSet = preparedStatement.executeQuery();
        int[] idList = new int[getRowCount(resultSet)];
        while (resultSet.next()){
            int currRow = resultSet.getRow() - 1;
            int ID = resultSet.getInt(1);
            idList[currRow] = ID;
        }
        
        close();
        
        return idList;
    }
    /*
    public int[] getAppointment(int treatmentID) throws Exception{
        PreparedStatement get = prepareStatement("SELECT (AppointmentDate,StartTime,Partner) FROM Treatments WHERE " +
                "TreatmentID = ?");
        preparedStatement.setDate(1, treatmentID);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            int currRow = resultSet.getRow() - 1;



        }
    }*/

}
