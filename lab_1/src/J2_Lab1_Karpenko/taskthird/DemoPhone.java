package J2_Lab1_Karpenko.taskthird;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DemoPhone {
    public static void main(String[] args) {
        String phone = "+38(068)1234567";
        String regex = "^\\+?38\\(0(67|68|9[6-8])\\)(-| )?\\d{3}(-| )?\\d{2}(-| )?\\d{2}$";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);

        if(m.matches()){
            System.out.println("\"" + phone + "\" - є номером телефону оператора Київстар");
        }else{
            System.out.println("\"" + phone + "\" - не є номером телефону оператора Київстар");
        }
    }
}