package expt.db.basic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.rowset.*;


public class exptPagination {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://192.168.23.150:15400/db_test";
    static final String USER = "db_dev";
    static final String PASS = "Huawei123!@";

    static String conn_url = DB_URL + "?user=" + USER + "&password=" + PASS + "&currentSchema=schema_test";
        
    public static void main(String[] args) throws SQLException {
        int pageNumber = 1; // from 1 to N
        int pageSize = 3;
        Connection conn = dbUtils.getConnect(JDBC_DRIVER, conn_url);
        
        if (args.length == 2) {
            pageNumber = Integer.parseInt(args[0]);
            pageSize = Integer.parseInt(args[1]);
        }
        
        pageQueryByLimitAndOffset(conn, pageNumber, pageSize);
        pageQueryByCachedRowSet(conn, pageNumber, pageSize);
        pageQueryByCursor(conn, pageNumber, pageSize);
        
        dbUtils.closeConnect(conn);
    }
    
    public static void pageQueryByCursor(Connection conn, int pageNumber, int pageSize) {
        PreparedStatement preparedStatement;
        
        int startNo = (pageNumber-1)*pageSize;
        int endNo = pageNumber*pageSize;
        
        String targetQuery = "select * from test_table";

        try {
            preparedStatement = conn.prepareStatement(targetQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setMaxRows(endNo);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            resultSet.beforeFirst();
            resultSet.relative(startNo);
            
            dbUtils.printAllRecords(resultSet);
            
            preparedStatement.close();
        } catch (SQLException ex) {
            System.err.println("SQLException information");
            while (ex != null) {
                System.err.println("Error msg: " + ex.getMessage());
                ex = ex.getNextException();
            }
        }
    }
    
    public static void pageQueryByCachedRowSet(Connection conn, int pageNumber, int pageSize) {
        PreparedStatement preparedStatement;
        String targetQuery = "select * from test_table";

        try {
            preparedStatement = conn.prepareStatement(targetQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            RowSetFactory factory =RowSetProvider.newFactory();
            CachedRowSet cachedRs = factory.createCachedRowSet();
            
            cachedRs.setPageSize(pageSize);
            cachedRs.populate(resultSet, (pageNumber-1)*pageSize+1);;
            dbUtils.printAllRecords(cachedRs);
            
            preparedStatement.close();
        } catch (SQLException ex) {
            System.err.println("SQLException information");
            while (ex != null) {
                System.err.println("Error msg: " + ex.getMessage());
                ex = ex.getNextException();
            }
        }
    }
    
    public static void pageQueryByLimitAndOffset(Connection conn, int pageNumber, int pageSize) {
        PreparedStatement preparedStatement;
        String targetQuery = "select * from test_table limit ?, ?";

        try {
            preparedStatement = conn.prepareStatement(targetQuery);
            preparedStatement.setInt(1, (pageNumber-1)*pageSize);
            preparedStatement.setInt(2, pageSize);
            ResultSet resultSet = preparedStatement.executeQuery();

            dbUtils.printAllRecords(resultSet);
            
            preparedStatement.close();
        } catch (SQLException ex) {
            System.err.println("SQLException information");
            while (ex != null) {
                System.err.println("Error msg: " + ex.getMessage());
                ex = ex.getNextException();
            }
        }
    }
}
