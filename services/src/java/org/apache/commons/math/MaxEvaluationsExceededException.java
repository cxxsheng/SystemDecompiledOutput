package org.apache.commons.math;

/* loaded from: classes3.dex */
public class MaxEvaluationsExceededException extends org.apache.commons.math.ConvergenceException {
    private static final long serialVersionUID = -5921271447220129118L;
    private final int maxEvaluations;

    public MaxEvaluationsExceededException(int i) {
        super(org.apache.commons.math.exception.util.LocalizedFormats.MAX_EVALUATIONS_EXCEEDED, java.lang.Integer.valueOf(i));
        this.maxEvaluations = i;
    }

    @java.lang.Deprecated
    public MaxEvaluationsExceededException(int i, java.lang.String str, java.lang.Object... objArr) {
        this(i, new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public MaxEvaluationsExceededException(int i, org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        super(localizable, objArr);
        this.maxEvaluations = i;
    }

    public int getMaxEvaluations() {
        return this.maxEvaluations;
    }
}
