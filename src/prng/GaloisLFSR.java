package prng;

import algebra.*;

/**
 * A Galois LFSR, currently only binary.
 * @author UndeadScythes
 */
public class GaloisLFSR {
    private final transient long feedback;
    private final transient long maxTap;
    private transient long state;

    public GaloisLFSR(final int degree, final Polynomial feedback, final long seed) {
        this.feedback = feedback.getLong();
        maxTap = (long)Math.pow(2, degree);
        state = seed;
    }

    public final void clock() {
        state *= 2;
        state ^= (((maxTap & state) == 0) ? 0 : feedback);
    }

    public final long getState() {
        return state;
    }

    public final int getBit(final int bit) {
        return ((state & (1 << bit)) == 0) ? 0 : 1;
    }
}
