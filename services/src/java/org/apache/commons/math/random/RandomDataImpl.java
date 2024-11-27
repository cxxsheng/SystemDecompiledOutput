package org.apache.commons.math.random;

/* loaded from: classes3.dex */
public class RandomDataImpl implements org.apache.commons.math.random.RandomData, java.io.Serializable {
    private static final long serialVersionUID = -626730818244969716L;
    private org.apache.commons.math.random.RandomGenerator rand;
    private java.security.SecureRandom secRand;

    public RandomDataImpl() {
        this.rand = null;
        this.secRand = null;
    }

    public RandomDataImpl(org.apache.commons.math.random.RandomGenerator randomGenerator) {
        this.rand = null;
        this.secRand = null;
        this.rand = randomGenerator;
    }

    @Override // org.apache.commons.math.random.RandomData
    public java.lang.String nextHexString(int i) {
        if (i <= 0) {
            throw new org.apache.commons.math.exception.NotStrictlyPositiveException(org.apache.commons.math.exception.util.LocalizedFormats.LENGTH, java.lang.Integer.valueOf(i));
        }
        org.apache.commons.math.random.RandomGenerator ran = getRan();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        int i2 = (i / 2) + 1;
        byte[] bArr = new byte[i2];
        ran.nextBytes(bArr);
        for (int i3 = 0; i3 < i2; i3++) {
            java.lang.String hexString = java.lang.Integer.toHexString(java.lang.Integer.valueOf(bArr[i3]).intValue() + 128);
            if (hexString.length() == 1) {
                hexString = "0" + hexString;
            }
            sb.append(hexString);
        }
        return sb.toString().substring(0, i);
    }

    @Override // org.apache.commons.math.random.RandomData
    public int nextInt(int i, int i2) {
        if (i >= i2) {
            throw new org.apache.commons.math.exception.NumberIsTooLargeException(org.apache.commons.math.exception.util.LocalizedFormats.LOWER_BOUND_NOT_BELOW_UPPER_BOUND, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), false);
        }
        double nextDouble = getRan().nextDouble();
        return (int) ((i2 * nextDouble) + ((1.0d - nextDouble) * i) + nextDouble);
    }

    @Override // org.apache.commons.math.random.RandomData
    public long nextLong(long j, long j2) {
        if (j >= j2) {
            throw new org.apache.commons.math.exception.NumberIsTooLargeException(org.apache.commons.math.exception.util.LocalizedFormats.LOWER_BOUND_NOT_BELOW_UPPER_BOUND, java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2), false);
        }
        double nextDouble = getRan().nextDouble();
        return (long) ((j2 * nextDouble) + ((1.0d - nextDouble) * j) + nextDouble);
    }

    @Override // org.apache.commons.math.random.RandomData
    public java.lang.String nextSecureHexString(int i) {
        if (i <= 0) {
            throw new org.apache.commons.math.exception.NotStrictlyPositiveException(org.apache.commons.math.exception.util.LocalizedFormats.LENGTH, java.lang.Integer.valueOf(i));
        }
        java.security.SecureRandom secRan = getSecRan();
        try {
            java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance("SHA-1");
            messageDigest.reset();
            int i2 = (i / 40) + 1;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            int i3 = 1;
            while (true) {
                if (i3 < i2 + 1) {
                    byte[] bArr = new byte[40];
                    secRan.nextBytes(bArr);
                    messageDigest.update(bArr);
                    for (byte b : messageDigest.digest()) {
                        java.lang.String hexString = java.lang.Integer.toHexString(java.lang.Integer.valueOf(b).intValue() + 128);
                        if (hexString.length() == 1) {
                            hexString = "0" + hexString;
                        }
                        sb.append(hexString);
                    }
                    i3++;
                } else {
                    return sb.toString().substring(0, i);
                }
            }
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new org.apache.commons.math.exception.MathInternalError(e);
        }
    }

    @Override // org.apache.commons.math.random.RandomData
    public int nextSecureInt(int i, int i2) {
        if (i >= i2) {
            throw new org.apache.commons.math.exception.NumberIsTooLargeException(org.apache.commons.math.exception.util.LocalizedFormats.LOWER_BOUND_NOT_BELOW_UPPER_BOUND, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), false);
        }
        return i + ((int) (getSecRan().nextDouble() * ((i2 - i) + 1)));
    }

    @Override // org.apache.commons.math.random.RandomData
    public long nextSecureLong(long j, long j2) {
        if (j >= j2) {
            throw new org.apache.commons.math.exception.NumberIsTooLargeException(org.apache.commons.math.exception.util.LocalizedFormats.LOWER_BOUND_NOT_BELOW_UPPER_BOUND, java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2), false);
        }
        return j + ((long) (getSecRan().nextDouble() * ((j2 - j) + 1)));
    }

    @Override // org.apache.commons.math.random.RandomData
    public long nextPoisson(double d) {
        long j;
        double d2;
        double d3;
        double d4;
        double d5;
        org.apache.commons.math.random.RandomDataImpl randomDataImpl = this;
        if (d <= 0.0d) {
            throw new org.apache.commons.math.exception.NotStrictlyPositiveException(org.apache.commons.math.exception.util.LocalizedFormats.MEAN, java.lang.Double.valueOf(d));
        }
        org.apache.commons.math.random.RandomGenerator ran = getRan();
        double d6 = 1.0d;
        if (d < 40.0d) {
            double exp = org.apache.commons.math.util.FastMath.exp(-d);
            while (r7 < 1000.0d * d) {
                d6 *= ran.nextDouble();
                if (d6 >= exp) {
                    r7++;
                } else {
                    return r7;
                }
            }
            return r7;
        }
        double floor = org.apache.commons.math.util.FastMath.floor(d);
        double d7 = d - floor;
        double log = org.apache.commons.math.util.FastMath.log(floor);
        double factorialLog = org.apache.commons.math.util.MathUtils.factorialLog((int) floor);
        r7 = d7 >= Double.MIN_VALUE ? randomDataImpl.nextPoisson(d7) : 0L;
        double sqrt = org.apache.commons.math.util.FastMath.sqrt(org.apache.commons.math.util.FastMath.log(((32.0d * floor) / 3.141592653589793d) + 1.0d) * floor);
        double d8 = sqrt / 2.0d;
        double d9 = floor * 2.0d;
        double d10 = d9 + sqrt;
        double sqrt2 = org.apache.commons.math.util.FastMath.sqrt(3.141592653589793d * d10) * org.apache.commons.math.util.FastMath.exp(floor * 0.0d);
        double d11 = d10 / sqrt;
        double exp2 = org.apache.commons.math.util.FastMath.exp(((-sqrt) * (sqrt + 1.0d)) / d10) * d11;
        double d12 = sqrt2 + exp2 + 1.0d;
        double d13 = sqrt2 / d12;
        double d14 = exp2 / d12;
        double d15 = 1.0d / (8.0d * floor);
        while (true) {
            j = r7;
            double nextUniform = randomDataImpl.nextUniform(0.0d, d6);
            if (nextUniform <= d13) {
                double nextGaussian = randomDataImpl.nextGaussian(0.0d, d6);
                double sqrt3 = (org.apache.commons.math.util.FastMath.sqrt(floor + d8) * nextGaussian) - 0.5d;
                if (sqrt3 > sqrt) {
                    r7 = j;
                    d6 = 1.0d;
                } else if (sqrt3 < (-floor)) {
                    r7 = j;
                    d6 = 1.0d;
                } else {
                    d5 = sqrt3 < 0.0d ? org.apache.commons.math.util.FastMath.floor(sqrt3) : org.apache.commons.math.util.FastMath.ceil(sqrt3);
                    d2 = d14;
                    d3 = ((-randomDataImpl.nextExponential(1.0d)) - ((nextGaussian * nextGaussian) / 2.0d)) + d15;
                    d4 = sqrt3;
                }
            } else {
                if (nextUniform > d13 + d14) {
                    break;
                }
                double nextExponential = (randomDataImpl.nextExponential(1.0d) * d11) + sqrt;
                double ceil = org.apache.commons.math.util.FastMath.ceil(nextExponential);
                d2 = d14;
                d3 = (-randomDataImpl.nextExponential(1.0d)) - (((nextExponential + 1.0d) * sqrt) / d10);
                d4 = nextExponential;
                d5 = ceil;
            }
            int i = d4 < 0.0d ? 1 : 0;
            double d16 = d5 + 1.0d;
            double d17 = sqrt;
            double d18 = (d5 * d16) / d9;
            double d19 = factorialLog;
            if (d3 < (-d18) && i == 0) {
                floor += d5;
                break;
            }
            double d20 = ((((d5 * 2.0d) + 1.0d) / (6.0d * floor)) - 1.0d) * d18;
            if (d3 < d20 - ((d18 * d18) / (((i * d16) + floor) * 3.0d))) {
                floor += d5;
                break;
            }
            if (d3 > d20) {
                randomDataImpl = this;
                sqrt = d17;
                r7 = j;
                factorialLog = d19;
                d6 = 1.0d;
                d14 = d2;
            } else {
                double d21 = d5 * log;
                double d22 = d5 + floor;
                if (d3 < (d21 - org.apache.commons.math.util.MathUtils.factorialLog((int) d22)) + d19) {
                    floor = d22;
                    break;
                }
                randomDataImpl = this;
                sqrt = d17;
                r7 = j;
                factorialLog = d19;
                d6 = 1.0d;
                d14 = d2;
            }
        }
        return j + ((long) floor);
    }

    @Override // org.apache.commons.math.random.RandomData
    public double nextGaussian(double d, double d2) {
        if (d2 <= 0.0d) {
            throw new org.apache.commons.math.exception.NotStrictlyPositiveException(org.apache.commons.math.exception.util.LocalizedFormats.STANDARD_DEVIATION, java.lang.Double.valueOf(d2));
        }
        return (d2 * getRan().nextGaussian()) + d;
    }

    @Override // org.apache.commons.math.random.RandomData
    public double nextExponential(double d) {
        if (d <= 0.0d) {
            throw new org.apache.commons.math.exception.NotStrictlyPositiveException(org.apache.commons.math.exception.util.LocalizedFormats.MEAN, java.lang.Double.valueOf(d));
        }
        org.apache.commons.math.random.RandomGenerator ran = getRan();
        double nextDouble = ran.nextDouble();
        while (nextDouble == 0.0d) {
            nextDouble = ran.nextDouble();
        }
        return (-d) * org.apache.commons.math.util.FastMath.log(nextDouble);
    }

    @Override // org.apache.commons.math.random.RandomData
    public double nextUniform(double d, double d2) {
        if (d >= d2) {
            throw new org.apache.commons.math.exception.NumberIsTooLargeException(org.apache.commons.math.exception.util.LocalizedFormats.LOWER_BOUND_NOT_BELOW_UPPER_BOUND, java.lang.Double.valueOf(d), java.lang.Double.valueOf(d2), false);
        }
        org.apache.commons.math.random.RandomGenerator ran = getRan();
        double nextDouble = ran.nextDouble();
        while (nextDouble <= 0.0d) {
            nextDouble = ran.nextDouble();
        }
        return d + (nextDouble * (d2 - d));
    }

    public double nextBeta(double d, double d2) throws org.apache.commons.math.MathException {
        return nextInversionDeviate(new org.apache.commons.math.distribution.BetaDistributionImpl(d, d2));
    }

    public int nextBinomial(int i, double d) throws org.apache.commons.math.MathException {
        return nextInversionDeviate(new org.apache.commons.math.distribution.BinomialDistributionImpl(i, d));
    }

    public double nextCauchy(double d, double d2) throws org.apache.commons.math.MathException {
        return nextInversionDeviate(new org.apache.commons.math.distribution.CauchyDistributionImpl(d, d2));
    }

    public double nextChiSquare(double d) throws org.apache.commons.math.MathException {
        return nextInversionDeviate(new org.apache.commons.math.distribution.ChiSquaredDistributionImpl(d));
    }

    public double nextF(double d, double d2) throws org.apache.commons.math.MathException {
        return nextInversionDeviate(new org.apache.commons.math.distribution.FDistributionImpl(d, d2));
    }

    public double nextGamma(double d, double d2) throws org.apache.commons.math.MathException {
        return nextInversionDeviate(new org.apache.commons.math.distribution.GammaDistributionImpl(d, d2));
    }

    public int nextHypergeometric(int i, int i2, int i3) throws org.apache.commons.math.MathException {
        return nextInversionDeviate(new org.apache.commons.math.distribution.HypergeometricDistributionImpl(i, i2, i3));
    }

    public int nextPascal(int i, double d) throws org.apache.commons.math.MathException {
        return nextInversionDeviate(new org.apache.commons.math.distribution.PascalDistributionImpl(i, d));
    }

    public double nextT(double d) throws org.apache.commons.math.MathException {
        return nextInversionDeviate(new org.apache.commons.math.distribution.TDistributionImpl(d));
    }

    public double nextWeibull(double d, double d2) throws org.apache.commons.math.MathException {
        return nextInversionDeviate(new org.apache.commons.math.distribution.WeibullDistributionImpl(d, d2));
    }

    public int nextZipf(int i, double d) throws org.apache.commons.math.MathException {
        return nextInversionDeviate(new org.apache.commons.math.distribution.ZipfDistributionImpl(i, d));
    }

    private org.apache.commons.math.random.RandomGenerator getRan() {
        if (this.rand == null) {
            this.rand = new org.apache.commons.math.random.JDKRandomGenerator();
            this.rand.setSeed(java.lang.System.currentTimeMillis());
        }
        return this.rand;
    }

    private java.security.SecureRandom getSecRan() {
        if (this.secRand == null) {
            this.secRand = new java.security.SecureRandom();
            this.secRand.setSeed(java.lang.System.currentTimeMillis());
        }
        return this.secRand;
    }

    public void reSeed(long j) {
        if (this.rand == null) {
            this.rand = new org.apache.commons.math.random.JDKRandomGenerator();
        }
        this.rand.setSeed(j);
    }

    public void reSeedSecure() {
        if (this.secRand == null) {
            this.secRand = new java.security.SecureRandom();
        }
        this.secRand.setSeed(java.lang.System.currentTimeMillis());
    }

    public void reSeedSecure(long j) {
        if (this.secRand == null) {
            this.secRand = new java.security.SecureRandom();
        }
        this.secRand.setSeed(j);
    }

    public void reSeed() {
        if (this.rand == null) {
            this.rand = new org.apache.commons.math.random.JDKRandomGenerator();
        }
        this.rand.setSeed(java.lang.System.currentTimeMillis());
    }

    public void setSecureAlgorithm(java.lang.String str, java.lang.String str2) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        this.secRand = java.security.SecureRandom.getInstance(str, str2);
    }

    @Override // org.apache.commons.math.random.RandomData
    public int[] nextPermutation(int i, int i2) {
        if (i2 > i) {
            throw new org.apache.commons.math.exception.NumberIsTooLargeException(org.apache.commons.math.exception.util.LocalizedFormats.PERMUTATION_EXCEEDS_N, java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i), true);
        }
        if (i2 == 0) {
            throw new org.apache.commons.math.exception.NotStrictlyPositiveException(org.apache.commons.math.exception.util.LocalizedFormats.PERMUTATION_SIZE, java.lang.Integer.valueOf(i2));
        }
        int[] natural = getNatural(i);
        shuffle(natural, i - i2);
        int[] iArr = new int[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            iArr[i3] = natural[(i - i3) - 1];
        }
        return iArr;
    }

    @Override // org.apache.commons.math.random.RandomData
    public java.lang.Object[] nextSample(java.util.Collection<?> collection, int i) {
        int size = collection.size();
        if (i > size) {
            throw new org.apache.commons.math.exception.NumberIsTooLargeException(org.apache.commons.math.exception.util.LocalizedFormats.SAMPLE_SIZE_EXCEEDS_COLLECTION_SIZE, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(size), true);
        }
        if (i <= 0) {
            throw new org.apache.commons.math.exception.NotStrictlyPositiveException(org.apache.commons.math.exception.util.LocalizedFormats.NUMBER_OF_SAMPLES, java.lang.Integer.valueOf(i));
        }
        java.lang.Object[] array = collection.toArray();
        int[] nextPermutation = nextPermutation(size, i);
        java.lang.Object[] objArr = new java.lang.Object[i];
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = array[nextPermutation[i2]];
        }
        return objArr;
    }

    public double nextInversionDeviate(org.apache.commons.math.distribution.ContinuousDistribution continuousDistribution) throws org.apache.commons.math.MathException {
        return continuousDistribution.inverseCumulativeProbability(nextUniform(0.0d, 1.0d));
    }

    public int nextInversionDeviate(org.apache.commons.math.distribution.IntegerDistribution integerDistribution) throws org.apache.commons.math.MathException {
        int inverseCumulativeProbability = integerDistribution.inverseCumulativeProbability(nextUniform(0.0d, 1.0d));
        if (integerDistribution.cumulativeProbability(inverseCumulativeProbability) == 1.0d) {
            return inverseCumulativeProbability;
        }
        return inverseCumulativeProbability + 1;
    }

    private void shuffle(int[] iArr, int i) {
        for (int length = iArr.length - 1; length >= i; length--) {
            int i2 = 0;
            if (length != 0) {
                i2 = nextInt(0, length);
            }
            int i3 = iArr[i2];
            iArr[i2] = iArr[length];
            iArr[length] = i3;
        }
    }

    private int[] getNatural(int i) {
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            iArr[i2] = i2;
        }
        return iArr;
    }
}
