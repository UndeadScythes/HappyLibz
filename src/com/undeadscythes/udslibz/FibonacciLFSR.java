package com.undeadscythes.udslibz;

/**
 *
 * @author UndeadScythes
 */
public class FibonacciLFSR {
    private final transient int feedback;
    private final transient int lastReg;
    private transient int state;

    public FibonacciLFSR(final int degree, final Polynomial feedback, final int seed) {
        this.feedback = feedback.toInt();
        lastReg = degree - 1;
        state = seed;
    }

    public final void clock() {
        final int bit = Integer.bitCount(state & feedback) % 2;
        state = (state >> 1) | (bit << lastReg);
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

    public final int getOutput() {
        return getBit(0);
    }
}
