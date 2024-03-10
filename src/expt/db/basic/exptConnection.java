package expt.db.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


public class exptConnection {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://192.168.0.175:15400/db_test";
    
    static final String USER = "db_dev";
    static final String PASS = "Huawei123!@";
    
    static String connection_url = DB_URL + "?user=" + USER + "&password=" + PASS;
    
    public static void main(String[] args) {
        getConnect01();
    }
    
    public static Connection getConnect01() {
        Connection conn = null;
        
        // register jdbc driver
        try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        // open connecting
        System.out.println("connecting database...");
        try {
            System.out.println("connection url is: " + connection_url);
            conn = DriverManager.getConnection(connection_url);
            System.out.println("connection successfully！");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Connection getConnect02() {
        Connection conn = null;
        
        // register jdbc driver
        try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        // open connecting
        System.out.println("connecting database...");
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("connection successfully！");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Connection getConnect03() {
        Properties info = new Properties();
        info.setProperty("user", USER);
        info.setProperty("password", PASS);
        Connection conn = null;
        
        // register jdbc driver
        try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        // open connecting
        System.out.println("connecting database...");
        try {
            conn = DriverManager.getConnection(DB_URL, info);
            System.out.println("connection successfully！");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

