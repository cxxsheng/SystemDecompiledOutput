package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class NonSquareMatrixException extends org.apache.commons.math.linear.InvalidMatrixException {
    private static final long serialVersionUID = 8996207526636673730L;

    public NonSquareMatrixException(int i, int i2) {
        super(org.apache.commons.math.exception.util.LocalizedFormats.NON_SQUARE_MATRIX, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
    }
}
