package J2_Lab1_Karpenko.taskfourth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DemoPassword {
    public static void main(String[] args) {
        String password = "DDD123qQ_1";
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[_\\-*]?)(?!.*#).{4,}$";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);

        if(m.matches()){
            System.out.println("\"" + password + "\" - відповідає вимогам");
        }else{
            System.out.println("\"" + password + "\" - не відповідає вимогам");
        }
    }
}