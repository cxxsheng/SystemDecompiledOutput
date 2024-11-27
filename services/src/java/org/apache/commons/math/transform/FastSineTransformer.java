package org.apache.commons.math.transform;

/* loaded from: classes3.dex */
public class FastSineTransformer implements org.apache.commons.math.transform.RealTransformer {
    @Override // org.apache.commons.math.transform.RealTransformer
    public double[] transform(double[] dArr) throws java.lang.IllegalArgumentException {
        return fst(dArr);
    }

    @Override // org.apache.commons.math.transform.RealTransformer
    public double[] transform(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, int i) throws org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        double[] sample = org.apache.commons.math.transform.FastFourierTransformer.sample(univariateRealFunction, d, d2, i);
        sample[0] = 0.0d;
        return fst(sample);
    }

    public double[] transform2(double[] dArr) throws java.lang.IllegalArgumentException {
        return org.apache.commons.math.transform.FastFourierTransformer.scaleArray(fst(dArr), org.apache.commons.math.util.FastMath.sqrt(2.0d / dArr.length));
    }

    public double[] transform2(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, int i) throws org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        double[] sample = org.apache.commons.math.transform.FastFourierTransformer.sample(univariateRealFunction, d, d2, i);
        sample[0] = 0.0d;
        return org.apache.commons.math.transform.FastFourierTransformer.scaleArray(fst(sample), org.apache.commons.math.util.FastMath.sqrt(2.0d / i));
    }

    @Override // org.apache.commons.math.transform.RealTransformer
    public double[] inversetransform(double[] dArr) throws java.lang.IllegalArgumentException {
        return org.apache.commons.math.transform.FastFourierTransformer.scaleArray(fst(dArr), 2.0d / dArr.length);
    }

    @Override // org.apache.commons.math.transform.RealTransformer
    public double[] inversetransform(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, int i) throws org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        double[] sample = org.apache.commons.math.transform.FastFourierTransformer.sample(univariateRealFunction, d, d2, i);
        sample[0] = 0.0d;
        return org.apache.commons.math.transform.FastFourierTransformer.scaleArray(fst(sample), 2.0d / i);
    }

    public double[] inversetransform2(double[] dArr) throws java.lang.IllegalArgumentException {
        return transform2(dArr);
    }

    public double[] inversetransform2(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, int i) throws org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        return transform2(univariateRealFunction, d, d2, i);
    }

    protected double[] fst(double[] dArr) throws java.lang.IllegalArgumentException {
        double[] dArr2 = new double[dArr.length];
        org.apache.commons.math.transform.FastFourierTransformer.verifyDataSet(dArr);
        if (dArr[0] != 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.FIRST_ELEMENT_NOT_ZERO, java.lang.Double.valueOf(dArr[0]));
        }
        int length = dArr.length;
        if (length == 1) {
            dArr2[0] = 0.0d;
            return dArr2;
        }
        double[] dArr3 = new double[length];
        dArr3[0] = 0.0d;
        int i = length >> 1;
        dArr3[i] = dArr[i] * 2.0d;
        for (int i2 = 1; i2 < i; i2++) {
            int i3 = length - i2;
            double sin = org.apache.commons.math.util.FastMath.sin((i2 * 3.141592653589793d) / length) * (dArr[i2] + dArr[i3]);
            double d = (dArr[i2] - dArr[i3]) * 0.5d;
            dArr3[i2] = sin + d;
            dArr3[i3] = sin - d;
        }
        org.apache.commons.math.complex.Complex[] transform = new org.apache.commons.math.transform.FastFourierTransformer().transform(dArr3);
        dArr2[0] = 0.0d;
        dArr2[1] = transform[0].getReal() * 0.5d;
        for (int i4 = 1; i4 < i; i4++) {
            int i5 = i4 * 2;
            dArr2[i5] = -transform[i4].getImaginary();
            dArr2[i5 + 1] = transform[i4].getReal() + dArr2[i5 - 1];
        }
        return dArr2;
    }
}
