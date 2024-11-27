package org.apache.commons.math.ode.nonstiff;

/* loaded from: classes3.dex */
class GillStepInterpolator extends org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator {
    private static final double TWO_MINUS_SQRT_2 = 2.0d - org.apache.commons.math.util.FastMath.sqrt(2.0d);
    private static final double TWO_PLUS_SQRT_2 = org.apache.commons.math.util.FastMath.sqrt(2.0d) + 2.0d;
    private static final long serialVersionUID = -107804074496313322L;

    public GillStepInterpolator() {
    }

    public GillStepInterpolator(org.apache.commons.math.ode.nonstiff.GillStepInterpolator gillStepInterpolator) {
        super(gillStepInterpolator);
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    protected org.apache.commons.math.ode.sampling.StepInterpolator doCopy() {
        return new org.apache.commons.math.ode.nonstiff.GillStepInterpolator(this);
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    protected void computeInterpolatedStateAndDerivatives(double d, double d2) throws org.apache.commons.math.ode.DerivativeException {
        double d3 = 2.0d * d;
        double d4 = 4.0d * d;
        double d5 = d2 / 6.0d;
        double d6 = 1.0d - d;
        double d7 = d5 * d6;
        double d8 = (d3 + 1.0d) * d7;
        double d9 = d7 * (1.0d - d4);
        double d10 = TWO_MINUS_SQRT_2 * d8;
        double d11 = d8 * TWO_PLUS_SQRT_2;
        double d12 = d5 * (((d4 + 1.0d) * d) + 1.0d);
        double d13 = ((d3 - 3.0d) * d) + 1.0d;
        double d14 = d6 * d;
        double d15 = TWO_MINUS_SQRT_2 * d14;
        double d16 = d14 * TWO_PLUS_SQRT_2;
        double d17 = (d3 - 1.0d) * d;
        int i = 0;
        while (i < this.interpolatedState.length) {
            double d18 = this.yDotK[0][i];
            double d19 = this.yDotK[1][i];
            double d20 = this.yDotK[2][i];
            double d21 = this.yDotK[3][i];
            double d22 = d17;
            this.interpolatedState[i] = (((this.currentState[i] - (d9 * d18)) - (d10 * d19)) - (d11 * d20)) - (d12 * d21);
            this.interpolatedDerivatives[i] = (d18 * d13) + (d19 * d15) + (d20 * d16) + (d22 * d21);
            i++;
            d17 = d22;
        }
    }
}
