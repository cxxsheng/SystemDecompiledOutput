package org.apache.commons.math.ode.nonstiff;

/* loaded from: classes3.dex */
class ClassicalRungeKuttaStepInterpolator extends org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator {
    private static final long serialVersionUID = -6576285612589783992L;

    public ClassicalRungeKuttaStepInterpolator() {
    }

    public ClassicalRungeKuttaStepInterpolator(org.apache.commons.math.ode.nonstiff.ClassicalRungeKuttaStepInterpolator classicalRungeKuttaStepInterpolator) {
        super(classicalRungeKuttaStepInterpolator);
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    protected org.apache.commons.math.ode.sampling.StepInterpolator doCopy() {
        return new org.apache.commons.math.ode.nonstiff.ClassicalRungeKuttaStepInterpolator(this);
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    protected void computeInterpolatedStateAndDerivatives(double d, double d2) throws org.apache.commons.math.ode.DerivativeException {
        double d3 = 4.0d * d;
        double d4 = 1.0d - d;
        double d5 = d * 2.0d;
        double d6 = 1.0d - d5;
        double d7 = d2 / 6.0d;
        double d8 = -d3;
        double d9 = (((d8 + 5.0d) * d) - 1.0d) * d7;
        double d10 = (((d3 - 2.0d) * d) - 2.0d) * d7;
        double d11 = d7 * (((d8 - 1.0d) * d) - 1.0d);
        double d12 = d4 * d6;
        double d13 = d5 * d4;
        double d14 = (-d) * d6;
        char c = 0;
        int i = 0;
        while (i < this.interpolatedState.length) {
            double d15 = this.yDotK[c][i];
            double d16 = this.yDotK[1][i] + this.yDotK[2][i];
            double d17 = this.yDotK[3][i];
            double d18 = d14;
            this.interpolatedState[i] = this.currentState[i] + (d9 * d15) + (d10 * d16) + (d11 * d17);
            this.interpolatedDerivatives[i] = (d15 * d12) + (d16 * d13) + (d17 * d18);
            i++;
            d14 = d18;
            c = 0;
        }
    }
}
