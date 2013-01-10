package udslibz;

/**
 *
 * @author UndeadScythes
 */
public class StringUtils {
    public static String reverse(final String string) {
        String reversed = "";
        for(int i = 0; i < string.length(); i++) {
            reversed = String.valueOf(string.charAt(i)).concat(reversed);
        }
        return reversed;
    }
}
