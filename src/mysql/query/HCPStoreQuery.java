package mysql.query;

import mysql.MySQLAccess;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// Class for HCPStore queries
public class HCPStoreQuery extends QuerySQL {
	
	public HCPStoreQuery(MySQLAccess access) {
		super(access);
	}

	// Retrives the details of all plans in the Store
	public String[] getAll() throws Exception {
		preparedStatement = prepareStatement("SELECT * FROM HCPStore;");

		resultSet = preparedStatement.executeQuery();
		
        int rows = getRowCount(resultSet);
        String[] HCPStore = new String[rows];
        
        while (resultSet.next()) {
            int currRow = resultSet.getRow() - 1;

            String HCPName = resultSet.getString(1);
            BigDecimal monthPay = resultSet.getBigDecimal(2);
            int checkupsMax = resultSet.getInt(3);
            int hygieneMax = resultSet.getInt(4);
            int repairsMax = resultSet.getInt(5);
            
            HCPStore[currRow] = HCPName + " (" +String.format("%.2f", monthPay)+ ", " +checkupsMax + ", " +hygieneMax+ ", " +repairsMax+ ")";
        }
        
        close();
        
		return HCPStore;
	}
}
