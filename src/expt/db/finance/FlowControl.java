package expt.db.finance;

import java.util.Scanner;

public class FlowControl {
    Service service;

    public FlowControl() {
        service = new Service();
    }

    public void startFlow() {
        int choices;
        do {
            System.out.print("\n"
                    + "* * * * * * * * *   操作列表  * * * * * * * * *\n"
                    + "     -------------------------------------\n"
                    + "  1) 客户列表查询 \n"
                    + "  2) 客户详细信息查询 \n"
                    + "  3) 客户基础信息修改 \n"
                    + "  4) 客户开户 \n"
                    + "  5) 客户开卡 \n"
                    + "  6) 客户销卡 \n"
                    + "  7) 客户销户 \n"
                    + "     -------------------------------------\n"
                    + "  8) 理财产品查询 \n"
                    + "  9) 理财产品购买  \n"
                    + " 10) 理财产品赎回 \n"
                    + "     -------------------------------------\n"
                    + "  0) 退出系统\n"
                    + "* * * * * * * * *  * * * * * * * * * * * * * *\n");

            choices = Utils.getInt(Const.PREFIX_LEVEL_FIR + "请输入需要进行的操作编号: ");

            switch (choices) {
                case 1:
                    service.showClients();
                    waitForContinue();
                    break;
                case 2:
                    service.showClientWithCardsAndAssets();
                    waitForContinue();
                    break;
                case 3:
                    service.modifyAnAccount();
                    waitForContinue();
                    break;
                case 4:
                    service.setUpAnAccount();
                    waitForContinue();
                    break;
                case 5:
                    service.setUpAnCard();
                    waitForContinue();
                    break;
                case 6:
                    service.closeAnCard();
                    waitForContinue();
                    break;
                case 7:
                    service.closeAnAccount();
                    waitForContinue();
                    break;
                case 8:
                    service.showFinancialProducts();
                    waitForContinue();
                    break;
                case 9:
                    service.buyAsset();
                    waitForContinue();
                    break;
                case 10:
                    service.redeemAsset();
                    waitForContinue();
                    break;
                case 0:
                    System.out.println(Const.PREFIX_LEVEL_FIR + "已退出!");
                    break;

                default:
                    System.out.println(Const.PREFIX_LEVEL_FIR + "当前输入信息无效!");
                    waitForContinue();
            }
        } while (choices != 0);
    }

    public void waitForContinue() {
        Scanner sc = new Scanner(System.in);
        System.out.print(Const.PREFIX_LEVEL_FIR + "请按回车继续选择...");
        sc.nextLine();
    }
}