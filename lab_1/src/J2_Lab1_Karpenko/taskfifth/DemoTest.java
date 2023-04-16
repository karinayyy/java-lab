package J2_Lab1_Karpenko.taskfifth;

public class DemoTest {
    public static void main(String[] args) {
        String text = "qjo234nlekf3455nklm34iop90";

        if(text.length() >= 20) {
            String[] substr = text.split("\\d+");

            for(String str : substr) {
                System.out.println(str);
            }
        } else {
            System.out.println("Рядок має бути довжиною більше 20 символів");
        }
    }
}