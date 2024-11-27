package org.apache.commons.math.stat.descriptive;

/* loaded from: classes3.dex */
public class SynchronizedDescriptiveStatistics extends org.apache.commons.math.stat.descriptive.DescriptiveStatistics {
    private static final long serialVersionUID = 1;

    public SynchronizedDescriptiveStatistics() {
        this(-1);
    }

    public SynchronizedDescriptiveStatistics(int i) {
        super(i);
    }

    public SynchronizedDescriptiveStatistics(org.apache.commons.math.stat.descriptive.SynchronizedDescriptiveStatistics synchronizedDescriptiveStatistics) {
        copy(synchronizedDescriptiveStatistics, this);
    }

    @Override // org.apache.commons.math.stat.descriptive.DescriptiveStatistics
    public synchronized void addValue(double d) {
        super.addValue(d);
    }

    @Override // org.apache.commons.math.stat.descriptive.DescriptiveStatistics
    public synchronized double apply(org.apache.commons.math.stat.descriptive.UnivariateStatistic univariateStatistic) {
        return super.apply(univariateStatistic);
    }

    @Override // org.apache.commons.math.stat.descriptive.DescriptiveStatistics
    public synchronized void clear() {
        super.clear();
    }

    @Override // org.apache.commons.math.stat.descriptive.DescriptiveStatistics
    public synchronized double getElement(int i) {
        return super.getElement(i);
    }

    @Override // org.apache.commons.math.stat.descriptive.DescriptiveStatistics, org.apache.commons.math.stat.descriptive.StatisticalSummary
    public synchronized long getN() {
        return super.getN();
    }

    @Override // org.apache.commons.math.stat.descriptive.DescriptiveStatistics, org.apache.commons.math.stat.descriptive.StatisticalSummary
    public synchronized double getStandardDeviation() {
        return super.getStandardDeviation();
    }

    @Override // org.apache.commons.math.stat.descriptive.DescriptiveStatistics
    public synchronized double[] getValues() {
        return super.getValues();
    }

    @Override // org.apache.commons.math.stat.descriptive.DescriptiveStatistics
    public synchronized int getWindowSize() {
        return super.getWindowSize();
    }

    @Override // org.apache.commons.math.stat.descriptive.DescriptiveStatistics
    public synchronized void setWindowSize(int i) {
        super.setWindowSize(i);
    }

    @Override // org.apache.commons.math.stat.descriptive.DescriptiveStatistics
    public synchronized java.lang.String toString() {
        return super.toString();
    }

    @Override // org.apache.commons.math.stat.descriptive.DescriptiveStatistics
    public synchronized org.apache.commons.math.stat.descriptive.SynchronizedDescriptiveStatistics copy() {
        org.apache.commons.math.stat.descriptive.SynchronizedDescriptiveStatistics synchronizedDescriptiveStatistics;
        synchronizedDescriptiveStatistics = new org.apache.commons.math.stat.descriptive.SynchronizedDescriptiveStatistics();
        copy(this, synchronizedDescriptiveStatistics);
        return synchronizedDescriptiveStatistics;
    }

    public static void copy(org.apache.commons.math.stat.descriptive.SynchronizedDescriptiveStatistics synchronizedDescriptiveStatistics, org.apache.commons.math.stat.descriptive.SynchronizedDescriptiveStatistics synchronizedDescriptiveStatistics2) {
        synchronized (synchronizedDescriptiveStatistics) {
            synchronized (synchronizedDescriptiveStatistics2) {
                org.apache.commons.math.stat.descriptive.DescriptiveStatistics.copy(synchronizedDescriptiveStatistics, synchronizedDescriptiveStatistics2);
            }
        }
    }
}
