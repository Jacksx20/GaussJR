package expt.db.finance;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import expt.db.finance.dao.*;


public class testDAO {
    
    public static void main(String[] args) throws SQLException {
        Connection conn = DBUtils.getConnect();
        
        testClient(conn);
        testBankCard(conn);
        testFinancialProduct(conn);
        testFinancialAsset(conn);
        
        conn.close();
    }
    
    public static void testFinancialAsset(Connection conn) {
        FinancialAsset fa = new FinancialAsset(conn);
        ResultSet rs = null;
        
        rs = fa.queryAssetList();
        DBUtils.printAllRecords(rs);
        
        rs = fa.queryAssetById(1);
        DBUtils.printAllRecords(rs);
        
        fa.insertAssetRecord(5, 1, 2, 1, 666);
        rs = fa.queryAssetByClientId(1);
        DBUtils.printAllRecords(rs);
        
        fa.deleteAssetRecordById(5);
        rs = fa.queryAssetList();
        DBUtils.printAllRecords(rs);
    }
    
    public static void testFinancialProduct(Connection conn) {
        FinancialProduct fp = new FinancialProduct(conn);
        ResultSet rs = null;
        
        rs = fp.queryProductList();
        DBUtils.printAllRecords(rs);
        
        rs = fp.queryProductById(1);
        DBUtils.printAllRecords(rs);
    }
    
    public static void testBankCard(Connection conn) {
        BankCard bc = new BankCard(conn);
        ResultSet rs = null;
        
        rs = bc.queryBankCardList();
        DBUtils.printAllRecords(rs);
        
        bc.insertBankCard("card_number_01", "储蓄卡", 1);
        rs = bc.queryBankCardByCardNumber("card_number_01");
        DBUtils.printAllRecords(rs);
        rs = bc.queryBankCardByClientId(1);
        DBUtils.printAllRecords(rs);
        
        bc.deleteBankCardByCardNumber("card_number_01");
        rs = bc.queryBankCardByClientId(1);
        DBUtils.printAllRecords(rs);
    }
    
    public static void testClient(Connection conn) {
        Client client = new Client(conn);
        ResultSet rs = null;
        
        rs = client.queryClientList();
        DBUtils.printAllRecords(rs);
        
        client.insertClient(11, "name01", "idCard01", "phone01", "email01");
        rs = client.queryClientById(11);
        DBUtils.printAllRecords(rs);
        
        client.updateClient(11, "name11", "phone11", "email11");
        rs = client.queryClientById(11);
        DBUtils.printAllRecords(rs);
        
        client.deleteClient(11);
        rs = client.queryClientList();
        DBUtils.printAllRecords(rs);
    }
    
}
