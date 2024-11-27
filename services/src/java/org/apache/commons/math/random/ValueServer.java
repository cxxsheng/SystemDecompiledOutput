package org.apache.commons.math.random;

/* loaded from: classes3.dex */
public class ValueServer {
    public static final int CONSTANT_MODE = 5;
    public static final int DIGEST_MODE = 0;
    public static final int EXPONENTIAL_MODE = 3;
    public static final int GAUSSIAN_MODE = 4;
    public static final int REPLAY_MODE = 1;
    public static final int UNIFORM_MODE = 2;
    private org.apache.commons.math.random.EmpiricalDistribution empiricalDistribution;
    private java.io.BufferedReader filePointer;
    private int mode;
    private double mu;
    private final org.apache.commons.math.random.RandomData randomData;
    private double sigma;
    private java.net.URL valuesFileURL;

    public ValueServer() {
        this.mode = 5;
        this.valuesFileURL = null;
        this.mu = 0.0d;
        this.sigma = 0.0d;
        this.empiricalDistribution = null;
        this.filePointer = null;
        this.randomData = new org.apache.commons.math.random.RandomDataImpl();
    }

    public ValueServer(org.apache.commons.math.random.RandomData randomData) {
        this.mode = 5;
        this.valuesFileURL = null;
        this.mu = 0.0d;
        this.sigma = 0.0d;
        this.empiricalDistribution = null;
        this.filePointer = null;
        this.randomData = randomData;
    }

    public double getNext() throws java.io.IOException {
        switch (this.mode) {
            case 0:
                return getNextDigest();
            case 1:
                return getNextReplay();
            case 2:
                return getNextUniform();
            case 3:
                return getNextExponential();
            case 4:
                return getNextGaussian();
            case 5:
                return this.mu;
            default:
                throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.exception.util.LocalizedFormats.UNKNOWN_MODE, java.lang.Integer.valueOf(this.mode), "DIGEST_MODE", 0, "REPLAY_MODE", 1, "UNIFORM_MODE", 2, "EXPONENTIAL_MODE", 3, "GAUSSIAN_MODE", 4, "CONSTANT_MODE", 5);
        }
    }

    public void fill(double[] dArr) throws java.io.IOException {
        for (int i = 0; i < dArr.length; i++) {
            dArr[i] = getNext();
        }
    }

    public double[] fill(int i) throws java.io.IOException {
        double[] dArr = new double[i];
        for (int i2 = 0; i2 < i; i2++) {
            dArr[i2] = getNext();
        }
        return dArr;
    }

    public void computeDistribution() throws java.io.IOException {
        this.empiricalDistribution = new org.apache.commons.math.random.EmpiricalDistributionImpl();
        this.empiricalDistribution.load(this.valuesFileURL);
    }

    public void computeDistribution(int i) throws java.io.IOException {
        this.empiricalDistribution = new org.apache.commons.math.random.EmpiricalDistributionImpl(i);
        this.empiricalDistribution.load(this.valuesFileURL);
        this.mu = this.empiricalDistribution.getSampleStats().getMean();
        this.sigma = this.empiricalDistribution.getSampleStats().getStandardDeviation();
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int i) {
        this.mode = i;
    }

    public java.net.URL getValuesFileURL() {
        return this.valuesFileURL;
    }

    public void setValuesFileURL(java.lang.String str) throws java.net.MalformedURLException {
        this.valuesFileURL = new java.net.URL(str);
    }

    public void setValuesFileURL(java.net.URL url) {
        this.valuesFileURL = url;
    }

    public org.apache.commons.math.random.EmpiricalDistribution getEmpiricalDistribution() {
        return this.empiricalDistribution;
    }

    public void resetReplayFile() throws java.io.IOException {
        if (this.filePointer != null) {
            try {
                this.filePointer.close();
                this.filePointer = null;
            } catch (java.io.IOException e) {
            }
        }
        this.filePointer = new java.io.BufferedReader(new java.io.InputStreamReader(this.valuesFileURL.openStream()));
    }

    public void closeReplayFile() throws java.io.IOException {
        if (this.filePointer != null) {
            this.filePointer.close();
            this.filePointer = null;
        }
    }

    public double getMu() {
        return this.mu;
    }

    public void setMu(double d) {
        this.mu = d;
    }

    public double getSigma() {
        return this.sigma;
    }

    public void setSigma(double d) {
        this.sigma = d;
    }

    private double getNextDigest() {
        if (this.empiricalDistribution == null || this.empiricalDistribution.getBinStats().size() == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.exception.util.LocalizedFormats.DIGEST_NOT_INITIALIZED, new java.lang.Object[0]);
        }
        return this.empiricalDistribution.getNextValue();
    }

    private double getNextReplay() throws java.io.IOException {
        if (this.filePointer == null) {
            resetReplayFile();
        }
        java.lang.String readLine = this.filePointer.readLine();
        if (readLine == null) {
            closeReplayFile();
            resetReplayFile();
            readLine = this.filePointer.readLine();
            if (readLine == null) {
                throw org.apache.commons.math.MathRuntimeException.createEOFException(org.apache.commons.math.exception.util.LocalizedFormats.URL_CONTAINS_NO_DATA, this.valuesFileURL);
            }
        }
        return java.lang.Double.valueOf(readLine).doubleValue();
    }

    private double getNextUniform() {
        return this.randomData.nextUniform(0.0d, this.mu * 2.0d);
    }

    private double getNextExponential() {
        return this.randomData.nextExponential(this.mu);
    }

    private double getNextGaussian() {
        return this.randomData.nextGaussian(this.mu, this.sigma);
    }
}
