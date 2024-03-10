package expt.db.basic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;


public class exptCursor {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://192.168.23.150:15400/db_test";
    static final String USER = "db_dev";
    static final String PASS = "Huawei123!@";

    static String conn_url = DB_URL + "?user=" + USER + "&password=" + PASS + "&currentSchema=schema_test";
        
    public static void main(String[] args) throws SQLException {
        PreparedStatement preparedStatement;

        try {
            Connection conn = dbUtils.getConnect(JDBC_DRIVER, conn_url);

            String targetQuery = "select * from test_table";
            preparedStatement = conn.prepareStatement(targetQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            dbUtils.printOneRecord(resultSet);
            
            resultSet.last();
            dbUtils.printOneRecord(resultSet);
            
            resultSet.previous();
            dbUtils.printOneRecord(resultSet);
            
            resultSet.first();
            dbUtils.printOneRecord(resultSet);
            
            System.out.println("is before first: " + resultSet.isBeforeFirst());
            resultSet.afterLast();
            System.out.println("is after last: " + resultSet.isAfterLast());
            resultSet.beforeFirst();
            System.out.println("is before first: " + resultSet.isBeforeFirst());
            resultSet.next();
            dbUtils.printOneRecord(resultSet);
            
            resultSet.absolute(4);
            dbUtils.printOneRecord(resultSet);
            resultSet.relative(1);
            dbUtils.printOneRecord(resultSet);
            
            resultSet.absolute(-2);
            dbUtils.printOneRecord(resultSet);
            resultSet.relative(-1);
            dbUtils.printOneRecord(resultSet);
            
            preparedStatement.close();
            dbUtils.closeConnect(conn);

        } catch (SQLException ex) {
            System.err.println("SQLException information");
            while (ex != null) {
                System.err.println("Error msg: " + ex.getMessage());
                ex = ex.getNextException();
            }
        }
    }
}
