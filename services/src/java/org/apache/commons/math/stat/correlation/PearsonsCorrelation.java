package org.apache.commons.math.stat.correlation;

/* loaded from: classes3.dex */
public class PearsonsCorrelation {
    private final org.apache.commons.math.linear.RealMatrix correlationMatrix;
    private final int nObs;

    public PearsonsCorrelation() {
        this.correlationMatrix = null;
        this.nObs = 0;
    }

    public PearsonsCorrelation(double[][] dArr) {
        this(new org.apache.commons.math.linear.BlockRealMatrix(dArr));
    }

    public PearsonsCorrelation(org.apache.commons.math.linear.RealMatrix realMatrix) {
        checkSufficientData(realMatrix);
        this.nObs = realMatrix.getRowDimension();
        this.correlationMatrix = computeCorrelationMatrix(realMatrix);
    }

    public PearsonsCorrelation(org.apache.commons.math.stat.correlation.Covariance covariance) {
        org.apache.commons.math.linear.RealMatrix covarianceMatrix = covariance.getCovarianceMatrix();
        if (covarianceMatrix == null) {
            throw new org.apache.commons.math.exception.NullArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.COVARIANCE_MATRIX);
        }
        this.nObs = covariance.getN();
        this.correlationMatrix = covarianceToCorrelation(covarianceMatrix);
    }

    public PearsonsCorrelation(org.apache.commons.math.linear.RealMatrix realMatrix, int i) {
        this.nObs = i;
        this.correlationMatrix = covarianceToCorrelation(realMatrix);
    }

    public org.apache.commons.math.linear.RealMatrix getCorrelationMatrix() {
        return this.correlationMatrix;
    }

    public org.apache.commons.math.linear.RealMatrix getCorrelationStandardErrors() {
        int columnDimension = this.correlationMatrix.getColumnDimension();
        double[][] dArr = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, columnDimension, columnDimension);
        for (int i = 0; i < columnDimension; i++) {
            for (int i2 = 0; i2 < columnDimension; i2++) {
                double entry = this.correlationMatrix.getEntry(i, i2);
                dArr[i][i2] = org.apache.commons.math.util.FastMath.sqrt((1.0d - (entry * entry)) / (this.nObs - 2));
            }
        }
        return new org.apache.commons.math.linear.BlockRealMatrix(dArr);
    }

    public org.apache.commons.math.linear.RealMatrix getCorrelationPValues() throws org.apache.commons.math.MathException {
        org.apache.commons.math.distribution.TDistributionImpl tDistributionImpl = new org.apache.commons.math.distribution.TDistributionImpl(this.nObs - 2);
        int columnDimension = this.correlationMatrix.getColumnDimension();
        double[][] dArr = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, columnDimension, columnDimension);
        for (int i = 0; i < columnDimension; i++) {
            for (int i2 = 0; i2 < columnDimension; i2++) {
                if (i == i2) {
                    dArr[i][i2] = 0.0d;
                } else {
                    double entry = this.correlationMatrix.getEntry(i, i2);
                    dArr[i][i2] = tDistributionImpl.cumulativeProbability(-org.apache.commons.math.util.FastMath.abs(entry * org.apache.commons.math.util.FastMath.sqrt((this.nObs - 2) / (1.0d - (entry * entry))))) * 2.0d;
                }
            }
        }
        return new org.apache.commons.math.linear.BlockRealMatrix(dArr);
    }

    public org.apache.commons.math.linear.RealMatrix computeCorrelationMatrix(org.apache.commons.math.linear.RealMatrix realMatrix) {
        int columnDimension = realMatrix.getColumnDimension();
        org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix = new org.apache.commons.math.linear.BlockRealMatrix(columnDimension, columnDimension);
        for (int i = 0; i < columnDimension; i++) {
            for (int i2 = 0; i2 < i; i2++) {
                double correlation = correlation(realMatrix.getColumn(i), realMatrix.getColumn(i2));
                blockRealMatrix.setEntry(i, i2, correlation);
                blockRealMatrix.setEntry(i2, i, correlation);
            }
            blockRealMatrix.setEntry(i, i, 1.0d);
        }
        return blockRealMatrix;
    }

    public org.apache.commons.math.linear.RealMatrix computeCorrelationMatrix(double[][] dArr) {
        return computeCorrelationMatrix(new org.apache.commons.math.linear.BlockRealMatrix(dArr));
    }

    public double correlation(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.stat.regression.SimpleRegression simpleRegression = new org.apache.commons.math.stat.regression.SimpleRegression();
        if (dArr.length != dArr2.length) {
            throw new org.apache.commons.math.exception.DimensionMismatchException(dArr.length, dArr2.length);
        }
        if (dArr.length < 2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INSUFFICIENT_DIMENSION, java.lang.Integer.valueOf(dArr.length), 2);
        }
        for (int i = 0; i < dArr.length; i++) {
            simpleRegression.addData(dArr[i], dArr2[i]);
        }
        return simpleRegression.getR();
    }

    public org.apache.commons.math.linear.RealMatrix covarianceToCorrelation(org.apache.commons.math.linear.RealMatrix realMatrix) {
        int columnDimension = realMatrix.getColumnDimension();
        org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix = new org.apache.commons.math.linear.BlockRealMatrix(columnDimension, columnDimension);
        for (int i = 0; i < columnDimension; i++) {
            double sqrt = org.apache.commons.math.util.FastMath.sqrt(realMatrix.getEntry(i, i));
            blockRealMatrix.setEntry(i, i, 1.0d);
            for (int i2 = 0; i2 < i; i2++) {
                double entry = realMatrix.getEntry(i, i2) / (org.apache.commons.math.util.FastMath.sqrt(realMatrix.getEntry(i2, i2)) * sqrt);
                blockRealMatrix.setEntry(i, i2, entry);
                blockRealMatrix.setEntry(i2, i, entry);
            }
        }
        return blockRealMatrix;
    }

    private void checkSufficientData(org.apache.commons.math.linear.RealMatrix realMatrix) {
        int rowDimension = realMatrix.getRowDimension();
        int columnDimension = realMatrix.getColumnDimension();
        if (rowDimension < 2 || columnDimension < 2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INSUFFICIENT_ROWS_AND_COLUMNS, java.lang.Integer.valueOf(rowDimension), java.lang.Integer.valueOf(columnDimension));
        }
    }
}
