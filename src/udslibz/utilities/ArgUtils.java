package udslibz.utilities;

/**
 * Utilities for fetching values from argument lists.
 * @author UndeadScythes
 */
public final class ArgUtils {
    public static int getInt(final String[] args, final String tag, final int def) {
        for(int i = 0; i < args.length; i++) {
            if(args[i].equals(tag)) {
                try {
                    return Integer.parseInt(args[i + 1]);
                } catch(NumberFormatException ex) {
                    System.err.println("Number required for argument " + tag + ".");
                } catch(ArrayIndexOutOfBoundsException ex) {
                    System.err.println("Number required for argument " + tag + ".");
                }
            }
        }
        return def;
    }

    private ArgUtils() {}
}
