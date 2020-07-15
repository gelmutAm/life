package com.epam.life.common;

import java.util.*;

public class GameUtils {
    public static Collection<Integer> getDivisors(int divident) {
        Vector<Integer> divisors = new Vector<>();
        for (int i = 2; i < divident; i++) {
            if (divident % i == 0) {
                divisors.add(i);
            }
        }

        Collections.sort(divisors);

        return divisors;
    }
}
