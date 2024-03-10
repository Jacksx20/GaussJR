package expt.db.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;


public class dbUtils {
   
    public static Connection getConnect(String driver, String db_conn_url) {
        Connection conn = null;
        
        // register jdbc driver
        try {
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        // open connecting
        System.out.println("connecting database...");
        try {
            System.out.println("connection url is: " + db_conn_url);
            conn = DriverManager.getConnection(db_conn_url);
            System.out.println("connection successfully！");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void closeConnect(Connection conn) {
        try {
            conn.close();
            System.out.println("connection closed！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void printAllRecords(ResultSet rs) {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                System.out.print(metaData.getColumnName(i + 1) + "\t");
            }
            System.out.println();

            while (rs.next()) {
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    System.out.print(rs.getString(i + 1) + "\t");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void printOneRecord(ResultSet rs) {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                System.out.print(metaData.getColumnName(i + 1) + "\t");
            }
            System.out.println();

            for (int i = 0; i < metaData.getColumnCount(); i++) {
                System.out.print(rs.getString(i + 1) + "\t");
            }

            System.out.println("\ncurrent row number: " + rs.getRow() + "\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

