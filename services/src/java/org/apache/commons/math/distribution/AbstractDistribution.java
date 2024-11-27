package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public abstract class AbstractDistribution implements org.apache.commons.math.distribution.Distribution, java.io.Serializable {
    private static final long serialVersionUID = -38038050983108802L;

    protected AbstractDistribution() {
    }

    @Override // org.apache.commons.math.distribution.Distribution
    public double cumulativeProbability(double d, double d2) throws org.apache.commons.math.MathException {
        if (d > d2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.LOWER_ENDPOINT_ABOVE_UPPER_ENDPOINT, java.lang.Double.valueOf(d), java.lang.Double.valueOf(d2));
        }
        return cumulativeProbability(d2) - cumulativeProbability(d);
    }
}
