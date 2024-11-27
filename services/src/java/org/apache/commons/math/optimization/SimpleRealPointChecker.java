package org.apache.commons.math.optimization;

/* loaded from: classes3.dex */
public class SimpleRealPointChecker implements org.apache.commons.math.optimization.RealConvergenceChecker {
    private static final double DEFAULT_ABSOLUTE_THRESHOLD = 2.2250738585072014E-306d;
    private static final double DEFAULT_RELATIVE_THRESHOLD = 1.1102230246251565E-14d;
    private final double absoluteThreshold;
    private final double relativeThreshold;

    public SimpleRealPointChecker() {
        this.relativeThreshold = DEFAULT_RELATIVE_THRESHOLD;
        this.absoluteThreshold = DEFAULT_ABSOLUTE_THRESHOLD;
    }

    public SimpleRealPointChecker(double d, double d2) {
        this.relativeThreshold = d;
        this.absoluteThreshold = d2;
    }

    @Override // org.apache.commons.math.optimization.RealConvergenceChecker
    public boolean converged(int i, org.apache.commons.math.optimization.RealPointValuePair realPointValuePair, org.apache.commons.math.optimization.RealPointValuePair realPointValuePair2) {
        double[] point = realPointValuePair.getPoint();
        double[] point2 = realPointValuePair2.getPoint();
        for (int i2 = 0; i2 < point.length; i2++) {
            double abs = org.apache.commons.math.util.FastMath.abs(point[i2] - point2[i2]);
            if (abs > org.apache.commons.math.util.FastMath.max(org.apache.commons.math.util.FastMath.abs(point[i2]), org.apache.commons.math.util.FastMath.abs(point2[i2])) * this.relativeThreshold && abs > this.absoluteThreshold) {
                return false;
            }
        }
        return true;
    }
}
