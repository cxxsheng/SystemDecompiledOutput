package org.apache.commons.math.stat.regression;

/* loaded from: classes3.dex */
public class GLSMultipleLinearRegression extends org.apache.commons.math.stat.regression.AbstractMultipleLinearRegression {
    private org.apache.commons.math.linear.RealMatrix Omega;
    private org.apache.commons.math.linear.RealMatrix OmegaInverse;

    public void newSampleData(double[] dArr, double[][] dArr2, double[][] dArr3) {
        validateSampleData(dArr2, dArr);
        newYSampleData(dArr);
        newXSampleData(dArr2);
        validateCovarianceData(dArr2, dArr3);
        newCovarianceData(dArr3);
    }

    protected void newCovarianceData(double[][] dArr) {
        this.Omega = new org.apache.commons.math.linear.Array2DRowRealMatrix(dArr);
        this.OmegaInverse = null;
    }

    protected org.apache.commons.math.linear.RealMatrix getOmegaInverse() {
        if (this.OmegaInverse == null) {
            this.OmegaInverse = new org.apache.commons.math.linear.LUDecompositionImpl(this.Omega).getSolver().getInverse();
        }
        return this.OmegaInverse;
    }

    @Override // org.apache.commons.math.stat.regression.AbstractMultipleLinearRegression
    protected org.apache.commons.math.linear.RealVector calculateBeta() {
        org.apache.commons.math.linear.RealMatrix omegaInverse = getOmegaInverse();
        org.apache.commons.math.linear.RealMatrix transpose = this.X.transpose();
        return new org.apache.commons.math.linear.LUDecompositionImpl(transpose.multiply(omegaInverse).multiply(this.X)).getSolver().getInverse().multiply(transpose).multiply(omegaInverse).operate(this.Y);
    }

    @Override // org.apache.commons.math.stat.regression.AbstractMultipleLinearRegression
    protected org.apache.commons.math.linear.RealMatrix calculateBetaVariance() {
        return new org.apache.commons.math.linear.LUDecompositionImpl(this.X.transpose().multiply(getOmegaInverse()).multiply(this.X)).getSolver().getInverse();
    }

    @Override // org.apache.commons.math.stat.regression.AbstractMultipleLinearRegression
    protected double calculateErrorVariance() {
        org.apache.commons.math.linear.RealVector calculateResiduals = calculateResiduals();
        return calculateResiduals.dotProduct(getOmegaInverse().operate(calculateResiduals)) / (this.X.getRowDimension() - this.X.getColumnDimension());
    }
}
