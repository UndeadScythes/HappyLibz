package algebra;

import java.util.*;
import prng.*;

/**
 * A polynomial, currently only binary.
 * @author UndeadScythes
 */
public class Polynomial {
    private static final int MAX_DEG = 63;

    private final transient long representation;
    private transient long order = -1;
    private transient int degree = -1;
    private transient int weight = -1;

    public Polynomial(final long representation) {
        this.representation = representation;
    }

    public final long getLong() {
        return representation;
    }

    public final long getOrder() {
        if(order == -1) {
            final GaloisLFSR lfsr = new GaloisLFSR(this.getDegree(), this, 1);
            lfsr.clock();
            order = 1;
            while(lfsr.getState() != 1) {
                order++;
                lfsr.clock();
            }
        }
        return order;
    }

    public final boolean isOrderAtLeast(final long test) {
        final GaloisLFSR lfsr = new GaloisLFSR(this.getDegree(), this, 1);
        lfsr.clock();
        order = 1;
        while(lfsr.getState() != 1) {
            order++;
            if(order >= test) {
                order = -1;
                return true;
            }
            lfsr.clock();
        }
        return false;
    }

    public final int getDegree() {
        if(degree == -1) {
            degree = 63 - Long.numberOfLeadingZeros(representation);
        }
        return degree;
    }

    public final int getWeight() {
        if(weight == -1) {
            weight = Long.bitCount(representation);
        }
        return weight;
    }

    public final boolean isMonomial() {
        return (1 & representation) == 1;
    }

    public static Polynomial getPrimitive(final int degree, final long start) {
        final long requiredOrder = (long)(Math.pow(2, degree) - 1);
        long test = start;
        Polynomial polynomial = new Polynomial(test);
        while(polynomial.getWeight() % 2 == 0 || !polynomial.isMonomial() || polynomial.getDegree() < degree || polynomial.getOrder() != requiredOrder) {
            test++;
            polynomial = new Polynomial(test);
        }
        return polynomial;
    }
}
