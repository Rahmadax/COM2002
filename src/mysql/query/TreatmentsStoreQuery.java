package mysql.query;

import mysql.MySQLAccess;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TreatmentsStoreQuery extends QuerySQL {
    public TreatmentsStoreQuery(MySQLAccess access) {super(access);}

    public String[][] getAll(String[] treatmentNames) throws Exception {
        String[][] treatmentsStore = new String[treatmentNames.length][3];
        for (int i = 0; i < treatmentNames.length; i++ ){
            PreparedStatement preparedStatement = prepareStatement("SELECT (TreatmentName, TreatmentCost, Type) "
                    + "FROM TreatmentsStore WHERE TreatmentName = ?;");
            preparedStatement.setString(1, treatmentNames[i]);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String treatmentName = resultSet.getString(1);
                String treatmentCost = Double.toString(resultSet.getDouble(2));
                String type = resultSet.getString(3);

                treatmentsStore[i][1] = treatmentName;
                treatmentsStore[i][2] = treatmentCost;
                treatmentsStore[i][3] = type;
            }
        }

        close();
        return treatmentsStore;
    }

}
