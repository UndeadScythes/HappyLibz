package utilities;

/**
 * For manipulating arrays.
 * @author UndeadScythes
 */
public class ArrayUtils {
    public static int[] reverse(final int[] array) {
        int arrayLen = array.length;
        int[] newArray = new int[arrayLen];
        for(int i = 0; i < arrayLen; i++) {
            newArray[arrayLen - 1 - i] = array[i];
        }
        return newArray;
    }
}
