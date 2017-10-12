package xj_adv.ch3_memory.solution321b;

import java.math.*;
import java.util.*;

public class FibonacciCalculator {
    public String f(int n) {
        if (n < 0)
            throw new IllegalArgumentException("n=" + n);
        Map<Integer, BigInteger> cache = new HashMap<>();
        cache.put(0, BigInteger.ZERO);
        cache.put(1, BigInteger.ONE);
        return f(n, cache).toString();
    }

    private BigInteger f(int n, Map<Integer, BigInteger> cache) {
        return cache.computeIfAbsent(n, key ->
                f(n - 1, cache).add(f(n - 2, cache)));
    }
}
