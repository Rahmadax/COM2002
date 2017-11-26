package mysql.query;

import mysql.MySQLAccess;

public class TestScript extends QuerySQL {
    public TestScript(MySQLAccess access) {
        super(access);
    }

    public static void main (String [] args) throws Exception {
        MySQLAccess access = new MySQLAccess();
        TestScript s = new TestScript(access);
        // Change this to run different scripts
        s.addTreatments();
    }

    public void addTreatments() throws Exception {
        // Manually increment both TreatmentIDs
        preparedStatement = prepareStatement("INSERT INTO Treatments (TreatmentID, TreatmentName)"
                + " VALUES ('3','Gold Crown')");
        preparedStatement.executeUpdate();

        preparedStatement = prepareStatement("INSERT INTO TreatmentApp_Linker (TreatmentID, AppointmentDate, StartTime, Partner)"
                + " VALUES ('3','2017-11-24', '10:00', 'Dentist')");
        preparedStatement.executeUpdate();
    }

    public void addAppointments() throws Exception {
        preparedStatement = prepareStatement("INSERT INTO Appointments (AppointmentDate, StartTime, Partner)"
                + " VALUES ('','','')");
        preparedStatement.executeUpdate();
    }

    public void addPatients() throws Exception {
        preparedStatement = prepareStatement("INSERT INTO Patients (PatientID, Title, FirstName, LastName, DOB, ContactNumber, 50, S10)"
                + " VALUES ('','','','','','','','')");
        preparedStatement.executeUpdate();
    }

}
