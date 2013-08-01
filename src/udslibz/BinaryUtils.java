package udslibz;



import java.util.*;

/**
 * Binary tools.
 * @author UndeadScythes
 */
public class BinaryUtils {
    public static String toString(final int value, final int length) {
        String string = Integer.toBinaryString(value);
        while(string.length() < length) {
            string = "0".concat(string);
        }
        return string;
    }

    public static int toDecimal(final int array[]) {
        int exp = 1;
        int decimal = 0;
        for(int i = 0; i < array.length; i++) {
            if(array[i] == 1) {
                decimal += exp;
            }
            exp <<= 1;
        }
        return decimal;
    }

    public static int toDecimal(final List<Integer> array) {
        int exp = 1;
        int decimal = 0;
        for(int i = 0; i < array.size(); i++) {
            if(array.get(i) == 1) {
                decimal += exp;
            }
            exp <<= 1;
        }
        return decimal;
    }

    public static int reverse(final int value, final int length) {
        int reversed = 0;
        for(int i = 0; i < length; i++) {
            if(((1 << i) & value) != 0) {
                reversed |= (1 << length - i - 1);
            }
        }
        return reversed;
    }
}
