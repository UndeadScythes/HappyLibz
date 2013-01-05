package udslibz.prng;

import udslibz.algebra.Polynomial;

/**
 * A Galois LFSR, currently only binary.
 * @author UndeadScythes
 */
public class GaloisLFSR {
    private final transient int feedback;
    private final transient int maxTap;
    private transient int state;

    public GaloisLFSR(final int degree, final Polynomial feedback, final int seed) {
        this.feedback = feedback.toInt();
        maxTap = 1 << degree;
        state = seed;
    }

    public final void clock() {
        state <<= 1;
        state ^= ((maxTap & state) == 0) ? 0 : feedback;
    }

    public final int getState() {
        return state;
    }

    public final int getBit(final int bit) {
        return ((state & (1 << bit)) == 0) ? 0 : 1;
    }

    public final void reset(final int newSeed) {
        state = newSeed;
    }
}
