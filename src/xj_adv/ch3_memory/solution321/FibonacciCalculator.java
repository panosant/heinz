package xj_adv.ch3_memory.solution321;

import java.math.*;

public class FibonacciCalculator {
    public String f(int n) {
        if (n < 0)
            throw new IllegalArgumentException("n=" + n);
        if (n == 0)
            return "0";
        if (n == 1)
            return "1";
        BigInteger b1 = BigInteger.ZERO;
        BigInteger b2 = BigInteger.ONE;
        for (int i = 1; i < n; i++) {
            BigInteger temp = b2;
            b2 = b1.add(b2);
            b1 = temp;
        }
        return b2.toString();
    }
}
