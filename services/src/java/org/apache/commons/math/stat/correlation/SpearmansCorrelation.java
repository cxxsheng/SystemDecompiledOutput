package org.apache.commons.math.stat.correlation;

/* loaded from: classes3.dex */
public class SpearmansCorrelation {
    private final org.apache.commons.math.linear.RealMatrix data;
    private final org.apache.commons.math.stat.correlation.PearsonsCorrelation rankCorrelation;
    private final org.apache.commons.math.stat.ranking.RankingAlgorithm rankingAlgorithm;

    public SpearmansCorrelation(org.apache.commons.math.linear.RealMatrix realMatrix, org.apache.commons.math.stat.ranking.RankingAlgorithm rankingAlgorithm) {
        this.data = realMatrix.copy();
        this.rankingAlgorithm = rankingAlgorithm;
        rankTransform(this.data);
        this.rankCorrelation = new org.apache.commons.math.stat.correlation.PearsonsCorrelation(this.data);
    }

    public SpearmansCorrelation(org.apache.commons.math.linear.RealMatrix realMatrix) {
        this(realMatrix, new org.apache.commons.math.stat.ranking.NaturalRanking());
    }

    public SpearmansCorrelation() {
        this.data = null;
        this.rankingAlgorithm = new org.apache.commons.math.stat.ranking.NaturalRanking();
        this.rankCorrelation = null;
    }

    public org.apache.commons.math.linear.RealMatrix getCorrelationMatrix() {
        return this.rankCorrelation.getCorrelationMatrix();
    }

    public org.apache.commons.math.stat.correlation.PearsonsCorrelation getRankCorrelation() {
        return this.rankCorrelation;
    }

    public org.apache.commons.math.linear.RealMatrix computeCorrelationMatrix(org.apache.commons.math.linear.RealMatrix realMatrix) {
        org.apache.commons.math.linear.RealMatrix copy = realMatrix.copy();
        rankTransform(copy);
        return new org.apache.commons.math.stat.correlation.PearsonsCorrelation().computeCorrelationMatrix(copy);
    }

    public org.apache.commons.math.linear.RealMatrix computeCorrelationMatrix(double[][] dArr) {
        return computeCorrelationMatrix(new org.apache.commons.math.linear.BlockRealMatrix(dArr));
    }

    public double correlation(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException {
        if (dArr.length != dArr2.length) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(dArr2.length));
        }
        if (dArr.length < 2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INSUFFICIENT_DIMENSION, java.lang.Integer.valueOf(dArr.length), 2);
        }
        return new org.apache.commons.math.stat.correlation.PearsonsCorrelation().correlation(this.rankingAlgorithm.rank(dArr), this.rankingAlgorithm.rank(dArr2));
    }

    private void rankTransform(org.apache.commons.math.linear.RealMatrix realMatrix) {
        for (int i = 0; i < realMatrix.getColumnDimension(); i++) {
            realMatrix.setColumn(i, this.rankingAlgorithm.rank(realMatrix.getColumn(i)));
        }
    }
}
