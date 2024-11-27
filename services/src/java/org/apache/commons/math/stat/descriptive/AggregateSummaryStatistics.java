package org.apache.commons.math.stat.descriptive;

/* loaded from: classes3.dex */
public class AggregateSummaryStatistics implements org.apache.commons.math.stat.descriptive.StatisticalSummary, java.io.Serializable {
    private static final long serialVersionUID = -8207112444016386906L;
    private final org.apache.commons.math.stat.descriptive.SummaryStatistics statistics;
    private final org.apache.commons.math.stat.descriptive.SummaryStatistics statisticsPrototype;

    public AggregateSummaryStatistics() {
        this(new org.apache.commons.math.stat.descriptive.SummaryStatistics());
    }

    public AggregateSummaryStatistics(org.apache.commons.math.stat.descriptive.SummaryStatistics summaryStatistics) {
        this(summaryStatistics, summaryStatistics == null ? null : new org.apache.commons.math.stat.descriptive.SummaryStatistics(summaryStatistics));
    }

    public AggregateSummaryStatistics(org.apache.commons.math.stat.descriptive.SummaryStatistics summaryStatistics, org.apache.commons.math.stat.descriptive.SummaryStatistics summaryStatistics2) {
        this.statisticsPrototype = summaryStatistics == null ? new org.apache.commons.math.stat.descriptive.SummaryStatistics() : summaryStatistics;
        this.statistics = summaryStatistics2 == null ? new org.apache.commons.math.stat.descriptive.SummaryStatistics() : summaryStatistics2;
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getMax() {
        double max;
        synchronized (this.statistics) {
            max = this.statistics.getMax();
        }
        return max;
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getMean() {
        double mean;
        synchronized (this.statistics) {
            mean = this.statistics.getMean();
        }
        return mean;
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getMin() {
        double min;
        synchronized (this.statistics) {
            min = this.statistics.getMin();
        }
        return min;
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public long getN() {
        long n;
        synchronized (this.statistics) {
            n = this.statistics.getN();
        }
        return n;
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getStandardDeviation() {
        double standardDeviation;
        synchronized (this.statistics) {
            standardDeviation = this.statistics.getStandardDeviation();
        }
        return standardDeviation;
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getSum() {
        double sum;
        synchronized (this.statistics) {
            sum = this.statistics.getSum();
        }
        return sum;
    }

    @Override // org.apache.commons.math.stat.descriptive.StatisticalSummary
    public double getVariance() {
        double variance;
        synchronized (this.statistics) {
            variance = this.statistics.getVariance();
        }
        return variance;
    }

    public double getSumOfLogs() {
        double sumOfLogs;
        synchronized (this.statistics) {
            sumOfLogs = this.statistics.getSumOfLogs();
        }
        return sumOfLogs;
    }

    public double getGeometricMean() {
        double geometricMean;
        synchronized (this.statistics) {
            geometricMean = this.statistics.getGeometricMean();
        }
        return geometricMean;
    }

    public double getSumsq() {
        double sumsq;
        synchronized (this.statistics) {
            sumsq = this.statistics.getSumsq();
        }
        return sumsq;
    }

    public double getSecondMoment() {
        double secondMoment;
        synchronized (this.statistics) {
            secondMoment = this.statistics.getSecondMoment();
        }
        return secondMoment;
    }

    public org.apache.commons.math.stat.descriptive.StatisticalSummary getSummary() {
        org.apache.commons.math.stat.descriptive.StatisticalSummaryValues statisticalSummaryValues;
        synchronized (this.statistics) {
            statisticalSummaryValues = new org.apache.commons.math.stat.descriptive.StatisticalSummaryValues(getMean(), getVariance(), getN(), getMax(), getMin(), getSum());
        }
        return statisticalSummaryValues;
    }

    public org.apache.commons.math.stat.descriptive.SummaryStatistics createContributingStatistics() {
        org.apache.commons.math.stat.descriptive.AggregateSummaryStatistics.AggregatingSummaryStatistics aggregatingSummaryStatistics = new org.apache.commons.math.stat.descriptive.AggregateSummaryStatistics.AggregatingSummaryStatistics(this.statistics);
        org.apache.commons.math.stat.descriptive.SummaryStatistics.copy(this.statisticsPrototype, aggregatingSummaryStatistics);
        return aggregatingSummaryStatistics;
    }

    public static org.apache.commons.math.stat.descriptive.StatisticalSummaryValues aggregate(java.util.Collection<org.apache.commons.math.stat.descriptive.SummaryStatistics> collection) {
        double d;
        if (collection == null) {
            return null;
        }
        java.util.Iterator<org.apache.commons.math.stat.descriptive.SummaryStatistics> it = collection.iterator();
        if (!it.hasNext()) {
            return null;
        }
        org.apache.commons.math.stat.descriptive.SummaryStatistics next = it.next();
        long n = next.getN();
        double min = next.getMin();
        double sum = next.getSum();
        double max = next.getMax();
        double secondMoment = next.getSecondMoment();
        double d2 = min;
        double d3 = sum;
        double d4 = max;
        double mean = next.getMean();
        while (it.hasNext()) {
            org.apache.commons.math.stat.descriptive.SummaryStatistics next2 = it.next();
            if (next2.getMin() < d2 || java.lang.Double.isNaN(d2)) {
                d2 = next2.getMin();
            }
            if (next2.getMax() > d4 || java.lang.Double.isNaN(d4)) {
                d4 = next2.getMax();
            }
            d3 += next2.getSum();
            double d5 = n;
            double n2 = next2.getN();
            long j = (long) (d5 + n2);
            double mean2 = next2.getMean() - mean;
            double d6 = j;
            mean = d3 / d6;
            secondMoment = secondMoment + next2.getSecondMoment() + ((((mean2 * mean2) * d5) * n2) / d6);
            n = j;
        }
        if (n == 0) {
            d = Double.NaN;
        } else if (n == 1) {
            d = 0.0d;
        } else {
            d = secondMoment / (n - 1);
        }
        return new org.apache.commons.math.stat.descriptive.StatisticalSummaryValues(mean, d, n, d4, d2, d3);
    }

    private static class AggregatingSummaryStatistics extends org.apache.commons.math.stat.descriptive.SummaryStatistics {
        private static final long serialVersionUID = 1;
        private final org.apache.commons.math.stat.descriptive.SummaryStatistics aggregateStatistics;

        public AggregatingSummaryStatistics(org.apache.commons.math.stat.descriptive.SummaryStatistics summaryStatistics) {
            this.aggregateStatistics = summaryStatistics;
        }

        @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
        public void addValue(double d) {
            super.addValue(d);
            synchronized (this.aggregateStatistics) {
                this.aggregateStatistics.addValue(d);
            }
        }

        @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
        public boolean equals(java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof org.apache.commons.math.stat.descriptive.AggregateSummaryStatistics.AggregatingSummaryStatistics)) {
                return false;
            }
            org.apache.commons.math.stat.descriptive.AggregateSummaryStatistics.AggregatingSummaryStatistics aggregatingSummaryStatistics = (org.apache.commons.math.stat.descriptive.AggregateSummaryStatistics.AggregatingSummaryStatistics) obj;
            return super.equals(aggregatingSummaryStatistics) && this.aggregateStatistics.equals(aggregatingSummaryStatistics.aggregateStatistics);
        }

        @Override // org.apache.commons.math.stat.descriptive.SummaryStatistics
        public int hashCode() {
            return super.hashCode() + 123 + this.aggregateStatistics.hashCode();
        }
    }
}
