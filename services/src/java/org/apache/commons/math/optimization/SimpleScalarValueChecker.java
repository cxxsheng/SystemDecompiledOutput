package org.apache.commons.math.optimization;

/* loaded from: classes3.dex */
public class SimpleScalarValueChecker implements org.apache.commons.math.optimization.RealConvergenceChecker {
    private static final double DEFAULT_ABSOLUTE_THRESHOLD = 2.2250738585072014E-306d;
    private static final double DEFAULT_RELATIVE_THRESHOLD = 1.1102230246251565E-14d;
    private final double absoluteThreshold;
    private final double relativeThreshold;

    public SimpleScalarValueChecker() {
        this.relativeThreshold = DEFAULT_RELATIVE_THRESHOLD;
        this.absoluteThreshold = DEFAULT_ABSOLUTE_THRESHOLD;
    }

    public SimpleScalarValueChecker(double d, double d2) {
        this.relativeThreshold = d;
        this.absoluteThreshold = d2;
    }

    @Override // org.apache.commons.math.optimization.RealConvergenceChecker
    public boolean converged(int i, org.apache.commons.math.optimization.RealPointValuePair realPointValuePair, org.apache.commons.math.optimization.RealPointValuePair realPointValuePair2) {
        double value = realPointValuePair.getValue();
        double value2 = realPointValuePair2.getValue();
        double abs = org.apache.commons.math.util.FastMath.abs(value - value2);
        return abs <= org.apache.commons.math.util.FastMath.max(org.apache.commons.math.util.FastMath.abs(value), org.apache.commons.math.util.FastMath.abs(value2)) * this.relativeThreshold || abs <= this.absoluteThreshold;
    }
}
