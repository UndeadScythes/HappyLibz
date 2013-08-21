package com.undeadscythes.udslibz;



import java.util.*;

/**
 * Handle factors.
 * @author UndeadScythes
 */
public class IntegerUtils {
    public static int uniques(final int[] ints) {
        final Set<Integer> set = new HashSet<Integer>();
        for(int i = 0; i < ints.length; i++) {
            set.add(ints[i]);
        }
        return set.size();
    }

    public static List<Integer> getPrimeFactors(final int number) {
        int test = number;
        final List<Integer> factors = new ArrayList<Integer>();
        for(int i = 2; i <= test / i; i++) {
            while(test % i == 0) {
                factors.add(i);
                test /= i;
            }
        }
        if(test > 1) {
            factors.add(test);
        }
        return factors;
    }

    public static List<Integer> getUniquePrimeFactors(final int number) {
        int test = number;
        final List<Integer> factors = new ArrayList<Integer>();
        for(int i = 2; i <= test / i; i++) {
            while(test % i == 0) {
                if(!factors.contains(i)) {
                    factors.add(i);
                }
                test /= i;
            }
        }
        if(test > 1) {
            factors.add(test);
        }
        return factors;
    }

    public static List<Integer> getFactors(final int number) {
        final List<Integer> factors = new ArrayList<Integer>();
        int inc = 1;
        if(number % 2 != 0) {
            inc = 2;
        } else {
            factors.add(2);
        }
        for(int i = 3; i <= number / 2; i += inc) {
            if(number % i == 0) {
                factors.add(i);
            }
        }
        return factors;
    }
}
