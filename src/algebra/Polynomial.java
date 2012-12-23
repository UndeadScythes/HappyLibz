package algebra;

import java.util.*;
import prng.*;

/**
 * A polynomial, currently only binary.
 * @author UndeadScythes
 */
public class Polynomial {
    private static final int MAX_DEG = 63;

    private transient int representation;
    private transient int order = -1;
    private transient int degree = -1;
    private transient int weight = -1;

    public Polynomial(final int representation) {
        this.representation = representation;
        degree = 63 - Long.numberOfLeadingZeros(representation);
        weight = Long.bitCount(representation);
    }

    public void refresh() {
        degree = 63 - Long.numberOfLeadingZeros(representation);
        weight = Long.bitCount(representation);
    }

    public final int toInt() {
        return representation;
    }

    public final int getOrder() {
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

    public final boolean isOrderAtLeast(final int test) {
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
        return degree;
    }

    public final int getWeight() {
        return weight;
    }

    public final boolean isMonomial() {
        return (1 & representation) == 1;
    }

    public final boolean isIrreducible() {
        int maxTest = (1 << degree) - 1;
        for(int i = 1; i < maxTest; i++) {
            for(int j = i; j < maxTest; j++) {
                Polynomial a = new Polynomial(i);
                Polynomial b = new Polynomial(j);
                if(a.getDegree() + b.getDegree() != degree) {
                    continue;
                }
                if(Polynomial.product(a, b).toInt() == representation) {
                    return false;
                }
            }
        }
        return true;
    }

    public final boolean isPrimitive() {
        return getOrder() == (1 << degree) - 1;
    }

    public final int getCoeff(final int coeff) {
        return (((1 << coeff) & representation) != 0) ? 1 : 0;
    }

    public final void add(final Polynomial add) {
        representation ^= add.toInt();
        refresh();
    }

    public static Polynomial product(final Polynomial polyA, final Polynomial polyB) {
        int degA = polyA.getDegree();
        Polynomial polyC = new Polynomial(0);
        for(int i = 0; i <= degA; i++) {
            if(polyA.getCoeff(i) == 1) {
                polyC.add(Polynomial.exponent(polyB, i));
            }
        }
        return polyC;
    }

    public static Polynomial exponent(final Polynomial poly, final int e) {
        return new Polynomial(poly.toInt() << e);
    }

    public static Polynomial getPrimitive(final int degree, final int start) {
        final int requiredOrder = (1 << degree) - 1;
        int test = start;
        Polynomial polynomial = new Polynomial(test);
        while(polynomial.getWeight() % 2 == 0 || !polynomial.isMonomial() || polynomial.getDegree() < degree || polynomial.getOrder() != requiredOrder) {
            test++;
            polynomial = new Polynomial(test);
            if(polynomial.getDegree() > degree) {
                return null;
            }
        }
        return polynomial;
    }

    public static Polynomial getIrreducible(final int degree, final int start) {
        int test = start;
        Polynomial polynomial = new Polynomial(test);
        while(polynomial.getWeight() % 2 == 0 || !polynomial.isMonomial() || polynomial.getDegree() < degree || !polynomial.isIrreducible()) {
            test++;
            polynomial = new Polynomial(test);
            if(polynomial.getDegree() > degree) {
                return null;
            }
        }
        return polynomial;
    }

    public static Polynomial getStrictIrreducible(final int degree, final int start) {
        int test = start;
        Polynomial polynomial = new Polynomial(test);
        while(polynomial.getWeight() % 2 == 0 || !polynomial.isMonomial() || polynomial.getDegree() < degree || !polynomial.isIrreducible() || polynomial.isPrimitive()) {
            test++;
            polynomial = new Polynomial(test);
            if(polynomial.getDegree() > degree) {
                return null;
            }
        }
        return polynomial;
    }
}
