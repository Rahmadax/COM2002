package mysql.query;

import calendar.Appointment;
import mysql.MySQLAccess;

import java.util.ArrayList;
import java.util.Iterator;

public class CalculateReciept {

    public String[][] getReciept(int patientID) throws Exception {
        MySQLAccess access = new MySQLAccess();
        HCPPatientLinkerQuery qHP_L = new HCPPatientLinkerQuery(access);
        HCPsQuery q = new HCPsQuery(access);
        HCPPatientLinkerQuery q2 = new HCPPatientLinkerQuery(access);
        AppointmentQuery appQ = new AppointmentQuery(access);
        TreatmentApp_LinkerQuery TA_LQ = new TreatmentApp_LinkerQuery(access);

        // List of the patients Health Care Plan Details
        int[] hcpDetails = qHP_L.getHCPDetails(patientID);

        // List of unpaid Appointments
        Appointment[] appList = appQ.isPaidFor(patientID);
        for ( int n = 0; n < appList.length; n++){
            appQ.payFor(appList[n].getStartDate(), appList[n].getPartner());
        }

        // List of Treatments connected to unpaid appointments
        ArrayList<Integer> treatmentIDList=new ArrayList<Integer>();
        for ( int i = 0; i < appList.length; i++) {

            // a list of treatments connected to given Appointment.
            int[] treatmentIDs = TA_LQ.getIDs(appList[i]);
            for (int j = 0; j < treatmentIDs.length; j++) {
                treatmentIDList.add(treatmentIDs[j]);
            }
        }
        TreatmentQuery tQ = new TreatmentQuery(access);
        TreatmentsStoreQuery tsQ = new TreatmentsStoreQuery(access);
        String[] treatmentArray = new String[treatmentIDList.size()];
        for ( int k = 0; k < treatmentIDList.size(); k++){
            treatmentArray[k] = tQ.getTreatmentName(treatmentIDList.get(k));
        }

        String[][] treatmentFinalDetails = tsQ.getAll(treatmentArray);
        String[][] treatmentOutput = new String [treatmentIDList.size()][3];
        int totalCost = 0;
        for (int m = 0; m < treatmentIDList.size(); m++){
            System.out.println("Type: " + treatmentFinalDetails[m][2]);

            if (treatmentFinalDetails[m][2].equals("Checkup") && hcpDetails[1] > 0) {
                hcpDetails[1] -= 1;
                treatmentOutput[m][2] = "Pre-Paid";
            } else if (treatmentFinalDetails[m][2].equals("Hygiene") && hcpDetails[2] > 0) {
                hcpDetails[2] -= 1;
                treatmentOutput[m][2] = "Pre-Paid";
            } else if (treatmentFinalDetails[m][2].equals("Repair") && hcpDetails[3] > 0) {
                hcpDetails[3] -= 1;
                treatmentOutput[m][2] = "Pre-Paid";
            } else {
                treatmentOutput[m][2] = "Paid";
            }
            treatmentOutput[m][0] = treatmentFinalDetails[m][0];
            treatmentOutput[m][1] = treatmentFinalDetails[m][1];

            HCPsQuery HCPQ = new HCPsQuery(access);
            HCPQ.HCPUpdate(hcpDetails[0],hcpDetails[1],hcpDetails[2],hcpDetails[3]);


        }
        access.close();
        return treatmentOutput;
    }
}
