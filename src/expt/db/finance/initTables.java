package expt.db.finance;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import expt.db.finance.dao.DBUtils;



public class initTables {
    
    public static void main(String[] args) throws SQLException {
        Connection conn = DBUtils.getConnect();
        createTableClient(conn);
        createTableBankCard(conn);
        createTableFinancialProduct(conn);
        createTableFinancialAsset(conn);
        
        System.out.println("数据表初始化创建完成!");
    }
    
    public static void executeSql(Connection conn, String targetSql) {
        Statement statement = null;

        try {
            statement = conn.createStatement();
            statement.execute(targetSql);

        } catch (SQLException ex) {
            System.err.println("SQLException information");
            while (ex != null) {
                System.err.println("Error msg: " + ex.getMessage());
                ex = ex.getNextException();
            }
        }
    }
    
    public static void createTableClient(Connection conn) {
        String execSql = "DROP Table If Exists finance.client;" + 
                         "Create Table finance.client " + 
                         "(c_id int Primary key," +
                         " c_name varchar(100) not null," +
                         " c_id_card char(20) unique not null," +
                         " c_phone char(20) unique not null," +
                         " c_mail char(30) unique);";
        
        executeSql(conn, execSql);
        System.out.println("table client created");
    }
    
    public static void createTableBankCard(Connection conn) {
        String execSql = "DROP Table If Exists finance.bank_card;" + 
                         "Create Table finance.bank_card " + 
                         "(b_number char(30) Primary key," +
                         " b_type char(20) not null," +
                         " b_client_id int not null);";
        
        executeSql(conn, execSql);
        System.out.println("table bank_card created");
    }
    
    public static void createTableFinancialProduct(Connection conn) {
        String execSql = "DROP Table If Exists finance.financial_product;" + 
                         "Create Table finance.financial_product " + 
                         "(p_id int Primary key," +
                         " p_name varchar(100) not null," +
                         " p_description varchar(1000)," +
                         " p_amount int," +
                         " p_year int);";
        
        executeSql(conn, execSql);
        System.out.println("table financial_product created");
    }
    
    public static void createTableFinancialAsset(Connection conn) {
        String execSql = "DROP Table If Exists finance.financial_asset;" +
                         "Create Table finance.financial_asset " +
                         "(a_id int Primary key," +
                         " a_client_id int not null," +
                         " a_product_id int not null," +
                         " a_type int not null," +
                         " a_status char(20)," +
                         " a_quantity int," +
                         " a_income int," +
                         " a_purchase_time Date);";
        
        executeSql(conn, execSql);
        System.out.println("table financial_asset created");
    }
}
