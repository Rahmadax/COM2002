package mysql.query;

import mysql.MySQLAccess;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TreatmentsStoreQuery extends QuerySQL {
    public TreatmentsStoreQuery(MySQLAccess access) {super(access);}

    public String[] getAll(String partner) throws Exception {
        PreparedStatement preparedStatement = prepareStatement("SELECT TreatmentName, TreatmentCost "
                + "FROM TreatmentsStore WHERE Partner = ?;");
        preparedStatement.setString(1, partner);
        
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

}
