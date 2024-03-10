package expt.db.basic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
//import java.sql.PreparedStatement;


public class exptInsert {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://192.168.23.150:15400/db_test";
    static final String USER = "db_dev";
    static final String PASS = "Huawei123!@";

    static String conn_url = DB_URL + "?user=" + USER + "&password=" + PASS + "&currentSchema=schema_test";
    
    public static void main(String[] args) throws SQLException {
        Connection conn = dbUtils.getConnect(JDBC_DRIVER, conn_url);
        
        Statement statement = conn.createStatement();
        statement.execute("INSERT INTO test_table(id, name, destination, uuid) "
                + "values (2, 'zhangsan', 'hangzhou', 123456789)");
        System.out.println("影响行数： "+statement.getUpdateCount());
        
//        PreparedStatement preparedStatement=conn.prepareStatement("insert into test_table (id,name) values (3,'zhaoliu')");
//        preparedStatement.execute();
//        System.out.println("num of rows influence： "+preparedStatement.getUpdateCount());
    }
    
}
