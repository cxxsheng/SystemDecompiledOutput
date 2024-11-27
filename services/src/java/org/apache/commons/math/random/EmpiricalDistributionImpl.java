package org.apache.commons.math.random;

/* loaded from: classes3.dex */
public class EmpiricalDistributionImpl implements java.io.Serializable, org.apache.commons.math.random.EmpiricalDistribution {
    private static final long serialVersionUID = 5729073523949762654L;
    private final int binCount;
    private final java.util.List<org.apache.commons.math.stat.descriptive.SummaryStatistics> binStats;
    private double delta;
    private boolean loaded;
    private double max;
    private double min;
    private final org.apache.commons.math.random.RandomData randomData;
    private org.apache.commons.math.stat.descriptive.SummaryStatistics sampleStats;
    private double[] upperBounds;

    public EmpiricalDistributionImpl() {
        this.sampleStats = null;
        this.max = Double.NEGATIVE_INFINITY;
        this.min = Double.POSITIVE_INFINITY;
        this.delta = 0.0d;
        this.loaded = false;
        this.upperBounds = null;
        this.randomData = new org.apache.commons.math.random.RandomDataImpl();
        this.binCount = 1000;
        this.binStats = new java.util.ArrayList();
    }

    public EmpiricalDistributionImpl(int i) {
        this.sampleStats = null;
        this.max = Double.NEGATIVE_INFINITY;
        this.min = Double.POSITIVE_INFINITY;
        this.delta = 0.0d;
        this.loaded = false;
        this.upperBounds = null;
        this.randomData = new org.apache.commons.math.random.RandomDataImpl();
        this.binCount = i;
        this.binStats = new java.util.ArrayList();
    }

    @Override // org.apache.commons.math.random.EmpiricalDistribution
    public void load(double[] dArr) {
        try {
            new org.apache.commons.math.random.EmpiricalDistributionImpl.ArrayDataAdapter(dArr).computeStats();
            fillBinStats(dArr);
            this.loaded = true;
        } catch (java.io.IOException e) {
            throw new org.apache.commons.math.MathRuntimeException(e);
        }
    }

    @Override // org.apache.commons.math.random.EmpiricalDistribution
    public void load(java.net.URL url) throws java.io.IOException {
        java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(url.openStream()));
        try {
            new org.apache.commons.math.random.EmpiricalDistributionImpl.StreamDataAdapter(bufferedReader).computeStats();
            if (this.sampleStats.getN() == 0) {
                throw org.apache.commons.math.MathRuntimeException.createEOFException(org.apache.commons.math.exception.util.LocalizedFormats.URL_CONTAINS_NO_DATA, url);
            }
            java.io.BufferedReader bufferedReader2 = new java.io.BufferedReader(new java.io.InputStreamReader(url.openStream()));
            try {
                fillBinStats(bufferedReader2);
                this.loaded = true;
                try {
                    bufferedReader2.close();
                } catch (java.io.IOException e) {
                }
            } catch (java.lang.Throwable th) {
                th = th;
                bufferedReader = bufferedReader2;
                try {
                    bufferedReader.close();
                } catch (java.io.IOException e2) {
                }
                throw th;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    @Override // org.apache.commons.math.random.EmpiricalDistribution
    public void load(java.io.File file) throws java.io.IOException {
        java.io.BufferedReader bufferedReader;
        java.io.BufferedReader bufferedReader2 = new java.io.BufferedReader(new java.io.FileReader(file));
        try {
            new org.apache.commons.math.random.EmpiricalDistributionImpl.StreamDataAdapter(bufferedReader2).computeStats();
            bufferedReader = new java.io.BufferedReader(new java.io.FileReader(file));
        } catch (java.lang.Throwable th) {
            th = th;
        }
        try {
            fillBinStats(bufferedReader);
            this.loaded = true;
            try {
                bufferedReader.close();
            } catch (java.io.IOException e) {
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
            bufferedReader2 = bufferedReader;
            try {
                bufferedReader2.close();
            } catch (java.io.IOException e2) {
            }
            throw th;
        }
    }

    private abstract class DataAdapter {
        public abstract void computeBinStats() throws java.io.IOException;

        public abstract void computeStats() throws java.io.IOException;

        private DataAdapter() {
        }
    }

    private class DataAdapterFactory {
        private DataAdapterFactory() {
        }

        public org.apache.commons.math.random.EmpiricalDistributionImpl.DataAdapter getAdapter(java.lang.Object obj) {
            if (obj instanceof java.io.BufferedReader) {
                return org.apache.commons.math.random.EmpiricalDistributionImpl.this.new StreamDataAdapter((java.io.BufferedReader) obj);
            }
            if (obj instanceof double[]) {
                return org.apache.commons.math.random.EmpiricalDistributionImpl.this.new ArrayDataAdapter((double[]) obj);
            }
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INPUT_DATA_FROM_UNSUPPORTED_DATASOURCE, obj.getClass().getName(), java.io.BufferedReader.class.getName(), double[].class.getName());
        }
    }

    private class StreamDataAdapter extends org.apache.commons.math.random.EmpiricalDistributionImpl.DataAdapter {
        private java.io.BufferedReader inputStream;

        public StreamDataAdapter(java.io.BufferedReader bufferedReader) {
            super();
            this.inputStream = bufferedReader;
        }

        @Override // org.apache.commons.math.random.EmpiricalDistributionImpl.DataAdapter
        public void computeBinStats() throws java.io.IOException {
            while (true) {
                java.lang.String readLine = this.inputStream.readLine();
                if (readLine != null) {
                    double parseDouble = java.lang.Double.parseDouble(readLine);
                    ((org.apache.commons.math.stat.descriptive.SummaryStatistics) org.apache.commons.math.random.EmpiricalDistributionImpl.this.binStats.get(org.apache.commons.math.random.EmpiricalDistributionImpl.this.findBin(parseDouble))).addValue(parseDouble);
                } else {
                    this.inputStream.close();
                    this.inputStream = null;
                    return;
                }
            }
        }

        @Override // org.apache.commons.math.random.EmpiricalDistributionImpl.DataAdapter
        public void computeStats() throws java.io.IOException {
            org.apache.commons.math.random.EmpiricalDistributionImpl.this.sampleStats = new org.apache.commons.math.stat.descriptive.SummaryStatistics();
            while (true) {
                java.lang.String readLine = this.inputStream.readLine();
                if (readLine != null) {
                    org.apache.commons.math.random.EmpiricalDistributionImpl.this.sampleStats.addValue(java.lang.Double.valueOf(readLine).doubleValue());
                } else {
                    this.inputStream.close();
                    this.inputStream = null;
                    return;
                }
            }
        }
    }

    private class ArrayDataAdapter extends org.apache.commons.math.random.EmpiricalDistributionImpl.DataAdapter {
        private double[] inputArray;

        public ArrayDataAdapter(double[] dArr) {
            super();
            this.inputArray = dArr;
        }

        @Override // org.apache.commons.math.random.EmpiricalDistributionImpl.DataAdapter
        public void computeStats() throws java.io.IOException {
            org.apache.commons.math.random.EmpiricalDistributionImpl.this.sampleStats = new org.apache.commons.math.stat.descriptive.SummaryStatistics();
            for (int i = 0; i < this.inputArray.length; i++) {
                org.apache.commons.math.random.EmpiricalDistributionImpl.this.sampleStats.addValue(this.inputArray[i]);
            }
        }

        @Override // org.apache.commons.math.random.EmpiricalDistributionImpl.DataAdapter
        public void computeBinStats() throws java.io.IOException {
            for (int i = 0; i < this.inputArray.length; i++) {
                ((org.apache.commons.math.stat.descriptive.SummaryStatistics) org.apache.commons.math.random.EmpiricalDistributionImpl.this.binStats.get(org.apache.commons.math.random.EmpiricalDistributionImpl.this.findBin(this.inputArray[i]))).addValue(this.inputArray[i]);
            }
        }
    }

    private void fillBinStats(java.lang.Object obj) throws java.io.IOException {
        this.min = this.sampleStats.getMin();
        this.max = this.sampleStats.getMax();
        this.delta = (this.max - this.min) / java.lang.Double.valueOf(this.binCount).doubleValue();
        if (!this.binStats.isEmpty()) {
            this.binStats.clear();
        }
        for (int i = 0; i < this.binCount; i++) {
            this.binStats.add(i, new org.apache.commons.math.stat.descriptive.SummaryStatistics());
        }
        new org.apache.commons.math.random.EmpiricalDistributionImpl.DataAdapterFactory().getAdapter(obj).computeBinStats();
        this.upperBounds = new double[this.binCount];
        this.upperBounds[0] = this.binStats.get(0).getN() / this.sampleStats.getN();
        for (int i2 = 1; i2 < this.binCount - 1; i2++) {
            this.upperBounds[i2] = this.upperBounds[i2 - 1] + (this.binStats.get(i2).getN() / this.sampleStats.getN());
        }
        this.upperBounds[this.binCount - 1] = 1.0d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int findBin(double d) {
        return org.apache.commons.math.util.FastMath.min(org.apache.commons.math.util.FastMath.max(((int) org.apache.commons.math.util.FastMath.ceil((d - this.min) / this.delta)) - 1, 0), this.binCount - 1);
    }

    @Override // org.apache.commons.math.random.EmpiricalDistribution
    public double getNextValue() throws java.lang.IllegalStateException {
        if (!this.loaded) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.exception.util.LocalizedFormats.DISTRIBUTION_NOT_LOADED, new java.lang.Object[0]);
        }
        double random = org.apache.commons.math.util.FastMath.random();
        for (int i = 0; i < this.binCount; i++) {
            if (random <= this.upperBounds[i]) {
                org.apache.commons.math.stat.descriptive.SummaryStatistics summaryStatistics = this.binStats.get(i);
                if (summaryStatistics.getN() > 0) {
                    if (summaryStatistics.getStandardDeviation() > 0.0d) {
                        return this.randomData.nextGaussian(summaryStatistics.getMean(), summaryStatistics.getStandardDeviation());
                    }
                    return summaryStatistics.getMean();
                }
            }
        }
        throw new org.apache.commons.math.MathRuntimeException(org.apache.commons.math.exception.util.LocalizedFormats.NO_BIN_SELECTED, new java.lang.Object[0]);
    }

    @Override // org.apache.commons.math.random.EmpiricalDistribution
    public org.apache.commons.math.stat.descriptive.StatisticalSummary getSampleStats() {
        return this.sampleStats;
    }

    @Override // org.apache.commons.math.random.EmpiricalDistribution
    public int getBinCount() {
        return this.binCount;
    }

    @Override // org.apache.commons.math.random.EmpiricalDistribution
    public java.util.List<org.apache.commons.math.stat.descriptive.SummaryStatistics> getBinStats() {
        return this.binStats;
    }

    @Override // org.apache.commons.math.random.EmpiricalDistribution
    public double[] getUpperBounds() {
        double[] dArr = new double[this.binCount];
        dArr[0] = this.min + this.delta;
        for (int i = 1; i < this.binCount - 1; i++) {
            dArr[i] = dArr[i - 1] + this.delta;
        }
        dArr[this.binCount - 1] = this.max;
        return dArr;
    }

    public double[] getGeneratorUpperBounds() {
        int length = this.upperBounds.length;
        double[] dArr = new double[length];
        java.lang.System.arraycopy(this.upperBounds, 0, dArr, 0, length);
        return dArr;
    }

    @Override // org.apache.commons.math.random.EmpiricalDistribution
    public boolean isLoaded() {
        return this.loaded;
    }
}
