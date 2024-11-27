package org.apache.commons.math.ode.nonstiff;

/* loaded from: classes3.dex */
class MidpointStepInterpolator extends org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator {
    private static final long serialVersionUID = -865524111506042509L;

    public MidpointStepInterpolator() {
    }

    public MidpointStepInterpolator(org.apache.commons.math.ode.nonstiff.MidpointStepInterpolator midpointStepInterpolator) {
        super(midpointStepInterpolator);
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    protected org.apache.commons.math.ode.sampling.StepInterpolator doCopy() {
        return new org.apache.commons.math.ode.nonstiff.MidpointStepInterpolator(this);
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    protected void computeInterpolatedStateAndDerivatives(double d, double d2) throws org.apache.commons.math.ode.DerivativeException {
        double d3 = d2 * d;
        double d4 = (d + 1.0d) * d2;
        double d5 = 2.0d * d;
        double d6 = 1.0d - d5;
        char c = 0;
        int i = 0;
        while (i < this.interpolatedState.length) {
            double d7 = this.yDotK[c][i];
            double d8 = this.yDotK[1][i];
            this.interpolatedState[i] = (this.currentState[i] + (d3 * d7)) - (d4 * d8);
            this.interpolatedDerivatives[i] = (d7 * d6) + (d8 * d5);
            i++;
            c = 0;
        }
    }
}
