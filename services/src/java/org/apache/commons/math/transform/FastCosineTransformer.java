package org.apache.commons.math.transform;

/* loaded from: classes3.dex */
public class FastCosineTransformer implements org.apache.commons.math.transform.RealTransformer {
    @Override // org.apache.commons.math.transform.RealTransformer
    public double[] transform(double[] dArr) throws java.lang.IllegalArgumentException {
        return fct(dArr);
    }

    @Override // org.apache.commons.math.transform.RealTransformer
    public double[] transform(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, int i) throws org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        return fct(org.apache.commons.math.transform.FastFourierTransformer.sample(univariateRealFunction, d, d2, i));
    }

    public double[] transform2(double[] dArr) throws java.lang.IllegalArgumentException {
        return org.apache.commons.math.transform.FastFourierTransformer.scaleArray(fct(dArr), org.apache.commons.math.util.FastMath.sqrt(2.0d / (dArr.length - 1)));
    }

    public double[] transform2(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, int i) throws org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        return org.apache.commons.math.transform.FastFourierTransformer.scaleArray(fct(org.apache.commons.math.transform.FastFourierTransformer.sample(univariateRealFunction, d, d2, i)), org.apache.commons.math.util.FastMath.sqrt(2.0d / (i - 1)));
    }

    @Override // org.apache.commons.math.transform.RealTransformer
    public double[] inversetransform(double[] dArr) throws java.lang.IllegalArgumentException {
        return org.apache.commons.math.transform.FastFourierTransformer.scaleArray(fct(dArr), 2.0d / (dArr.length - 1));
    }

    @Override // org.apache.commons.math.transform.RealTransformer
    public double[] inversetransform(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, int i) throws org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        return org.apache.commons.math.transform.FastFourierTransformer.scaleArray(fct(org.apache.commons.math.transform.FastFourierTransformer.sample(univariateRealFunction, d, d2, i)), 2.0d / (i - 1));
    }

    public double[] inversetransform2(double[] dArr) throws java.lang.IllegalArgumentException {
        return transform2(dArr);
    }

    public double[] inversetransform2(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, int i) throws org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        return transform2(univariateRealFunction, d, d2, i);
    }

    protected double[] fct(double[] dArr) throws java.lang.IllegalArgumentException {
        double[] dArr2 = new double[dArr.length];
        int length = dArr.length - 1;
        if (!org.apache.commons.math.transform.FastFourierTransformer.isPowerOf2(length)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POWER_OF_TWO_PLUS_ONE, java.lang.Integer.valueOf(dArr.length));
        }
        double d = 0.5d;
        if (length == 1) {
            dArr2[0] = (dArr[0] + dArr[1]) * 0.5d;
            dArr2[1] = (dArr[0] - dArr[1]) * 0.5d;
            return dArr2;
        }
        double[] dArr3 = new double[length];
        dArr3[0] = (dArr[0] + dArr[length]) * 0.5d;
        int i = length >> 1;
        dArr3[i] = dArr[i];
        double d2 = (dArr[0] - dArr[length]) * 0.5d;
        int i2 = 1;
        while (i2 < i) {
            int i3 = length - i2;
            double d3 = (dArr[i2] + dArr[i3]) * d;
            double[] dArr4 = dArr3;
            double d4 = (i2 * 3.141592653589793d) / length;
            double sin = org.apache.commons.math.util.FastMath.sin(d4) * (dArr[i2] - dArr[i3]);
            double cos = org.apache.commons.math.util.FastMath.cos(d4) * (dArr[i2] - dArr[i3]);
            dArr4[i2] = d3 - sin;
            dArr4[i3] = d3 + sin;
            d2 += cos;
            i2++;
            dArr3 = dArr4;
            d = 0.5d;
        }
        org.apache.commons.math.complex.Complex[] transform = new org.apache.commons.math.transform.FastFourierTransformer().transform(dArr3);
        dArr2[0] = transform[0].getReal();
        dArr2[1] = d2;
        for (int i4 = 1; i4 < i; i4++) {
            int i5 = i4 * 2;
            dArr2[i5] = transform[i4].getReal();
            dArr2[i5 + 1] = dArr2[i5 - 1] - transform[i4].getImaginary();
        }
        dArr2[length] = transform[i].getReal();
        return dArr2;
    }
}
