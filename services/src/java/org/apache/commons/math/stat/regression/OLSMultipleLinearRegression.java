package org.apache.commons.math.stat.regression;

/* loaded from: classes3.dex */
public class OLSMultipleLinearRegression extends org.apache.commons.math.stat.regression.AbstractMultipleLinearRegression {
    private org.apache.commons.math.linear.QRDecomposition qr = null;

    public void newSampleData(double[] dArr, double[][] dArr2) {
        validateSampleData(dArr2, dArr);
        newYSampleData(dArr);
        newXSampleData(dArr2);
    }

    @Override // org.apache.commons.math.stat.regression.AbstractMultipleLinearRegression
    public void newSampleData(double[] dArr, int i, int i2) {
        super.newSampleData(dArr, i, i2);
        this.qr = new org.apache.commons.math.linear.QRDecompositionImpl(this.X);
    }

    public org.apache.commons.math.linear.RealMatrix calculateHat() {
        org.apache.commons.math.linear.RealMatrix q = this.qr.getQ();
        int columnDimension = this.qr.getR().getColumnDimension();
        int columnDimension2 = q.getColumnDimension();
        org.apache.commons.math.linear.Array2DRowRealMatrix array2DRowRealMatrix = new org.apache.commons.math.linear.Array2DRowRealMatrix(columnDimension2, columnDimension2);
        double[][] dataRef = array2DRowRealMatrix.getDataRef();
        for (int i = 0; i < columnDimension2; i++) {
            for (int i2 = 0; i2 < columnDimension2; i2++) {
                if (i == i2 && i < columnDimension) {
                    dataRef[i][i2] = 1.0d;
                } else {
                    dataRef[i][i2] = 0.0d;
                }
            }
        }
        return q.multiply(array2DRowRealMatrix).multiply(q.transpose());
    }

    public double calculateTotalSumOfSquares() {
        if (isNoIntercept()) {
            return org.apache.commons.math.stat.StatUtils.sumSq(this.Y.getData());
        }
        return new org.apache.commons.math.stat.descriptive.moment.SecondMoment().evaluate(this.Y.getData());
    }

    public double calculateResidualSumOfSquares() {
        org.apache.commons.math.linear.RealVector calculateResiduals = calculateResiduals();
        return calculateResiduals.dotProduct(calculateResiduals);
    }

    public double calculateRSquared() {
        return 1.0d - (calculateResidualSumOfSquares() / calculateTotalSumOfSquares());
    }

    public double calculateAdjustedRSquared() {
        double rowDimension = this.X.getRowDimension();
        if (isNoIntercept()) {
            return 1.0d - ((1.0d - calculateRSquared()) * (rowDimension / (rowDimension - this.X.getColumnDimension())));
        }
        return 1.0d - ((calculateResidualSumOfSquares() * (rowDimension - 1.0d)) / (calculateTotalSumOfSquares() * (rowDimension - this.X.getColumnDimension())));
    }

    @Override // org.apache.commons.math.stat.regression.AbstractMultipleLinearRegression
    protected void newXSampleData(double[][] dArr) {
        super.newXSampleData(dArr);
        this.qr = new org.apache.commons.math.linear.QRDecompositionImpl(this.X);
    }

    @Override // org.apache.commons.math.stat.regression.AbstractMultipleLinearRegression
    protected org.apache.commons.math.linear.RealVector calculateBeta() {
        return this.qr.getSolver().solve(this.Y);
    }

    @Override // org.apache.commons.math.stat.regression.AbstractMultipleLinearRegression
    protected org.apache.commons.math.linear.RealMatrix calculateBetaVariance() {
        int columnDimension = this.X.getColumnDimension() - 1;
        org.apache.commons.math.linear.RealMatrix inverse = new org.apache.commons.math.linear.LUDecompositionImpl(this.qr.getR().getSubMatrix(0, columnDimension, 0, columnDimension)).getSolver().getInverse();
        return inverse.multiply(inverse.transpose());
    }
}
