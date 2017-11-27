package mysql.query;

import mysql.MySQLAccess;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.text.SimpleDateFormat;

import calendar.Appointment;
// Class for TreatmentApp Linker queries
public class TreatmentApp_LinkerQuery extends QuerySQL {
    public TreatmentApp_LinkerQuery(MySQLAccess access) { super(access); }

    // getIDs from Appointment primary keys
    public int[] getIDs(String appDate, String sTime, String p) throws Exception{    	
    	java.sql.Date appointmentDate = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(appDate).getTime());		
		java.sql.Time startTime = new java.sql.Time(new SimpleDateFormat("HH:mm:ss").parse(sTime).getTime());
		String partner = p;
    	
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

    // gets the Id from an appointments primary keys (Appointment form)
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


}
