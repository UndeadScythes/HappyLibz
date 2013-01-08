package numbertheory;

import udslibz.utilities.BinaryUtils;

/**
 * A sequence of values, currently only binary.
 * @author UndeadScythes
 */
public class Sequence {
    private final static int PART_LEN = 32;

    private final int[] sequence;
    private final int length;

    public Sequence(final int length, final int[] sequence) {
        this.length = length;
        this.sequence = sequence.clone();
    }

    public Sequence(final int length) {
        this.length = length;
        this.sequence = new int[(length / PART_LEN) + 1];
    }

    public int getElement(final int index) {
        if(index > length) {
            return 0;
        }
        return (sequence[index / PART_LEN] & (1 << (index % PART_LEN))) == 0 ? 0 : 1;
    }

    public int getLength() {
        return length;
    }

    public void setElement(final int index, final int element) {
        if(index > length) {
            return;
        }
        if(element == 1) {
            sequence[index / PART_LEN] |= (1 << (index % PART_LEN));
        } else {
            sequence[index / PART_LEN] &= ~(1 << (index % PART_LEN));
        }
    }

    @Override
    public String toString() {
        String temp = "";
        for(int i = 0; i < sequence.length - 1; i++) {
            temp = temp.concat(BinaryUtils.toString(sequence[i], PART_LEN));
        }
        temp = temp.concat(BinaryUtils.toString(sequence[sequence.length - 1], length % PART_LEN));
        return temp;
    }

    public Sequence getSubSequence(final int start, final int end) {
        final int newLength = end - start;
        final Sequence subSeq = new Sequence(newLength);
        for(int i = 0; i < newLength; i++) {
            subSeq.setElement(i, getElement(i + start));
        }
        return subSeq;
    }
}
