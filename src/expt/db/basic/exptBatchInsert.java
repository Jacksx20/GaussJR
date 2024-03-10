package expt.db.basic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.UUID;


public class exptBatchInsert {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://192.168.23.150:15400/db_test";
    static final String USER = "db_dev";
    static final String PASS = "Huawei123!@";

    static String conn_url = DB_URL + "?user=" + USER + "&password=" + PASS + "&currentSchema=schema_test";
        
    public static void main(String[] args) throws SQLException {
        int current;
        current = insertRecordOnceATime(1, 1000);
        insertRecordBatch(current, 1000);
    }
    
    public static int insertRecordOnceATime(int begin, int count) {
        PreparedStatement preparedStatement;
        int index = begin;

        try {
            Connection conn = dbUtils.getConnect(JDBC_DRIVER, conn_url);
            conn.setAutoCommit(true);

            String targetQuery = "INSERT INTO test_table(id, name, destination, uuid) VALUES(?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(targetQuery);

            long start = System.currentTimeMillis();

            for( ; index < begin+count; index++) {
                preparedStatement.setInt(1, index);
                preparedStatement.setString(2, "name-"+index);
                preparedStatement.setString(3, "destination-"+index);
                preparedStatement.setString(4, UUID.randomUUID().toString());
//                long startInternal = System.currentTimeMillis();
                preparedStatement.executeUpdate();
//                System.out.println("each transaction time taken = " + (System.currentTimeMillis() - startInternal) + " ms");
            }

            long end = System.currentTimeMillis();
            System.out.println("total time taken = " + (end - start) + " ms");
            System.out.println("avg total time taken = " + (end - start)/ count + " ms");

            preparedStatement.close();
            dbUtils.closeConnect(conn);

        } catch (SQLException ex) {
            System.err.println("SQLException information");
            while (ex != null) {
                System.err.println("Error msg: " + ex.getMessage());
                ex = ex.getNextException();
            }
        }
        
        System.out.println(index);
        return index;
    }
    
    public static void insertRecordBatch(int begin, int count) {
        PreparedStatement preparedStatement;
        int index = begin;  

        try {
            Connection conn = dbUtils.getConnect(JDBC_DRIVER, conn_url);
            conn.setAutoCommit(true);

            String targetQuery = "INSERT INTO test_table(id, name, destination, uuid) VALUES(?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(targetQuery);

            for( ; index < begin+count; index++) {
                preparedStatement.setInt(1, index);
                preparedStatement.setString(2, "name-"+index);
                preparedStatement.setString(3, "destination-"+index);
                preparedStatement.setString(4, UUID.randomUUID().toString());
                preparedStatement.addBatch();
            }

            long start = System.currentTimeMillis();
            int[] inserted = preparedStatement.executeBatch();
            long end = System.currentTimeMillis();

            System.out.println("total time taken to insert the batch = " + (end - start) + " ms");
            System.out.println("total time taken = " + (end - start)/count + " s");

            preparedStatement.close();
            dbUtils.closeConnect(conn);

            System.out.println("row influence number is: " + inserted.length);

        } catch (SQLException ex) {
            System.err.println("SQLException information");
            while (ex != null) {
                System.err.println("Error msg: " + ex.getMessage());
                ex = ex.getNextException();
            }
            throw new RuntimeException("Error");
        }
    }
    
}
