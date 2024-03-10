package expt.db.finance.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Properties;
import expt.db.finance.Const;

public class DBUtils {

    public static Connection getConnect() {
        Connection conn = null;

        System.out.println("connecting database...");
        try {
            InputStream inputStream = DBUtils.class.getClassLoader()
                    .getResourceAsStream("expt/db/finance/resources/config-db.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            String url = properties.getProperty("DB_URL");
            String user = properties.getProperty("USER");
            String password = properties.getProperty("PASSWORD");
            String driverClass = properties.getProperty("JDBC_DRIVER");
            String schema = properties.getProperty("SCHEMA");

            Class.forName(driverClass);
            conn = DriverManager.getConnection(url, user, password);
            conn.setSchema(schema);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        System.out.println("connection successfully！");
        return conn;
    }

    public static void closeConnect(Connection conn) {
        System.out.println("colsing connection...");
        try {
            conn.close();
            System.out.println("connection closed！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printAllRecords(ResultSet rs) {
        try {
            if (rs == null || rs.isBeforeFirst() == false) {
                System.out.println(Const.PREFIX_LEVEL_SEC + "未查询到相关数据！");
                return;
            }
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
            if (rs == null || rs.isBeforeFirst() == false) {
                System.out.println(Const.PREFIX_LEVEL_SEC + "未查询到相关数据！");
                return;
            }

            ResultSetMetaData metaData = rs.getMetaData();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                System.out.print(metaData.getColumnName(i + 1) + "\t");
            }
            System.out.println();

            rs.next();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                System.out.print(rs.getString(i + 1) + "\t");
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}