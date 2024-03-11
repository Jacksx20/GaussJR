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

    // 完成“查询客户卡余额”功能模块
    public ResultSet queryCardAssetList(int clientId) {
        PreparedStatement pstat = null;
        ResultSet rs = null;

        try {
            pstat = conn.prepareStatement(
                    "select b_number as 银行卡号, b_type as 卡片类型, card_money as 账户余额, moneytype as 币种 from bank_card inner join card_asset on b_number = card_num where b_client_id = ?");
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

    // 存取款模块
    public boolean queryDepositAndWithdrawal(String cardNum, int action, int money) {
        PreparedStatement pstat = null;
        if (action == 2) {
            money = 0 - money;
        }
        try {
            pstat = conn.prepareStatement("UPDATE card_asset SET card_money = card_money + ? WHERE card_num = ?");
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

    // 客户开卡时，新增一条存/取款信息
    public boolean insertCardAsset(String card_number) {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("insert into card_asset (card_num, card_money, moneytype) values(?,?,?)");
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

    // 客户销卡”模块,客户销卡时，删除此卡的存/取款信息
    public boolean deleteCardAsset(String card_number) {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("delete from card_asset where card_num = ?");
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
