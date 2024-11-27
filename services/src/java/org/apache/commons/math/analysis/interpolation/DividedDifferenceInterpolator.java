package org.apache.commons.math.analysis.interpolation;

/* loaded from: classes3.dex */
public class DividedDifferenceInterpolator implements org.apache.commons.math.analysis.interpolation.UnivariateRealInterpolator, java.io.Serializable {
    private static final long serialVersionUID = 107049519551235069L;

    @Override // org.apache.commons.math.analysis.interpolation.UnivariateRealInterpolator
    public org.apache.commons.math.analysis.polynomials.PolynomialFunctionNewtonForm interpolate(double[] dArr, double[] dArr2) throws org.apache.commons.math.DuplicateSampleAbscissaException {
        org.apache.commons.math.analysis.polynomials.PolynomialFunctionLagrangeForm.verifyInterpolationArray(dArr, dArr2);
        int length = dArr.length - 1;
        double[] dArr3 = new double[length];
        java.lang.System.arraycopy(dArr, 0, dArr3, 0, length);
        return new org.apache.commons.math.analysis.polynomials.PolynomialFunctionNewtonForm(computeDividedDifference(dArr, dArr2), dArr3);
    }

    protected static double[] computeDividedDifference(double[] dArr, double[] dArr2) throws org.apache.commons.math.DuplicateSampleAbscissaException {
        org.apache.commons.math.analysis.polynomials.PolynomialFunctionLagrangeForm.verifyInterpolationArray(dArr, dArr2);
        double[] dArr3 = (double[]) dArr2.clone();
        int length = dArr.length;
        double[] dArr4 = new double[length];
        dArr4[0] = dArr3[0];
        for (int i = 1; i < length; i++) {
            int i2 = 0;
            while (i2 < length - i) {
                int i3 = i2 + i;
                double d = dArr[i3] - dArr[i2];
                if (d == 0.0d) {
                    throw new org.apache.commons.math.DuplicateSampleAbscissaException(dArr[i2], i2, i3);
                }
                int i4 = i2 + 1;
                dArr3[i2] = (dArr3[i4] - dArr3[i2]) / d;
                i2 = i4;
            }
            dArr4[i] = dArr3[0];
        }
        return dArr4;
    }
}
