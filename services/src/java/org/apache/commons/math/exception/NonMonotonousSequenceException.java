package org.apache.commons.math.exception;

/* loaded from: classes3.dex */
public class NonMonotonousSequenceException extends org.apache.commons.math.exception.MathIllegalNumberException {
    private static final long serialVersionUID = 3596849179428944575L;
    private final org.apache.commons.math.util.MathUtils.OrderDirection direction;
    private final int index;
    private final java.lang.Number previous;
    private final boolean strict;

    public NonMonotonousSequenceException(java.lang.Number number, java.lang.Number number2, int i) {
        this(number, number2, i, org.apache.commons.math.util.MathUtils.OrderDirection.INCREASING, true);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public NonMonotonousSequenceException(java.lang.Number number, java.lang.Number number2, int i, org.apache.commons.math.util.MathUtils.OrderDirection orderDirection, boolean z) {
        super(r0, number, number2, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i - 1));
        org.apache.commons.math.exception.util.LocalizedFormats localizedFormats;
        if (orderDirection == org.apache.commons.math.util.MathUtils.OrderDirection.INCREASING) {
            if (z) {
                localizedFormats = org.apache.commons.math.exception.util.LocalizedFormats.NOT_STRICTLY_INCREASING_SEQUENCE;
            } else {
                localizedFormats = org.apache.commons.math.exception.util.LocalizedFormats.NOT_INCREASING_SEQUENCE;
            }
        } else if (z) {
            localizedFormats = org.apache.commons.math.exception.util.LocalizedFormats.NOT_STRICTLY_DECREASING_SEQUENCE;
        } else {
            localizedFormats = org.apache.commons.math.exception.util.LocalizedFormats.NOT_DECREASING_SEQUENCE;
        }
        this.direction = orderDirection;
        this.strict = z;
        this.index = i;
        this.previous = number2;
    }

    public org.apache.commons.math.util.MathUtils.OrderDirection getDirection() {
        return this.direction;
    }

    public boolean getStrict() {
        return this.strict;
    }

    public int getIndex() {
        return this.index;
    }

    public java.lang.Number getPrevious() {
        return this.previous;
    }
}
