package org.apache.commons.math.stat.regression;

/* loaded from: classes3.dex */
public abstract class AbstractMultipleLinearRegression implements org.apache.commons.math.stat.regression.MultipleLinearRegression {
    protected org.apache.commons.math.linear.RealMatrix X;
    protected org.apache.commons.math.linear.RealVector Y;
    private boolean noIntercept = false;

    protected abstract org.apache.commons.math.linear.RealVector calculateBeta();

    protected abstract org.apache.commons.math.linear.RealMatrix calculateBetaVariance();

    public boolean isNoIntercept() {
        return this.noIntercept;
    }

    public void setNoIntercept(boolean z) {
        this.noIntercept = z;
    }

    public void newSampleData(double[] dArr, int i, int i2) {
        if (dArr == null) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NULL_NOT_ALLOWED, new java.lang.Object[0]);
        }
        int i3 = i2 + 1;
        if (dArr.length != i * i3) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INVALID_REGRESSION_ARRAY, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        }
        if (i <= i2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_ENOUGH_DATA_FOR_NUMBER_OF_PREDICTORS, new java.lang.Object[0]);
        }
        double[] dArr2 = new double[i];
        if (!this.noIntercept) {
            i2 = i3;
        }
        double[][] dArr3 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, i, i2);
        int i4 = 0;
        int i5 = 0;
        while (i4 < i) {
            int i6 = i5 + 1;
            dArr2[i4] = dArr[i5];
            if (!this.noIntercept) {
                dArr3[i4][0] = 1.0d;
            }
            int i7 = !this.noIntercept ? 1 : 0;
            while (i7 < i2) {
                dArr3[i4][i7] = dArr[i6];
                i7++;
                i6++;
            }
            i4++;
            i5 = i6;
        }
        this.X = new org.apache.commons.math.linear.Array2DRowRealMatrix(dArr3);
        this.Y = new org.apache.commons.math.linear.ArrayRealVector(dArr2);
    }

    protected void newYSampleData(double[] dArr) {
        if (dArr == null) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NULL_NOT_ALLOWED, new java.lang.Object[0]);
        }
        if (dArr.length == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NO_DATA, new java.lang.Object[0]);
        }
        this.Y = new org.apache.commons.math.linear.ArrayRealVector(dArr);
    }

    protected void newXSampleData(double[][] dArr) {
        if (dArr == null) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NULL_NOT_ALLOWED, new java.lang.Object[0]);
        }
        if (dArr.length == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NO_DATA, new java.lang.Object[0]);
        }
        if (this.noIntercept) {
            this.X = new org.apache.commons.math.linear.Array2DRowRealMatrix(dArr, true);
            return;
        }
        int length = dArr[0].length;
        double[][] dArr2 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, dArr.length, length + 1);
        for (int i = 0; i < dArr.length; i++) {
            if (dArr[i].length == length) {
                dArr2[i][0] = 1.0d;
                java.lang.System.arraycopy(dArr[i], 0, dArr2[i], 1, length);
            } else {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIFFERENT_ROWS_LENGTHS, java.lang.Integer.valueOf(dArr[i].length), java.lang.Integer.valueOf(length));
            }
        }
        this.X = new org.apache.commons.math.linear.Array2DRowRealMatrix(dArr2, false);
    }

    protected void validateSampleData(double[][] dArr, double[] dArr2) {
        if (dArr == null || dArr2 == null || dArr.length != dArr2.length) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(dArr == null ? 0 : dArr.length), java.lang.Integer.valueOf(dArr2 != null ? dArr2.length : 0));
        }
        if (dArr.length == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NO_DATA, new java.lang.Object[0]);
        }
        if (dArr[0].length + 1 > dArr.length) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_ENOUGH_DATA_FOR_NUMBER_OF_PREDICTORS, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(dArr[0].length));
        }
    }

    protected void validateCovarianceData(double[][] dArr, double[][] dArr2) {
        if (dArr.length != dArr2.length) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(dArr2.length));
        }
        if (dArr2.length > 0 && dArr2.length != dArr2[0].length) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NON_SQUARE_MATRIX, java.lang.Integer.valueOf(dArr2.length), java.lang.Integer.valueOf(dArr2[0].length));
        }
    }

    @Override // org.apache.commons.math.stat.regression.MultipleLinearRegression
    public double[] estimateRegressionParameters() {
        return calculateBeta().getData();
    }

    @Override // org.apache.commons.math.stat.regression.MultipleLinearRegression
    public double[] estimateResiduals() {
        return this.Y.subtract(this.X.operate(calculateBeta())).getData();
    }

    @Override // org.apache.commons.math.stat.regression.MultipleLinearRegression
    public double[][] estimateRegressionParametersVariance() {
        return calculateBetaVariance().getData();
    }

    @Override // org.apache.commons.math.stat.regression.MultipleLinearRegression
    public double[] estimateRegressionParametersStandardErrors() {
        double[][] estimateRegressionParametersVariance = estimateRegressionParametersVariance();
        double calculateErrorVariance = calculateErrorVariance();
        int length = estimateRegressionParametersVariance[0].length;
        double[] dArr = new double[length];
        for (int i = 0; i < length; i++) {
            dArr[i] = org.apache.commons.math.util.FastMath.sqrt(estimateRegressionParametersVariance[i][i] * calculateErrorVariance);
        }
        return dArr;
    }

    @Override // org.apache.commons.math.stat.regression.MultipleLinearRegression
    public double estimateRegressandVariance() {
        return calculateYVariance();
    }

    public double estimateErrorVariance() {
        return calculateErrorVariance();
    }

    public double estimateRegressionStandardError() {
        return java.lang.Math.sqrt(estimateErrorVariance());
    }

    protected double calculateYVariance() {
        return new org.apache.commons.math.stat.descriptive.moment.Variance().evaluate(this.Y.getData());
    }

    protected double calculateErrorVariance() {
        org.apache.commons.math.linear.RealVector calculateResiduals = calculateResiduals();
        return calculateResiduals.dotProduct(calculateResiduals) / (this.X.getRowDimension() - this.X.getColumnDimension());
    }

    protected org.apache.commons.math.linear.RealVector calculateResiduals() {
        return this.Y.subtract(this.X.operate(calculateBeta()));
    }
}
