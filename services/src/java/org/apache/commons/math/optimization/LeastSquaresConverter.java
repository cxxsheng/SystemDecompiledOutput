package org.apache.commons.math.optimization;

/* loaded from: classes3.dex */
public class LeastSquaresConverter implements org.apache.commons.math.analysis.MultivariateRealFunction {
    private final org.apache.commons.math.analysis.MultivariateVectorialFunction function;
    private final double[] observations;
    private final org.apache.commons.math.linear.RealMatrix scale;
    private final double[] weights;

    public LeastSquaresConverter(org.apache.commons.math.analysis.MultivariateVectorialFunction multivariateVectorialFunction, double[] dArr) {
        this.function = multivariateVectorialFunction;
        this.observations = (double[]) dArr.clone();
        this.weights = null;
        this.scale = null;
    }

    public LeastSquaresConverter(org.apache.commons.math.analysis.MultivariateVectorialFunction multivariateVectorialFunction, double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException {
        if (dArr.length != dArr2.length) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(dArr2.length));
        }
        this.function = multivariateVectorialFunction;
        this.observations = (double[]) dArr.clone();
        this.weights = (double[]) dArr2.clone();
        this.scale = null;
    }

    public LeastSquaresConverter(org.apache.commons.math.analysis.MultivariateVectorialFunction multivariateVectorialFunction, double[] dArr, org.apache.commons.math.linear.RealMatrix realMatrix) throws java.lang.IllegalArgumentException {
        if (dArr.length != realMatrix.getColumnDimension()) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(realMatrix.getColumnDimension()));
        }
        this.function = multivariateVectorialFunction;
        this.observations = (double[]) dArr.clone();
        this.weights = null;
        this.scale = realMatrix.copy();
    }

    @Override // org.apache.commons.math.analysis.MultivariateRealFunction
    public double value(double[] dArr) throws org.apache.commons.math.FunctionEvaluationException {
        double[] value = this.function.value(dArr);
        if (value.length != this.observations.length) {
            throw new org.apache.commons.math.FunctionEvaluationException(dArr, org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(value.length), java.lang.Integer.valueOf(this.observations.length));
        }
        int i = 0;
        for (int i2 = 0; i2 < value.length; i2++) {
            value[i2] = value[i2] - this.observations[i2];
        }
        double d = 0.0d;
        if (this.weights != null) {
            while (i < value.length) {
                double d2 = value[i];
                d += this.weights[i] * d2 * d2;
                i++;
            }
        } else if (this.scale != null) {
            double[] operate = this.scale.operate(value);
            int length = operate.length;
            while (i < length) {
                double d3 = operate[i];
                d += d3 * d3;
                i++;
            }
        } else {
            int length2 = value.length;
            while (i < length2) {
                double d4 = value[i];
                d += d4 * d4;
                i++;
            }
        }
        return d;
    }
}
