package arithmetic;

/**
 * Binary tools.
 * @author UndeadScythes
 */
public class Binary {
    public static String toString(final long value, final int length) {
        String string = Long.toBinaryString(value);
        while(string.length() < length) {
            string = "0".concat(string);
        }
        return string;
    }
}
