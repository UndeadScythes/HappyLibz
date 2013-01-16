package udslibz;

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

    public void leftShift(final int shift) {
        for(int i = 0; i < length - shift; i++) {
            setElement(i, getElement(i + shift));
        }
        for(int i = length - shift; i < length; i++) {
            setElement(i, 0);
        }
    }

    public void rightShift(final int shift) {
        for(int i = length - 1; i > shift - 1; i--) {
            setElement(i, getElement(i - shift));
        }
        for(int i = 0; i < shift; i++) {
            setElement(i, 0);
        }
    }

    @Override
    public String toString() {
        String temp = "";
        for(int i = 0; i < sequence.length - 1; i++) {
            temp = temp.concat(SequenceUtils.toString(sequence[i], PART_LEN));
        }
        temp = temp.concat(SequenceUtils.toString(sequence[sequence.length - 1], length % PART_LEN));
        return temp;
    }

    public Sequence getSubSequence(final int start, final int end) {
        final int newLength = end - start + 1;
        final Sequence subSeq = new Sequence(newLength);
        for(int i = 0; i < newLength; i++) {
            subSeq.setElement(i, getElement(i + start));
        }
        return subSeq;
    }

    public boolean equalTo(final Sequence seq) {
        if(length != seq.getLength()) {
            return false;
        }
        for(int i = 0; i < length; i++) {
            if(getElement(i) != seq.getElement(i)) {
                return false;
            }
        }
        return true;
    }

    public static Sequence fromTrace(final Polynomial fieldElement, final Polynomial charPoly, final int length) {
        final int n = charPoly.getDegree();
        final Sequence seq = new Sequence(length);
        for(int i = 0; i < length; i++) {
            final Polynomial element = new Polynomial(0);
            for(int k = 0; k < n; k++) {
                final Polynomial part1 = PolynomialUtils.toPowerMod(fieldElement, (int)Math.pow(2, k), charPoly);
                final Polynomial part2 = PolynomialUtils.toPowerMod(new Polynomial(2), (int)Math.pow(2, k) * i, charPoly);
                element.add(PolynomialUtils.productMod(part1, part2, charPoly));
            }
            seq.setElement(i, element.getCoeff(0));
        }
        return seq;
    }

    public boolean contains(final Sequence seq) {
        final int seqLength = seq.getLength();
        if(seqLength > length) {
            return false;
        }
        for(int i = 0; i <= length - seqLength; i++) {
            if(getSubSequence(i, i + seqLength).equalTo(seq)) {
                return true;
            }
        }
        return false;
    }

    public int find(final Sequence seq) {
        final int seqLength = seq.getLength();
        if(seqLength > length) {
            return -1;
        }
        for(int i = 0; i <= length - seqLength; i++) {
            if(getSubSequence(i, i + seqLength - 1).equalTo(seq)) {
                return i;
            }
        }
        return -1;
    }

    public Polynomial getMinimal() {
        return PolynomialUtils.berlekampMassey(this);
    }
}
