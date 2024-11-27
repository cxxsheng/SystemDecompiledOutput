package org.apache.commons.math.stat.descriptive;

/* loaded from: classes3.dex */
public class SynchronizedSummaryStatistics extends org.apache.commons.math.stat.descriptive.SummaryStatistics {
    private static final long serialVersionUID = 1909861009042253704L;

    public SynchronizedSummaryStatistics() {
    }

    public SynchronizedSummaryStatistics(org.apache.commons.math.stat.descriptive.SynchronizedSummaryStatistics synchronizedSummaryStatistics) {
        copy(synchronizedSummaryStatistics, this);
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized org.apache.commons.math.stat.descriptive.StatisticalSummary getSummary() {
        return super.getSummary();
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized void addValue(double d) {
        super.addValue(d);
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics, org.apache.commons.math.stat.descriptive.StatisticalSummary
    public synchronized long getN() {
        return super.getN();
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics, org.apache.commons.math.stat.descriptive.StatisticalSummary
    public synchronized double getSum() {
        return super.getSum();
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized double getSumsq() {
        return super.getSumsq();
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics, org.apache.commons.math.stat.descriptive.StatisticalSummary
    public synchronized double getMean() {
        return super.getMean();
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics, org.apache.commons.math.stat.descriptive.StatisticalSummary
    public synchronized double getStandardDeviation() {
        return super.getStandardDeviation();
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics, org.apache.commons.math.stat.descriptive.StatisticalSummary
    public synchronized double getVariance() {
        return super.getVariance();
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics, org.apache.commons.math.stat.descriptive.StatisticalSummary
    public synchronized double getMax() {
        return super.getMax();
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics, org.apache.commons.math.stat.descriptive.StatisticalSummary
    public synchronized double getMin() {
        return super.getMin();
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized double getGeometricMean() {
        return super.getGeometricMean();
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized java.lang.String toString() {
        return super.toString();
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized void clear() {
        super.clear();
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized boolean equals(java.lang.Object obj) {
        return super.equals(obj);
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized int hashCode() {
        return super.hashCode();
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getSumImpl() {
        return super.getSumImpl();
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized void setSumImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic storelessUnivariateStatistic) {
        super.setSumImpl(storelessUnivariateStatistic);
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getSumsqImpl() {
        return super.getSumsqImpl();
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized void setSumsqImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic storelessUnivariateStatistic) {
        super.setSumsqImpl(storelessUnivariateStatistic);
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getMinImpl() {
        return super.getMinImpl();
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized void setMinImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic storelessUnivariateStatistic) {
        super.setMinImpl(storelessUnivariateStatistic);
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getMaxImpl() {
        return super.getMaxImpl();
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized void setMaxImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic storelessUnivariateStatistic) {
        super.setMaxImpl(storelessUnivariateStatistic);
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getSumLogImpl() {
        return super.getSumLogImpl();
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized void setSumLogImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic storelessUnivariateStatistic) {
        super.setSumLogImpl(storelessUnivariateStatistic);
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getGeoMeanImpl() {
        return super.getGeoMeanImpl();
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized void setGeoMeanImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic storelessUnivariateStatistic) {
        super.setGeoMeanImpl(storelessUnivariateStatistic);
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getMeanImpl() {
        return super.getMeanImpl();
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized void setMeanImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic storelessUnivariateStatistic) {
        super.setMeanImpl(storelessUnivariateStatistic);
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getVarianceImpl() {
        return super.getVarianceImpl();
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized void setVarianceImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic storelessUnivariateStatistic) {
        super.setVarianceImpl(storelessUnivariateStatistic);
    }

    @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
    public synchronized org.apache.commons.math.stat.descriptive.SynchronizedSummaryStatistics copy() {
        org.apache.commons.math.stat.descriptive.SynchronizedSummaryStatistics synchronizedSummaryStatistics;
        synchronizedSummaryStatistics = new org.apache.commons.math.stat.descriptive.SynchronizedSummaryStatistics();
        copy(this, synchronizedSummaryStatistics);
        return synchronizedSummaryStatistics;
    }

    public static void copy(org.apache.commons.math.stat.descriptive.SynchronizedSummaryStatistics synchronizedSummaryStatistics, org.apache.commons.math.stat.descriptive.SynchronizedSummaryStatistics synchronizedSummaryStatistics2) {
        synchronized (synchronizedSummaryStatistics) {
            synchronized (synchronizedSummaryStatistics2) {
                org.apache.commons.math.stat.descriptive.SummaryStatistics.copy(synchronizedSummaryStatistics, synchronizedSummaryStatistics2);
            }
        }
    }
}
