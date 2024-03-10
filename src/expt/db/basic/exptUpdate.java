package expt.db.basic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class exptUpdate {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://192.168.23.150:15400/db_test";
    static final String USER = "db_dev";
    static final String PASS = "Huawei123!@";

    static String conn_url = DB_URL + "?user=" + USER + "&password=" + PASS + "&currentSchema=schema_test";
        
    public static void main(String[] args) throws SQLException {
        Connection conn = dbUtils.getConnect(JDBC_DRIVER, conn_url);
        
        //01
        Statement statement = conn.createStatement();
        statement.execute("update test_table set name='wangwu' where id=1");
        
        //02
        PreparedStatement preparedStatement=conn.prepareStatement("update test_table set name=? where id=?");
        preparedStatement.setObject(1, "test_name");
        preparedStatement.setObject(2, 2);
        preparedStatement.execute();
        
        dbUtils.closeConnect(conn);
    }
    
}
