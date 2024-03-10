package expt.db.finance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FinancialProduct {
    private Connection conn;
    
    public FinancialProduct(Connection connection) {
        conn = connection;
    }
    
    public ResultSet queryProductList() {
        PreparedStatement pstat = null;
        ResultSet rs = null;

        try {
            pstat = conn.prepareStatement("select p_id as 产品编号, p_name as 产品名称, "
                    + "p_description as 产品描述, p_amount as 购买金额, p_year as 理财年限 "
                    + "from financial_product");
            rs = pstat.executeQuery();
        } catch (SQLException ex) {
            System.err.println("SQLException information");
            while (ex != null) {
                System.err.println("Error msg: " + ex.getMessage());
                ex = ex.getNextException();
            }
        }
        
        return rs;
    }
    
    public ResultSet queryProductById(int product_id) {
        PreparedStatement pstat = null;
        ResultSet rs = null;

        try {
            pstat = conn.prepareStatement("select p_id as 产品编号, p_name as 产品名称, "
                    + "p_description as 产品描述, p_amount as 购买金额, p_year as 理财年限 "
                    + "from financial_product where p_id = ?");
            pstat.setInt(1, product_id);
            rs = pstat.executeQuery();
        } catch (SQLException ex) {
            System.err.println("SQLException information");
            while (ex != null) {
                System.err.println("Error msg: " + ex.getMessage());
                ex = ex.getNextException();
            }
        }
        return rs;
    }
}
