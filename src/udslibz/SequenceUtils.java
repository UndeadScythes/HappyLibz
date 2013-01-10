package udslibz;



import java.util.*;

/**
 * Binary tools.
 * @author UndeadScythes
 */
public class SequenceUtils {
    public static String toString(final int value, final int length) {
        String string = Integer.toBinaryString(value);
        while(string.length() < length) {
            string = "0".concat(string);
        }
        return StringUtils.reverse(string);
    }
}
