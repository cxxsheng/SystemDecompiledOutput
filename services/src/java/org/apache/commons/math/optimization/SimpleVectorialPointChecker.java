package org.apache.commons.math.optimization;

/* loaded from: classes3.dex */
public class SimpleVectorialPointChecker implements org.apache.commons.math.optimization.VectorialConvergenceChecker {
    private static final double DEFAULT_ABSOLUTE_THRESHOLD = 2.2250738585072014E-306d;
    private static final double DEFAULT_RELATIVE_THRESHOLD = 1.1102230246251565E-14d;
    private final double absoluteThreshold;
    private final double relativeThreshold;

    public SimpleVectorialPointChecker() {
        this.relativeThreshold = DEFAULT_RELATIVE_THRESHOLD;
        this.absoluteThreshold = DEFAULT_ABSOLUTE_THRESHOLD;
    }

    public SimpleVectorialPointChecker(double d, double d2) {
        this.relativeThreshold = d;
        this.absoluteThreshold = d2;
    }

    @Override // org.apache.commons.math.optimization.VectorialConvergenceChecker
    public boolean converged(int i, org.apache.commons.math.optimization.VectorialPointValuePair vectorialPointValuePair, org.apache.commons.math.optimization.VectorialPointValuePair vectorialPointValuePair2) {
        double[] pointRef = vectorialPointValuePair.getPointRef();
        double[] pointRef2 = vectorialPointValuePair2.getPointRef();
        for (int i2 = 0; i2 < pointRef.length; i2++) {
            double d = pointRef[i2];
            double d2 = pointRef2[i2];
            double abs = org.apache.commons.math.util.FastMath.abs(d - d2);
            if (abs > org.apache.commons.math.util.FastMath.max(org.apache.commons.math.util.FastMath.abs(d), org.apache.commons.math.util.FastMath.abs(d2)) * this.relativeThreshold && abs > this.absoluteThreshold) {
                return false;
            }
        }
        return true;
    }
}
