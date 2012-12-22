package algebra;

import java.util.*;

/**
 * Handle factors.
 * @author UndeadScythes
 */
public class Factor {
    public static List<Long> getPrimeFactors(final long number) {
        long test = number;
        final List<Long> factors = new ArrayList<Long>();
        for(long i = 2; i <= test / i; i++) {
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

    public static List<Long> getFactors(final long number) {
        final List<Long> factors = new ArrayList<Long>();
        int inc = 1;
        if(number % 2 != 0) {
            inc = 2;
        }
        for(long i = 1; i <= number / 2; i += inc) {
            if(number % i == 0) {
                factors.add(i);
            }
        }
        return factors;
    }
}
