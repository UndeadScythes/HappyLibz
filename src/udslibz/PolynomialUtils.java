package udslibz;

/**
 *
 * @author UndeadScythes
 */
public class PolynomialUtils {
    public static Polynomial product(final Polynomial polyA, final Polynomial polyB) {
        int temp = 0;
        for(int i = 0; i <= polyA.getDegree(); i++) {
            if(polyA.getCoeff(i) == 1) {
                temp ^= (polyB.toInt() << i);
            }
        }
        return new Polynomial(temp);
    }

    public static Polynomial productMod(final Polynomial polyA, final Polynomial polyB, final Polynomial mod) {
        int temp1 = 0;
        for(int i = 0; i <= polyA.getDegree(); i++) {
            if(polyA.getCoeff(i) == 1) {
                temp1 ^= (polyB.toInt() << i);
            }
        }
        final Polynomial temp2 = new Polynomial(temp1);
        temp2.modulo(mod);
        return temp2;
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
        return PolynomialUtils.product(poly, PolynomialUtils.toPower(poly, e - 1));
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

        public static Polynomial getStrictIrreducible(final int degree, final int order, final int start) {
        int test = start;
        Polynomial polynomial = new Polynomial(test);
        while(polynomial.getWeight() % 2 == 0 || !polynomial.isMonomial() || polynomial.getDegree() < degree || !polynomial.isIrreducible() || polynomial.isPrimitive() || polynomial.getOrder() != order) {
            test++;
            polynomial = new Polynomial(test);
            if(polynomial.getDegree() > degree) {
                return null;
            }
        }
        return polynomial;
    }
}
