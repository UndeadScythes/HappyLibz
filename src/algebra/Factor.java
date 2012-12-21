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
        for (long i = 2; i <= test; i++) {
            while (test % i == 0) {
                factors.add(i);
                test /= i;
            }
        }
        return factors;
    }
}
