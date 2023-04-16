package J2_Lab1_Karpenko.tasksixth;

import java.math.BigInteger;
import java.util.Random;

public class DemoBigInt {
    public static void main(String[] args) {
        Random random = new Random();
        BigInteger ranNum = new BigInteger(100, random);
        BigInteger exp = new BigInteger("5");
        System.out.println("Число: " + ranNum + " pow (exp) " + exp);

        BigInteger res1 = ranNum.pow(exp.intValue());
        System.out.println("З використанням функції pow: " + res1);

        BigInteger res2 = BigInteger.ONE;
        for (int i = 0; i < exp.intValue(); i++) {
            res2 = res2.multiply(ranNum);
        }
        System.out.println("Множення довгих цілих: " + res2);
    }
}
