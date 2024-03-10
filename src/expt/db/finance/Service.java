package expt.db.finance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import expt.db.finance.dao.*;

public class Service {
    private Connection conn;
    private Client client;
    private BankCard bankCard;
    private FinancialProduct fp;
    private FinancialAsset fa;
    private CardAsset ca;

    public Service() {
        conn = openConnection();

        client = new Client(conn);
        bankCard = new BankCard(conn);
        fp = new FinancialProduct(conn);
        fa = new FinancialAsset(conn);
        ca = new CardAsset(conn);
    }

    public Connection openConnection() {
        return DBUtils.getConnect();
    }

    public void closeConnection() throws SQLException {
        conn.close();
    }

    public void showClients() {
        System.out.println("* * * * * * * * *    客 户 列 表     * * * * * * * * *");
        DBUtils.printAllRecords(client.queryClientList());
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }

    public void showClient() {
        int clientId = Utils.getInt(Const.PREFIX_LEVEL_SEC + "请输入需要查询信息的客户的ID: ");

        System.out.println("* * * * * * * * *    基 础 信 息     * * * * * * * * *");
        DBUtils.printOneRecord(client.queryClientById(clientId));
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }

    public void showClientWithCardsAndAssets() {
        int clientId = Utils.getInt(Const.PREFIX_LEVEL_SEC + "请输入需要查询信息的客户的ID: ");

        System.out.println("* * * * * * * * * *   基 本 信 息    * * * * * * * * *");
        DBUtils.printOneRecord(client.queryClientById(clientId));
        System.out.println("- - - - - - - - - -   开 卡 信 息    - - - - - - - - -");
        DBUtils.printAllRecords(bankCard.queryBankCardByClientId(clientId));
        System.out.println("- - - - - - - - - -   理财资产信息    - - - - - - - - -");
        DBUtils.printAllRecords(fa.queryAssetByClientId(clientId));
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }

    public void setUpAnAccount() {
        System.out.println(Const.PREFIX_LEVEL_FIR + "已进入开户流程，请按提示要求输入客户相关信息，并按回车");

        String name = Utils.getString(Const.PREFIX_LEVEL_SEC + "请输入客户姓名: ");
        String idCard = Utils.getString(Const.PREFIX_LEVEL_SEC + "请输入客户身份证号: ");
        String phoneNumber = Utils.getString(Const.PREFIX_LEVEL_SEC + "请输入客户手机号码: ");
        String email = Utils.getString(Const.PREFIX_LEVEL_SEC + "请输入客户电子邮箱地址: ");
        int clientId = Utils.getInt(Const.PREFIX_LEVEL_SEC + "请输入客户ID: ");

        boolean ret = client.insertClient(clientId, name, idCard, phoneNumber, email);
        if (ret == Const.SUCCEED) {
            System.out.println(Const.PREFIX_LEVEL_SEC + "客户信息已录入成功！");
            return;
        }

        System.out.println(Const.PREFIX_LEVEL_SEC + "客户信息已录入失败!");
    }

    public void modifyAnAccount() {
        System.out.println(Const.PREFIX_LEVEL_FIR + "已进入更新客户信息流程，请按提示要求输入相关信息，并按回车");

        int clientId = Utils.getInt(Const.PREFIX_LEVEL_SEC + "请输入需要修改信息的客户的ID: ");
        String name = Utils.getString(Const.PREFIX_LEVEL_SEC + "请输入客户更新后姓名: ");
        String phoneNumber = Utils.getString(Const.PREFIX_LEVEL_SEC + "请输入客户更新后手机号码: ");
        String email = Utils.getString(Const.PREFIX_LEVEL_SEC + "请输入客户更新后电子邮箱地址: ");

        boolean ret = client.updateClient(clientId, name, phoneNumber, email);
        if (ret == Const.SUCCEED) {
            System.out.println(Const.PREFIX_LEVEL_SEC + "客户础信信息更新成功！");
            return;
        }

        System.out.println(Const.PREFIX_LEVEL_SEC + "客户信息已录入失败!");
    }

    public void closeAnAccount() {
        System.out.println(Const.PREFIX_LEVEL_FIR + "已进入注销客户信息流程，请按提示要求输入相关信息，并按回车");

        int clientId = Utils.getInt(Const.PREFIX_LEVEL_SEC + "请输入需要注销的客户的ID: ");
        if (!isSafeToCloseAccount(clientId)) {
            return;
        }

        boolean ret = client.deleteClient(clientId);
        if (ret == Const.SUCCEED) {
            System.out.println(Const.PREFIX_LEVEL_SEC + "销户成功!");
            return;
        }

        System.out.println(Const.PREFIX_LEVEL_SEC + "销户失败!");
    }

    public void setUpAnCard() {
        System.out.println(Const.PREFIX_LEVEL_FIR + "已进入开卡流程，请按提示要求输入相关信息，并按回车");

        int clientId = Utils.getInt(Const.PREFIX_LEVEL_SEC + "请输入待开卡的客户的ID: ");
        String cardNumber = Utils.getString(Const.PREFIX_LEVEL_SEC + "请输入卡号: ");

        System.out.println("开卡中,默认开卡类型为储蓄卡");
        String cardType = "储蓄卡";

        boolean ret = bankCard.insertBankCard(cardNumber, cardType, clientId);
        if (ret == Const.SUCCEED) {
            ca.insertCardAsset(cardNumber);
            System.out.println(Const.PREFIX_LEVEL_SEC + "开卡成功!");
            //
            return;
        }

        System.out.println(Const.PREFIX_LEVEL_SEC + "开卡失败!");
    }

    public void closeAnCard() {
        System.out.println(Const.PREFIX_LEVEL_FIR + "已进入销卡流程，请按提示要求输入相关信息，并按回车");

        String cardNumber = Utils.getString(Const.PREFIX_LEVEL_SEC + "请输入卡号: ");
        boolean ret = bankCard.deleteBankCardByCardNumber(cardNumber);
        if (ret == Const.SUCCEED) {
            ca.deleteCardAsset(cardNumber);
            System.out.println(Const.PREFIX_LEVEL_SEC + "销卡成功!");
            return;
        }

        System.out.println(Const.PREFIX_LEVEL_SEC + "销卡失败!");
    }

    public boolean isSafeToCloseAccount(int clientId) {
        int countCardsUnderCurrentClient = bankCard.countOfBankCards(clientId);
        int countAssetRecordsUnderCurrentClient = fa.countOfAssetRecords(clientId);

        if (countCardsUnderCurrentClient == Const.INVALID ||
                countAssetRecordsUnderCurrentClient == Const.INVALID) {
            System.out.println(Const.PREFIX_LEVEL_SEC + "当前信息查询异常，暂不能进行销户操作！");
            return false;
        }

        if (countCardsUnderCurrentClient > 0) {
            System.out.println(Const.PREFIX_LEVEL_SEC + "当前客户存在" + countCardsUnderCurrentClient +
                    "张银行卡未注销，不能注销账户！");
            return false;
        }

        if (countAssetRecordsUnderCurrentClient > 0) {
            System.out.println(Const.PREFIX_LEVEL_SEC + "当前客户存在" + countCardsUnderCurrentClient +
                    "笔金融产品未赎回，不能注销账户！");
            return false;
        }

        return true;
    }

    public void showFinancialProducts() {
        System.out.println("* * * * * * * * *     理 财 产 品    * * * * * * * * *");
        DBUtils.printAllRecords(fp.queryProductList());
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }

    public void buyAsset() {
        System.out.println(Const.PREFIX_LEVEL_FIR + "已进入金融产品购买流程，请按提示要求输入相关信息，并按回车");

        int clientId = Utils.getInt(Const.PREFIX_LEVEL_SEC + "请输入客户ID: ");
        int productId = Utils.getInt(Const.PREFIX_LEVEL_SEC + "请输入金融产品编号: ");
        int assetType = 1;
        int quantity = Utils.getInt(Const.PREFIX_LEVEL_SEC + "请输入申购金额: ");
        int assetId = Utils.getInt(Const.PREFIX_LEVEL_SEC + "请输入资产编号: ");

        boolean ret = fa.insertAssetRecord(assetId, clientId, productId, assetType, quantity);
        if (ret == Const.SUCCEED) {
            System.out.println(Const.PREFIX_LEVEL_SEC + "金融产品购买成功！");
            return;
        }

        System.out.println(Const.PREFIX_LEVEL_SEC + "金融产品购买失败!");
    }

    public void redeemAsset() {
        System.out.println(Const.PREFIX_LEVEL_FIR + "已进入金融产品赎回流程，请按提示要求输入相关信息，并按回车");

        int assetId = Utils.getInt(Const.PREFIX_LEVEL_SEC + "请输入资产编号: ");

        boolean ret = fa.deleteAssetRecordById(assetId);
        if (ret == Const.SUCCEED) {
            System.out.println(Const.PREFIX_LEVEL_SEC + "赎回成功!");
            return;
        }
        System.out.println(Const.PREFIX_LEVEL_SEC + "赎回失败!");
    }

    public void showCardAsset() {
        int clientId = Utils.getInt(Const.PREFIX_LEVEL_SEC + "请输入需要查询信息的客户的ID: ");

        System.out.println("* * * * * * *    拥 有 的 所 有 卡     * * * * * * * * ");
        DBUtils.printAllRecords(ca.queryCardAssetList(clientId));
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }

    public void doDepositAndWithdrawal() {
        int clientId = Utils.getInt(Const.PREFIX_LEVEL_SEC + "请输入需要查询信息的客户的ID: ");
        System.out.println("* * * * * * *    拥 有 的 所 有 卡     * * * * * * * * ");
        ResultSet rs = ca.queryCardAssetList(clientId);
        if (rs == null) {
            System.out.println("当前用户未持有任何卡！");
        } else {
            DBUtils.printAllRecords(rs);
            String cardNum = Utils.getString(Const.PREFIX_LEVEL_SEC + "请输入需要进行的操作的银行卡号: ");
            int action = Utils.getInt(Const.PREFIX_LEVEL_SEC + "请输入需要进行的操作（1.存款 2.取款）: ");
            int money = Utils.getInt(Const.PREFIX_LEVEL_SEC + "请输入金额: ");
            boolean result = ca.queryDepositAndWithdrawal(cardNum, action, money);
            if (result == Const.SUCCEED) {
                System.out.println(Const.PREFIX_LEVEL_SEC + "存/取款操作成功！");
                return;
            }
            System.out.println(Const.PREFIX_LEVEL_SEC + "存/取款操作失败!");

        }
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }
}
