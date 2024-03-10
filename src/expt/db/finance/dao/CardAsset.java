package expt.db.finance.dao;

import expt.db.finance.Const;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CardAsset {
    private Connection conn;

    public CardAsset(Connection connection) {
        conn = connection;
    }

    public ResultSet queryCardAssetList(int clientId) {
        PreparedStatement pstat = null;
        ResultSet rs = null;

        try {
            pstat = conn.prepareStatement("");
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

    public boolean queryDepositAndWithdrawal(String cardNum, int action, int money) {
        PreparedStatement pstat = null;
        if (action == 2) {
            money = 0 - money;
        }
        try {
            pstat = conn.prepareStatement("");
            pstat.setInt(1, money);
            pstat.setString(2, cardNum);
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

    public boolean insertCardAsset(String card_number) {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("");
            pstat.setString(1, card_number);
            pstat.setInt(2, 0);
            pstat.setString(3, "人民币");
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

    public boolean deleteCardAsset(String card_number) {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("");
            pstat.setString(1, card_number);

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
}
