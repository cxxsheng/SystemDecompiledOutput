package org.apache.commons.math.stat.correlation;

/* loaded from: classes3.dex */
public class Covariance {
    private final org.apache.commons.math.linear.RealMatrix covarianceMatrix;
    private final int n;

    public Covariance() {
        this.covarianceMatrix = null;
        this.n = 0;
    }

    public Covariance(double[][] dArr, boolean z) {
        this(new org.apache.commons.math.linear.BlockRealMatrix(dArr), z);
    }

    public Covariance(double[][] dArr) {
        this(dArr, true);
    }

    public Covariance(org.apache.commons.math.linear.RealMatrix realMatrix, boolean z) {
        checkSufficientData(realMatrix);
        this.n = realMatrix.getRowDimension();
        this.covarianceMatrix = computeCovarianceMatrix(realMatrix, z);
    }

    public Covariance(org.apache.commons.math.linear.RealMatrix realMatrix) {
        this(realMatrix, true);
    }

    public org.apache.commons.math.linear.RealMatrix getCovarianceMatrix() {
        return this.covarianceMatrix;
    }

    public int getN() {
        return this.n;
    }

    protected org.apache.commons.math.linear.RealMatrix computeCovarianceMatrix(org.apache.commons.math.linear.RealMatrix realMatrix, boolean z) {
        int columnDimension = realMatrix.getColumnDimension();
        org.apache.commons.math.stat.descriptive.moment.Variance variance = new org.apache.commons.math.stat.descriptive.moment.Variance(z);
        org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix = new org.apache.commons.math.linear.BlockRealMatrix(columnDimension, columnDimension);
        for (int i = 0; i < columnDimension; i++) {
            for (int i2 = 0; i2 < i; i2++) {
                double covariance = covariance(realMatrix.getColumn(i), realMatrix.getColumn(i2), z);
                blockRealMatrix.setEntry(i, i2, covariance);
                blockRealMatrix.setEntry(i2, i, covariance);
            }
            blockRealMatrix.setEntry(i, i, variance.evaluate(realMatrix.getColumn(i)));
        }
        return blockRealMatrix;
    }

    protected org.apache.commons.math.linear.RealMatrix computeCovarianceMatrix(org.apache.commons.math.linear.RealMatrix realMatrix) {
        return computeCovarianceMatrix(realMatrix, true);
    }

    protected org.apache.commons.math.linear.RealMatrix computeCovarianceMatrix(double[][] dArr, boolean z) {
        return computeCovarianceMatrix(new org.apache.commons.math.linear.BlockRealMatrix(dArr), z);
    }

    protected org.apache.commons.math.linear.RealMatrix computeCovarianceMatrix(double[][] dArr) {
        return computeCovarianceMatrix(dArr, true);
    }

    public double covariance(double[] dArr, double[] dArr2, boolean z) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.stat.descriptive.moment.Mean mean = new org.apache.commons.math.stat.descriptive.moment.Mean();
        int length = dArr.length;
        if (length != dArr2.length) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(length), java.lang.Integer.valueOf(dArr2.length));
        }
        if (length < 2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INSUFFICIENT_DIMENSION, java.lang.Integer.valueOf(length), 2);
        }
        double evaluate = mean.evaluate(dArr);
        double evaluate2 = mean.evaluate(dArr2);
        double d = 0.0d;
        int i = 0;
        while (i < length) {
            double d2 = ((dArr[i] - evaluate) * (dArr2[i] - evaluate2)) - d;
            i++;
            d += d2 / i;
        }
        return z ? d * (length / (length - 1)) : d;
    }

    public double covariance(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException {
        return covariance(dArr, dArr2, true);
    }

    private void checkSufficientData(org.apache.commons.math.linear.RealMatrix realMatrix) {
        int rowDimension = realMatrix.getRowDimension();
        int columnDimension = realMatrix.getColumnDimension();
        if (rowDimension < 2 || columnDimension < 2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INSUFFICIENT_ROWS_AND_COLUMNS, java.lang.Integer.valueOf(rowDimension), java.lang.Integer.valueOf(columnDimension));
        }
    }
}
