package expt.db.finance;

import java.util.Scanner;

public class Utils {
    public static int getInt(String hint) {
        int target;
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            System.out.print(hint);
            
            try {
                target = sc.nextInt();
                break;
            } catch (Exception ex) {
                System.out.println("* * * * * *    输入有误，请重新输入！   * * * * * *");
                sc.nextLine();
            }
            
        }
        
        return target;
    }
    
    public static String getString(String hint) {
        String target;
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            System.out.print(hint);
            
            try {
                target = sc.nextLine();
                break;
            } catch (Exception ex) {
                System.out.println("* * * * * *    输入有误，请重新输入！   * * * * * *");
                sc.nextLine();
            }
        }
        
        return target;
    }
}


