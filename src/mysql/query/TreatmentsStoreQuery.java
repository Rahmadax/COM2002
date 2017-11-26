package mysql.query;

import mysql.MySQLAccess;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TreatmentsStoreQuery extends QuerySQL {
    public TreatmentsStoreQuery(MySQLAccess access) {super(access);}

    public String[] getAll(String type) throws Exception {
        PreparedStatement preparedStatement = prepareStatement("SELECT TreatmentName, TreatmentCost "
                + "FROM TreatmentsStore WHERE Type = ?;");
        preparedStatement.setString(1, type);
        
        ResultSet resultSet = preparedStatement.executeQuery();

        int rows = getRowCount(resultSet);
        String[] treatmentsStore = new String[rows];

        while (resultSet.next()) {
            int currRow = resultSet.getRow() - 1;

            String treatmentName = resultSet.getString(1);
            BigDecimal treatmentCost = resultSet.getBigDecimal(2);

            treatmentsStore[currRow] = treatmentName + " (" + 
            		String.format("%.2f", treatmentCost) + ")";
            ;
        }

        close();
        return treatmentsStore;
    }
    
    public String[][] getAll(String[] treatmentNames) throws Exception {
        String[][] treatmentsStore = new String[treatmentNames.length][3];
        for (int i = 0; i < treatmentNames.length; i++ ){        	
            preparedStatement = prepareStatement("SELECT TreatmentName, TreatmentCost, Type "
                    + "FROM TreatmentsStore WHERE TreatmentName = ?;");
            preparedStatement.setString(1, treatmentNames[i]);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            	String treatmentName = resultSet.getString(1);
                String treatmentCost = Double.toString(resultSet.getDouble(2));
                String type = resultSet.getString(3);
                treatmentsStore[i] = new String[3];
                
                treatmentsStore[i][0] = treatmentName;
                treatmentsStore[i][1] = treatmentCost;
                treatmentsStore[i][2] = type;
            }
        }

        close();
        return treatmentsStore;
    }

}
