package org.apache.commons.math;

/* loaded from: classes3.dex */
public class MaxIterationsExceededException extends org.apache.commons.math.ConvergenceException {
    private static final long serialVersionUID = -7821226672760574694L;
    private final int maxIterations;

    public MaxIterationsExceededException(int i) {
        super(org.apache.commons.math.exception.util.LocalizedFormats.MAX_ITERATIONS_EXCEEDED, java.lang.Integer.valueOf(i));
        this.maxIterations = i;
    }

    @java.lang.Deprecated
    public MaxIterationsExceededException(int i, java.lang.String str, java.lang.Object... objArr) {
        this(i, new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public MaxIterationsExceededException(int i, org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        super(localizable, objArr);
        this.maxIterations = i;
    }

    public int getMaxIterations() {
        return this.maxIterations;
    }
}
