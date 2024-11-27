package org.apache.commons.math.ode.nonstiff;

/* loaded from: classes3.dex */
public class EulerIntegrator extends org.apache.commons.math.ode.nonstiff.RungeKuttaIntegrator {
    private static final double[] STATIC_C = new double[0];
    private static final double[][] STATIC_A = new double[0][];
    private static final double[] STATIC_B = {1.0d};

    public EulerIntegrator(double d) {
        super("Euler", STATIC_C, STATIC_A, STATIC_B, new org.apache.commons.math.ode.nonstiff.EulerStepInterpolator(), d);
    }
}
