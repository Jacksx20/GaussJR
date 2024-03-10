package expt.db.finance.dao;

import java.sql.Connection;
import java.sql.SQLException;
import expt.db.finance.Const;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Client {
    private Connection conn;

    public Client(Connection connection) {
        conn = connection;
    }

    public ResultSet queryClientList() {
        PreparedStatement pstat = null;
        ResultSet rs = null;

        try {
            pstat = conn.prepareStatement("select c_id as 客户ID, c_name as 姓名, "
                    + "c_id_card as 身份证号, c_phone as 电话号码, c_mail as 电子邮件地址 "
                    + "from client");
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

    public ResultSet queryClientById(int id) {
        PreparedStatement pstat = null;
        ResultSet rs = null;

        try {
            pstat = conn.prepareStatement("select c_id as 客户ID, c_name as 姓名, "
                    + "c_id_card as 身份证号, c_phone as 电话号码, c_mail as 电子邮件地址 "
                    + "from client where c_id = ?");
            pstat.setInt(1, id);
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

    public boolean insertClient(int id, String name, String id_card, String phone_number, String email) {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("INSERT INTO client(c_id,c_name,c_id_card,c_phone,c_mail) "
                    + "VALUES (?,?,?,?,?);");
            pstat.setInt(1, id);
            pstat.setString(2, name);
            pstat.setString(3, id_card);
            pstat.setString(4, phone_number);
            pstat.setString(5, email);
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

    public boolean updateClient(int id, String name, String phone_number, String email) {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("UPDATE client set c_name=?, c_phone=?, c_mail=? where c_id=?");
            pstat.setString(1, name);
            pstat.setString(2, phone_number);
            pstat.setString(3, email);
            pstat.setInt(4, id);
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

    public boolean deleteClient(int id) {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement("DELETE from client where c_id = ?");
            pstat.setInt(1, id);
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
