package org.apache.commons.math.ode.nonstiff;

/* loaded from: classes3.dex */
public class HighamHall54Integrator extends org.apache.commons.math.ode.nonstiff.EmbeddedRungeKuttaIntegrator {
    private static final java.lang.String METHOD_NAME = "Higham-Hall 5(4)";
    private static final double[] STATIC_C = {0.2222222222222222d, 0.3333333333333333d, 0.5d, 0.6d, 1.0d, 1.0d};
    private static final double[][] STATIC_A = {new double[]{0.2222222222222222d}, new double[]{0.08333333333333333d, 0.25d}, new double[]{0.125d, 0.0d, 0.375d}, new double[]{0.182d, -0.27d, 0.624d, 0.064d}, new double[]{-0.55d, 1.35d, 2.4d, -7.2d, 5.0d}, new double[]{0.08333333333333333d, 0.0d, 0.84375d, -1.3333333333333333d, 1.3020833333333333d, 0.10416666666666667d}};
    private static final double[] STATIC_B = {0.08333333333333333d, 0.0d, 0.84375d, -1.3333333333333333d, 1.3020833333333333d, 0.10416666666666667d, 0.0d};
    private static final double[] STATIC_E = {-0.05d, 0.0d, 0.50625d, -1.2d, 0.78125d, 0.0625d, -0.1d};

    public HighamHall54Integrator(double d, double d2, double d3, double d4) {
        super(METHOD_NAME, false, STATIC_C, STATIC_A, STATIC_B, (org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator) new org.apache.commons.math.ode.nonstiff.HighamHall54StepInterpolator(), d, d2, d3, d4);
    }

    public HighamHall54Integrator(double d, double d2, double[] dArr, double[] dArr2) {
        super(METHOD_NAME, false, STATIC_C, STATIC_A, STATIC_B, (org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator) new org.apache.commons.math.ode.nonstiff.HighamHall54StepInterpolator(), d, d2, dArr, dArr2);
    }

    @Override // org.apache.commons.math.ode.nonstiff.EmbeddedRungeKuttaIntegrator
    public int getOrder() {
        return 5;
    }

    @Override // org.apache.commons.math.ode.nonstiff.EmbeddedRungeKuttaIntegrator
    protected double estimateError(double[][] dArr, double[] dArr2, double[] dArr3, double d) {
        double d2;
        double d3;
        double d4 = 0.0d;
        for (int i = 0; i < this.mainSetDimension; i++) {
            double d5 = STATIC_E[0] * dArr[0][i];
            for (int i2 = 1; i2 < STATIC_E.length; i2++) {
                d5 += STATIC_E[i2] * dArr[i2][i];
            }
            double max = org.apache.commons.math.util.FastMath.max(org.apache.commons.math.util.FastMath.abs(dArr2[i]), org.apache.commons.math.util.FastMath.abs(dArr3[i]));
            if (this.vecAbsoluteTolerance == null) {
                d2 = this.scalAbsoluteTolerance;
                d3 = this.scalRelativeTolerance;
            } else {
                d2 = this.vecAbsoluteTolerance[i];
                d3 = this.vecRelativeTolerance[i];
            }
            double d6 = (d5 * d) / (d2 + (d3 * max));
            d4 += d6 * d6;
        }
        return org.apache.commons.math.util.FastMath.sqrt(d4 / this.mainSetDimension);
    }
}
