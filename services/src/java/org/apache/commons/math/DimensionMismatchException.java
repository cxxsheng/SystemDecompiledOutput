package org.apache.commons.math;

/* loaded from: classes3.dex */
public class DimensionMismatchException extends org.apache.commons.math.MathException {
    private static final long serialVersionUID = -1316089546353786411L;
    private final int dimension1;
    private final int dimension2;

    public DimensionMismatchException(int i, int i2) {
        super(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        this.dimension1 = i;
        this.dimension2 = i2;
    }

    public int getDimension1() {
        return this.dimension1;
    }

    public int getDimension2() {
        return this.dimension2;
    }
}
