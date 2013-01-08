package udslibz.algebra;

import udslibz.prng.GaloisLFSR;
import java.util.*;
import udslibz.utilities.BinaryUtils;
import udslibz.prng.GaloisLFSR;

/**
 * A binary polynomial.
 * @author UndeadScythes
 */
public class Polynomial {
    private static final int MAX_DEG = 31;

    private transient int representation;
    private transient int order = -1;
    private transient int degree;
    private transient int weight;

    public Polynomial(final int representation) {
        this.representation = representation;
        degree = MAX_DEG - Integer.numberOfLeadingZeros(representation);
        weight = Integer.bitCount(representation);
    }

    private void refresh() {
        degree = MAX_DEG - Integer.numberOfLeadingZeros(representation);
        weight = Integer.bitCount(representation);
    }

    public boolean equalTo(final Polynomial poly) {
        return poly.toInt() == representation;
    }

    public Polynomial copy() {
        return new Polynomial(representation);
    }

    public int getDegree() {
        return degree;
    }

    public int getWeight() {
        return weight;
    }

    public int getOrder() {
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

    public int getCoeff(final int coeff) {
        return (((1 << coeff) & representation) != 0) ? 1 : 0;
    }

    public int toInt() {
        return representation;
    }

    public String toBinary() {
        return BinaryUtils.toString(representation, degree);
    }

    @Override
    public String toString() {
        return toBinary();
    }

    public String toString(final String var) {
        String binary = Integer.toBinaryString(representation);
        String temp = "";
        for(int i = 0; i < binary.length() - 2; i++) {
            if(binary.charAt(i) == '1') {
                temp = temp.concat(var.concat("^" + (degree - i) + " + "));
            }
        }
        if(binary.length() > 1 && binary.charAt(binary.length() - 2) == '1') {
            temp = temp.concat(String.valueOf(var).concat(" + "));
        }
        if(binary.length() > 0 && binary.charAt(binary.length() - 1) == '1') {
            temp = temp.concat("1 + ");
        }
        return temp.substring(0, temp.length() > 3 ? temp.length() - 3 : 0);
    }

    public boolean isMonomial() {
        return (1 & representation) == 1;
    }

    public boolean isIrreducible() {
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

    public boolean isPrimitive() {
        return getOrder() == (1 << degree) - 1;
    }

    public void add(final Polynomial add) {
        representation ^= add.toInt();
        refresh();
    }

    public void multiply(final Polynomial multiply) {
        int temp = 0;
        for(int i = 0; i <= multiply.getDegree(); i++) {
            if(multiply.getCoeff(i) == 1) {
                temp ^= (representation << i);
            }
        }
        representation = temp;
        refresh();
    }

    public void multiplyMod(final Polynomial multiply, final Polynomial mod) {
        multiply(multiply);
        modulo(mod);
    }

    public void modulo(final Polynomial mod) {
        int diff = getDegree() - mod.getDegree();
        while(diff >= 0) {
            add(Polynomial.product(mod, (1 << diff)));
            diff = getDegree() - mod.getDegree();
        }
    }

    public Polynomial nextPoly() {
        return new Polynomial(representation + 1);
    }

    public Polynomial getPrimitiveRoot() {
        final int q = ((1 << degree) - 1) / order;
        Polynomial alpha = new Polynomial(1);
        while(alpha.getDegree() < degree) {
            boolean flag = false;
            if(Polynomial.toPowerMod(alpha, q, this).toInt() == 2) {
                flag = true;
                for(int i = 1; i < (1 << degree) - 1; i++) {
                    if(Polynomial.toPowerMod(alpha, i, this).toInt() == 1) {
                        flag = false;
                        break;
                    }
                }
            }
            if(flag) {
                break;
            }
            alpha = alpha.nextPoly();
        }
        return alpha;
    }

    public List<Polynomial> getClassReps(final Polynomial alpha) {
        final int q = ((1 << degree) - 1) / order;
        final List<Polynomial> reps = new ArrayList<Polynomial>();
        Polynomial seqRep = new Polynomial(1);
        for(int classNo = 1; classNo <= q; classNo++) {
            reps.add(seqRep.copy());
            final Polynomial nextClassRep = new Polynomial(0);
            for(int i = 0; i < degree; i++) {
                if(alpha.getCoeff(i) == 1) {
                    nextClassRep.add(seqRep);
                }
                seqRep.multiplyMod(new Polynomial(2), this);
            }
            seqRep = nextClassRep;
        }
        return reps;
    }

    public static Polynomial product(final Polynomial polyA, final Polynomial polyB) {
        int temp = 0;
        for(int i = 0; i <= polyA.getDegree(); i++) {
            if(polyA.getCoeff(i) == 1) {
                temp ^= (polyB.toInt() << i);
            }
        }
        return new Polynomial(temp);
    }

    public static Polynomial product(final Polynomial polyA, final int polyB) {
        int temp = 0;
        for(int i = 0; i <= polyA.getDegree(); i++) {
            if(polyA.getCoeff(i) == 1) {
                temp ^= (polyB << i);
            }
        }
        return new Polynomial(temp);
    }

    public static Polynomial toPower(final Polynomial poly, final int e) {
        if(e == 0) return new Polynomial(1);
        if(e == 1) return new Polynomial(poly.toInt());
        return Polynomial.product(poly, Polynomial.toPower(poly, e - 1));
    }

    public static Polynomial toPowerMod(final Polynomial poly, final int e, final Polynomial mod) {
        if(e == 0) return new Polynomial(1);
        final Polynomial temp = new Polynomial(poly.toInt());
        for(int i = 1; i < e; i++) {
            temp.multiplyMod(poly, mod);
        }
        return temp;
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
