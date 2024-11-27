package org.apache.commons.math.analysis.polynomials;

/* loaded from: classes3.dex */
public class PolynomialsUtils {
    private static final java.util.ArrayList<org.apache.commons.math.fraction.BigFraction> CHEBYSHEV_COEFFICIENTS = new java.util.ArrayList<>();
    private static final java.util.ArrayList<org.apache.commons.math.fraction.BigFraction> HERMITE_COEFFICIENTS;
    private static final java.util.ArrayList<org.apache.commons.math.fraction.BigFraction> LAGUERRE_COEFFICIENTS;
    private static final java.util.ArrayList<org.apache.commons.math.fraction.BigFraction> LEGENDRE_COEFFICIENTS;

    private interface RecurrenceCoefficientsGenerator {
        org.apache.commons.math.fraction.BigFraction[] generate(int i);
    }

    static {
        CHEBYSHEV_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.ONE);
        CHEBYSHEV_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.ZERO);
        CHEBYSHEV_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.ONE);
        HERMITE_COEFFICIENTS = new java.util.ArrayList<>();
        HERMITE_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.ONE);
        HERMITE_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.ZERO);
        HERMITE_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.TWO);
        LAGUERRE_COEFFICIENTS = new java.util.ArrayList<>();
        LAGUERRE_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.ONE);
        LAGUERRE_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.ONE);
        LAGUERRE_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.MINUS_ONE);
        LEGENDRE_COEFFICIENTS = new java.util.ArrayList<>();
        LEGENDRE_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.ONE);
        LEGENDRE_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.ZERO);
        LEGENDRE_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.ONE);
    }

    private PolynomialsUtils() {
    }

    public static org.apache.commons.math.analysis.polynomials.PolynomialFunction createChebyshevPolynomial(int i) {
        return buildPolynomial(i, CHEBYSHEV_COEFFICIENTS, new org.apache.commons.math.analysis.polynomials.PolynomialsUtils.RecurrenceCoefficientsGenerator() { // from class: org.apache.commons.math.analysis.polynomials.PolynomialsUtils.1
            private final org.apache.commons.math.fraction.BigFraction[] coeffs = {org.apache.commons.math.fraction.BigFraction.ZERO, org.apache.commons.math.fraction.BigFraction.TWO, org.apache.commons.math.fraction.BigFraction.ONE};

            @Override // org.apache.commons.math.analysis.polynomials.PolynomialsUtils.RecurrenceCoefficientsGenerator
            public org.apache.commons.math.fraction.BigFraction[] generate(int i2) {
                return this.coeffs;
            }
        });
    }

    public static org.apache.commons.math.analysis.polynomials.PolynomialFunction createHermitePolynomial(int i) {
        return buildPolynomial(i, HERMITE_COEFFICIENTS, new org.apache.commons.math.analysis.polynomials.PolynomialsUtils.RecurrenceCoefficientsGenerator() { // from class: org.apache.commons.math.analysis.polynomials.PolynomialsUtils.2
            @Override // org.apache.commons.math.analysis.polynomials.PolynomialsUtils.RecurrenceCoefficientsGenerator
            public org.apache.commons.math.fraction.BigFraction[] generate(int i2) {
                return new org.apache.commons.math.fraction.BigFraction[]{org.apache.commons.math.fraction.BigFraction.ZERO, org.apache.commons.math.fraction.BigFraction.TWO, new org.apache.commons.math.fraction.BigFraction(i2 * 2)};
            }
        });
    }

    public static org.apache.commons.math.analysis.polynomials.PolynomialFunction createLaguerrePolynomial(int i) {
        return buildPolynomial(i, LAGUERRE_COEFFICIENTS, new org.apache.commons.math.analysis.polynomials.PolynomialsUtils.RecurrenceCoefficientsGenerator() { // from class: org.apache.commons.math.analysis.polynomials.PolynomialsUtils.3
            @Override // org.apache.commons.math.analysis.polynomials.PolynomialsUtils.RecurrenceCoefficientsGenerator
            public org.apache.commons.math.fraction.BigFraction[] generate(int i2) {
                int i3 = i2 + 1;
                return new org.apache.commons.math.fraction.BigFraction[]{new org.apache.commons.math.fraction.BigFraction((i2 * 2) + 1, i3), new org.apache.commons.math.fraction.BigFraction(-1, i3), new org.apache.commons.math.fraction.BigFraction(i2, i3)};
            }
        });
    }

    public static org.apache.commons.math.analysis.polynomials.PolynomialFunction createLegendrePolynomial(int i) {
        return buildPolynomial(i, LEGENDRE_COEFFICIENTS, new org.apache.commons.math.analysis.polynomials.PolynomialsUtils.RecurrenceCoefficientsGenerator() { // from class: org.apache.commons.math.analysis.polynomials.PolynomialsUtils.4
            @Override // org.apache.commons.math.analysis.polynomials.PolynomialsUtils.RecurrenceCoefficientsGenerator
            public org.apache.commons.math.fraction.BigFraction[] generate(int i2) {
                int i3 = i2 + 1;
                return new org.apache.commons.math.fraction.BigFraction[]{org.apache.commons.math.fraction.BigFraction.ZERO, new org.apache.commons.math.fraction.BigFraction(i2 + i3, i3), new org.apache.commons.math.fraction.BigFraction(i2, i3)};
            }
        });
    }

    private static org.apache.commons.math.analysis.polynomials.PolynomialFunction buildPolynomial(int i, java.util.ArrayList<org.apache.commons.math.fraction.BigFraction> arrayList, org.apache.commons.math.analysis.polynomials.PolynomialsUtils.RecurrenceCoefficientsGenerator recurrenceCoefficientsGenerator) {
        int floor = ((int) org.apache.commons.math.util.FastMath.floor(org.apache.commons.math.util.FastMath.sqrt(arrayList.size() * 2))) - 1;
        synchronized (org.apache.commons.math.analysis.polynomials.PolynomialsUtils.class) {
            if (i > floor) {
                try {
                    computeUpToDegree(i, floor, recurrenceCoefficientsGenerator, arrayList);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        int i2 = i + 1;
        int i3 = (i * i2) / 2;
        double[] dArr = new double[i2];
        for (int i4 = 0; i4 <= i; i4++) {
            dArr[i4] = arrayList.get(i3 + i4).doubleValue();
        }
        return new org.apache.commons.math.analysis.polynomials.PolynomialFunction(dArr);
    }

    private static void computeUpToDegree(int i, int i2, org.apache.commons.math.analysis.polynomials.PolynomialsUtils.RecurrenceCoefficientsGenerator recurrenceCoefficientsGenerator, java.util.ArrayList<org.apache.commons.math.fraction.BigFraction> arrayList) {
        int i3 = ((i2 - 1) * i2) / 2;
        while (i2 < i) {
            int i4 = i3 + i2;
            org.apache.commons.math.fraction.BigFraction[] generate = recurrenceCoefficientsGenerator.generate(i2);
            org.apache.commons.math.fraction.BigFraction bigFraction = arrayList.get(i4);
            arrayList.add(bigFraction.multiply(generate[0]).subtract(arrayList.get(i3).multiply(generate[2])));
            int i5 = 1;
            while (i5 < i2) {
                org.apache.commons.math.fraction.BigFraction bigFraction2 = arrayList.get(i4 + i5);
                arrayList.add(bigFraction2.multiply(generate[0]).add(bigFraction.multiply(generate[1])).subtract(arrayList.get(i3 + i5).multiply(generate[2])));
                i5++;
                bigFraction = bigFraction2;
            }
            org.apache.commons.math.fraction.BigFraction bigFraction3 = arrayList.get(i4 + i2);
            arrayList.add(bigFraction3.multiply(generate[0]).add(bigFraction.multiply(generate[1])));
            arrayList.add(bigFraction3.multiply(generate[1]));
            i2++;
            i3 = i4;
        }
    }
}
