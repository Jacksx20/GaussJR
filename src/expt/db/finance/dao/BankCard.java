package expt.db.finance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import expt.db.finance.Const;

public class BankCard {
    private Connection conn;
    
    public BankCard(Connection connection) {
        this.conn = connection;
    }
    
    public ResultSet queryBankCardList() {
        PreparedStatement pstat = null;
        ResultSet rs = null;

        try {
            pstat = conn.prepareStatement("select b_number as 银行卡号, b_type as 卡片类型, "
                    + "b_client_id as 客户ID "
                    + "from bank_card");
            rs = pstat.executeQuery();
        } catch (SQLException ex) {
            System.err.println("SQLException information");
            while (ex != null) {
                System.err.println("Error msg: " + ex.getMessage());
                ex = ex.getNextException();
            }
        }
        
        return rs;
    }
    
    public ResultSet queryBankCardByCardNumber(String bank_card_number) {
        PreparedStatement pstat = null;
        ResultSet rs = null;

        try {
            pstat = conn.prepareStatement("select b_number as 银行卡号, b_type as 卡片类型, "
                    + "b_client_id as 客户ID "
                    + "from bank_card where b_number = ?");
            pstat.setString(1, bank_card_number);
            rs = pstat.executeQuery();
        } catch (SQLException ex) {
            System.err.println("SQLException information");
            while (ex != null) {
                System.err.println("Error msg: " + ex.getMessage());
                ex = ex.getNextException();
            }
        }
        return rs;
    }
    
    public ResultSet queryBankCardByClientId(int clientId) {
        PreparedStatement pstat = null;
        ResultSet rs = null;

        try {
            pstat = conn.prepareStatement("select b_number as 银行卡号, b_type as 卡片类型, "
                    + "b_client_id as 客户ID "
                    + "from bank_card where b_client_id = ?");
            pstat.setInt(1, clientId);
            rs = pstat.executeQuery();
        } catch (SQLException ex) {
            System.err.println("SQLException information");
            while (ex != null) {
                System.err.println("Error msg: " + ex.getMessage());
                ex = ex.getNextException();
            }
        }
        return rs;
    }
    
    public int countOfBankCards(int clientId) {
        PreparedStatement pstat = null;
        ResultSet rs = null;
        int recordsCount = Const.INVALID;

        try {
            pstat = conn.prepareStatement("select count(*) as RECORDSCOUNT from bank_card where b_client_id = ?");
            pstat.setInt(1, clientId);
            rs = pstat.executeQuery();
            
            if (rs.next()) {
                recordsCount = rs.getInt("RECORDSCOUNT");
            }
        } catch (SQLException ex) {
            System.err.println("SQLException information");
            while (ex != null) {
                System.err.println("Error msg: " + ex.getMessage());
                ex = ex.getNextException();
            }
        }
        return recordsCount;
    }
    
    public boolean insertBankCard(String card_number, String card_type, int client_id) {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("INSERT INTO bank_card(b_number, b_type, b_client_id) "
                    + "VALUES (?,?,?);");
            pstat.setString(1, card_number);
            pstat.setString(2, card_type);
            pstat.setInt(3, client_id);
            pstat.execute();
            
            return Const.SUCCEED;
        } catch (SQLException ex) {
            System.err.println("SQLException information");
            while (ex != null) {
                System.err.println("Error msg: " + ex.getMessage());
                ex = ex.getNextException();
            }
            
            return Const.FAILED;
        }
    }

    public boolean deleteBankCardByCardNumber(String bank_card_number) {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("DELETE from bank_card where b_number = ?");
            pstat.setString(1, bank_card_number);
            pstat.execute();
            
            return true;
        } catch (SQLException ex) {
            System.err.println("SQLException information");
            while (ex != null) {
                System.err.println("Error msg: " + ex.getMessage());
                ex = ex.getNextException();
            }
            return false;
        }
    }

}
