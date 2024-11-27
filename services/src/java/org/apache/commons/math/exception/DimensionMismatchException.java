package org.apache.commons.math.exception;

/* loaded from: classes3.dex */
public class DimensionMismatchException extends org.apache.commons.math.exception.MathIllegalNumberException {
    private static final long serialVersionUID = -8415396756375798143L;
    private final int dimension;

    public DimensionMismatchException(int i, int i2) {
        super(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        this.dimension = i2;
    }

    public int getDimension() {
        return this.dimension;
    }
}
