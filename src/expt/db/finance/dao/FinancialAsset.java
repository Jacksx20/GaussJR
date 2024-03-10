package expt.db.finance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import expt.db.finance.Const;

public class FinancialAsset {
    private Connection conn;

    public FinancialAsset(Connection connection) {
        conn = connection;
    }

    public ResultSet queryAssetList() {
        PreparedStatement pstat = null;
        ResultSet rs = null;

        try {
            pstat = conn.prepareStatement("select a_id as 资产编号, a_client_id as 客户ID, "
                    + "a_product_id as 产品编号, a_type as 产品类型, a_status as 状态, a_quantity as 金额, "
                    + "a_income as 收益金额, a_purchase_time as 申购时间 "
                    + "from financial_asset");
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

    public ResultSet queryAssetById(int asset_id) {
        PreparedStatement pstat = null;
        ResultSet rs = null;
        // 将“理财资产信息”中缺少“申购时间”的数据在查询时过滤掉，并且将“理财资产信息”中的列表信息按照“申购时间”降序排列.
        try {
            pstat = conn.prepareStatement("select a_id as 资产编号, a_client_id as 客户ID, "
                    + "a_product_id as 产品编号, a_type as 产品类型, a_status as 状态, a_quantity as 金额, "
                    + "a_income as 收益金额, a_purchase_time as 申购时间 "
                    + "from financial_asset where a_id = ?");
            pstat.setInt(1, asset_id);
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

    public ResultSet queryAssetByClientId(int client_id) {
        PreparedStatement pstat = null;
        ResultSet rs = null;

        try {
            pstat = conn.prepareStatement("select a_id as 资产编号, a_client_id as 客户ID, "
                    + "a_product_id as 产品编号, a_type as 产品类型, a_status as 状态, a_quantity as 金额, "
                    + "a_income as 收益金额, a_purchase_time as 申购时间 "
                    + "from financial_asset where a_client_id = ? and (a_purchase_time is not null) order by a_purchase_time desc");
            pstat.setInt(1, client_id);
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

    public boolean insertAssetRecord(int asset_id, int client_id, int product_id, int asset_type, int quantity) {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("INSERT INTO financial_asset(a_id, a_client_id, a_product_id, "
                    + "a_type, a_status, a_quantity, a_income, a_purchase_time) "
                    + "VALUES (?,?,?,?,?,?,?,?);");
            pstat.setInt(1, asset_id);
            pstat.setInt(2, client_id);
            pstat.setInt(3, product_id);
            pstat.setInt(4, asset_type);
            pstat.setString(5, "可用");
            pstat.setInt(6, quantity);
            pstat.setInt(7, 0);
            pstat.setDate(8, new java.sql.Date(new java.util.Date().getTime()));
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

    public boolean deleteAssetRecordById(int asset_id) {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("DELETE from financial_asset where a_id = ?");
            pstat.setInt(1, asset_id);
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

    public int countOfAssetRecords(int clientId) {
        PreparedStatement pstat = null;
        ResultSet rs = null;
        int recordsCount = Const.INVALID;

        try {
            pstat = conn.prepareStatement("select count(*) as RECORDSCOUNT from financial_asset where a_client_id = ?");
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
}
