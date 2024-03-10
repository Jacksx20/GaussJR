package expt.db.basic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class exptCreate {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://192.168.23.150:15400/db_test";
    static final String USER = "db_dev";
    static final String PASS = "Huawei123!@";

    static String conn_url = DB_URL + "?user=" + USER + "&password=" + PASS + "&currentSchema=schema_test";
    
    public static void main(String[] args) throws SQLException {
        Connection conn = dbUtils.getConnect(JDBC_DRIVER, conn_url);
        
        // 01
        Statement statement = conn.createStatement();
        statement.execute("create table test_table (id int, name varchar(10), destination varchar(20), uuid varchar(36))");
//        statement.execute("drop table test_table");
        System.out.println("execute successfully！");
        
        // 02
//        PreparedStatement preparedStatement=conn.prepareStatement("create table test_table (id int,name varchar (10))");
//        preparedStatement.execute();
    }
    
}
