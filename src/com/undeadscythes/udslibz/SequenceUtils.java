package com.undeadscythes.udslibz;

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

    public static Sequence generateFLFSR(final Polynomial poly, final int length) {
        final Sequence seq = new Sequence(length);
        final int wtX = poly.getWeight() - 1;
        final int[] taps = new int[wtX];
        int pos = 0;
        final int deg = poly.getDegree();
        for(int i = deg; i > 0; i--) {
            if(poly.getCoeff(i) != 0) {
                taps[pos] = deg - i;
                pos++;
            }
        }
        int lfsr = 1;
        for(int i = 0; i < length; i++) {
            seq.setElement(i, lfsr & 1);
            int bit = 0;
            for(int j = 0; j < wtX; j++) {
                bit ^= (lfsr >> taps[j]) & 1;
            }
            lfsr = (lfsr >> 1) | (bit << deg - 1);
        }
        return seq;
    }

    public static Sequence generateGLFSR(final Polynomial poly, final int length) {
        final Sequence seq = new Sequence(length);
        final int taps = (poly.toInt() >> 1);
        int lfsr = 1;
        for(int i = 0; i < length; i++) {
            lfsr = (lfsr >> 1) ^ (-(1 & lfsr) & taps);
            seq.setElement(i, lfsr & 1);
        }
        return seq;
    }

    public static Sequence generateGLFSR(final Polynomial poly, final Sequence[] seqs, final int length) {
        final Sequence seq = new Sequence(length);
        final int seqCount = seqs.length;
        final int deg = poly.getDegree();
        int[][] taps = new int[seqCount][deg];
        for(int i = 0; i < seqCount; i++) {
            int pos = 0;
            for(int j = 0; j < deg; j++) {
                if(seqs[i].getElement(j) != 0) {
                    taps[i][pos] = deg - j - 1;
                    pos++;
                }
            }
            for(int j = pos; j < deg; j++) {
                taps[i][j] = -1;
            }
        }

        final int gTaps = (poly.toInt() >> 1);
        int lfsr = 1;
        for(int i = 0; i < length; i = i) {
            lfsr = (lfsr >> 1) ^ (-(1 & lfsr) & gTaps);
            for(int j = 0; j < seqCount; j++) {
                if(i == length) {
                    break;
                }
                int pos = 0;
                int bit = 0;
                while(pos < deg && taps[j][pos] != -1) {
                    bit ^= (lfsr >> taps[j][pos]) & 1;
                    pos++;
                }
                seq.setElement(i, bit);
                i++;
            }
        }
        return seq;
    }

    private SequenceUtils() {}
}
