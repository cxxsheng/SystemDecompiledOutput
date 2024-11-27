package org.apache.commons.math.analysis.interpolation;

/* loaded from: classes3.dex */
public class MicrosphereInterpolator implements org.apache.commons.math.analysis.interpolation.MultivariateRealInterpolator {
    public static final int DEFAULT_BRIGHTNESS_EXPONENT = 2;
    public static final int DEFAULT_MICROSPHERE_ELEMENTS = 2000;
    private int brightnessExponent;
    private int microsphereElements;

    public MicrosphereInterpolator() {
        this(2000, 2);
    }

    public MicrosphereInterpolator(int i, int i2) {
        setMicropshereElements(i);
        setBrightnessExponent(i2);
    }

    @Override // org.apache.commons.math.analysis.interpolation.MultivariateRealInterpolator
    public org.apache.commons.math.analysis.MultivariateRealFunction interpolate(double[][] dArr, double[] dArr2) throws org.apache.commons.math.MathException, java.lang.IllegalArgumentException {
        return new org.apache.commons.math.analysis.interpolation.MicrosphereInterpolatingFunction(dArr, dArr2, this.brightnessExponent, this.microsphereElements, new org.apache.commons.math.random.UnitSphereRandomVectorGenerator(dArr[0].length));
    }

    public void setBrightnessExponent(int i) {
        if (i < 0) {
            throw new org.apache.commons.math.exception.NotPositiveException(java.lang.Integer.valueOf(i));
        }
        this.brightnessExponent = i;
    }

    public void setMicropshereElements(int i) {
        if (i <= 0) {
            throw new org.apache.commons.math.exception.NotStrictlyPositiveException(java.lang.Integer.valueOf(i));
        }
        this.microsphereElements = i;
    }
}
