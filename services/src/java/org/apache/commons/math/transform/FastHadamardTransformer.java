package org.apache.commons.math.transform;

/* loaded from: classes3.dex */
public class FastHadamardTransformer implements org.apache.commons.math.transform.RealTransformer {
    @Override // org.apache.commons.math.transform.RealTransformer
    public double[] transform(double[] dArr) throws java.lang.IllegalArgumentException {
        return fht(dArr);
    }

    @Override // org.apache.commons.math.transform.RealTransformer
    public double[] transform(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, int i) throws org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        return fht(org.apache.commons.math.transform.FastFourierTransformer.sample(univariateRealFunction, d, d2, i));
    }

    @Override // org.apache.commons.math.transform.RealTransformer
    public double[] inversetransform(double[] dArr) throws java.lang.IllegalArgumentException {
        return org.apache.commons.math.transform.FastFourierTransformer.scaleArray(fht(dArr), 1.0d / dArr.length);
    }

    @Override // org.apache.commons.math.transform.RealTransformer
    public double[] inversetransform(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, int i) throws org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        return org.apache.commons.math.transform.FastFourierTransformer.scaleArray(fht(org.apache.commons.math.transform.FastFourierTransformer.sample(univariateRealFunction, d, d2, i)), 1.0d / i);
    }

    public int[] transform(int[] iArr) throws java.lang.IllegalArgumentException {
        return fht(iArr);
    }

    protected double[] fht(double[] dArr) throws java.lang.IllegalArgumentException {
        int length = dArr.length;
        int i = length / 2;
        if (!org.apache.commons.math.transform.FastFourierTransformer.isPowerOf2(length)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POWER_OF_TWO, java.lang.Integer.valueOf(length));
        }
        double[] dArr2 = new double[length];
        double[] dArr3 = (double[]) dArr.clone();
        int i2 = 1;
        while (true) {
            double[] dArr4 = dArr2;
            dArr2 = dArr3;
            dArr3 = dArr4;
            if (i2 < length) {
                for (int i3 = 0; i3 < i; i3++) {
                    int i4 = i3 * 2;
                    dArr3[i3] = dArr2[i4] + dArr2[i4 + 1];
                }
                for (int i5 = i; i5 < length; i5++) {
                    int i6 = (i5 * 2) - length;
                    dArr3[i5] = dArr2[i6] - dArr2[i6 + 1];
                }
                i2 <<= 1;
            } else {
                return dArr2;
            }
        }
    }

    protected int[] fht(int[] iArr) throws java.lang.IllegalArgumentException {
        int length = iArr.length;
        int i = length / 2;
        if (!org.apache.commons.math.transform.FastFourierTransformer.isPowerOf2(length)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POWER_OF_TWO, java.lang.Integer.valueOf(length));
        }
        int[] iArr2 = new int[length];
        int[] iArr3 = (int[]) iArr.clone();
        int i2 = 1;
        while (true) {
            int[] iArr4 = iArr2;
            iArr2 = iArr3;
            iArr3 = iArr4;
            if (i2 < length) {
                for (int i3 = 0; i3 < i; i3++) {
                    int i4 = i3 * 2;
                    iArr3[i3] = iArr2[i4] + iArr2[i4 + 1];
                }
                for (int i5 = i; i5 < length; i5++) {
                    int i6 = (i5 * 2) - length;
                    iArr3[i5] = iArr2[i6] - iArr2[i6 + 1];
                }
                i2 <<= 1;
            } else {
                return iArr2;
            }
        }
    }
}
