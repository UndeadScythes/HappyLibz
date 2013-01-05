package udslibz.algebra;

import java.util.*;

/**
 * Handle factors.
 * @author UndeadScythes
 */
public class Factor {
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

    public static List<Integer> getFactors(final int number) {
        final List<Integer> factors = new ArrayList<Integer>();
        int inc = 1;
        if(number % 2 != 0) {
            inc = 2;
        }
        for(int i = 1; i <= number / 2; i += inc) {
            if(number % i == 0) {
                factors.add(i);
            }
        }
        return factors;
    }
}
