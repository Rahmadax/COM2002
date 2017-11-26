package mysql.query;

import mysql.MySQLAccess;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HCPStoreQuery extends QuerySQL {
	
	protected HCPStoreQuery(MySQLAccess access) {
		super(access);
	}
	
	public String[] getAll() throws Exception {
		PreparedStatement getAll = prepareStatement("SELECT * FROM HCPStore;");

		ResultSet rs = getAll.executeQuery();
		
        int rows = getRowCount(rs);
        String[] HCPStore = new String[rows];
        
        while (rs.next()) {
            int currRow = rs.getRow() - 1;

            String HCPName = rs.getString(1);
            BigDecimal monthPay = rs.getBigDecimal(2);
            int checkupsLeft = rs.getInt(3);
            int hygieneLeft = rs.getInt(4);
            int repairsLeft = rs.getInt(5);
            
            HCPStore[currRow] = HCPName + " (" +String.format("%.2f", monthPay)+ ", " +checkupsLeft + ", " +hygieneLeft+ ", " +repairsLeft+ ")";
        }
		return HCPStore;
	}
}
