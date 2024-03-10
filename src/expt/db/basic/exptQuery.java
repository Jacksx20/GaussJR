package expt.db.basic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;


public class exptQuery {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://192.168.23.150:15400/db_test";
    static final String USER = "db_dev";
    static final String PASS = "Huawei123!@";

    static String conn_url = DB_URL + "?user=" + USER + "&password=" + PASS + "&currentSchema=schema_test";
    
    public static void main(String[] args) throws SQLException {
        Connection conn = dbUtils.getConnect(JDBC_DRIVER, conn_url);
        ResultSet resultSet = null;
        
        //01
//        Statement statement = conn.createStatement();
//        resultSet = statement.executeQuery("select * from test_table");
//        dbUtils.printAllRecords(resultSet);
        
        //02
        PreparedStatement preparedStatement=conn.prepareStatement("select * from test_table where id=?;");
        preparedStatement.setObject(1,2);
        resultSet = preparedStatement.executeQuery();
        dbUtils.printAllRecords(resultSet);
        
        dbUtils.closeConnect(conn);
    }
    
}
