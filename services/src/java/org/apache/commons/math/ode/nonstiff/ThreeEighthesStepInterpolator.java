package org.apache.commons.math.ode.nonstiff;

/* loaded from: classes3.dex */
class ThreeEighthesStepInterpolator extends org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator {
    private static final long serialVersionUID = -3345024435978721931L;

    public ThreeEighthesStepInterpolator() {
    }

    public ThreeEighthesStepInterpolator(org.apache.commons.math.ode.nonstiff.ThreeEighthesStepInterpolator threeEighthesStepInterpolator) {
        super(threeEighthesStepInterpolator);
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    protected org.apache.commons.math.ode.sampling.StepInterpolator doCopy() {
        return new org.apache.commons.math.ode.nonstiff.ThreeEighthesStepInterpolator(this);
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    protected void computeInterpolatedStateAndDerivatives(double d, double d2) throws org.apache.commons.math.ode.DerivativeException {
        double d3 = 4.0d * d;
        double d4 = d3 * d;
        double d5 = d2 / 8.0d;
        double d6 = ((1.0d - (7.0d * d)) + (d4 * 2.0d)) * d5;
        double d7 = 3.0d * d5;
        double d8 = d + 1.0d;
        double d9 = (d8 - d4) * d7;
        double d10 = d7 * d8;
        double d11 = d5 * (d8 + d4);
        double d12 = 0.75d * d;
        double d13 = ((d3 - 5.0d) * d12) + 1.0d;
        double d14 = (5.0d - (6.0d * d)) * d12;
        double d15 = ((2.0d * d) - 1.0d) * d12;
        for (int i = 0; i < this.interpolatedState.length; i++) {
            double d16 = this.yDotK[0][i];
            double d17 = this.yDotK[1][i];
            double d18 = this.yDotK[2][i];
            double d19 = this.yDotK[3][i];
            this.interpolatedState[i] = (((this.currentState[i] - (d6 * d16)) - (d9 * d17)) - (d10 * d18)) - (d11 * d19);
            d15 = d15;
            this.interpolatedDerivatives[i] = (d16 * d13) + (d17 * d14) + (d18 * d12) + (d15 * d19);
        }
    }
}
