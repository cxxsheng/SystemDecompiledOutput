package org.apache.commons.math.ode.nonstiff;

/* loaded from: classes3.dex */
class HighamHall54StepInterpolator extends org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator {
    private static final long serialVersionUID = -3583240427587318654L;

    public HighamHall54StepInterpolator() {
    }

    public HighamHall54StepInterpolator(org.apache.commons.math.ode.nonstiff.HighamHall54StepInterpolator highamHall54StepInterpolator) {
        super(highamHall54StepInterpolator);
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    protected org.apache.commons.math.ode.sampling.StepInterpolator doCopy() {
        return new org.apache.commons.math.ode.nonstiff.HighamHall54StepInterpolator(this);
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    protected void computeInterpolatedStateAndDerivatives(double d, double d2) throws org.apache.commons.math.ode.DerivativeException {
        double d3 = this.h * ((((((((((-5.0d) * d) / 2.0d) + 5.333333333333333d) * d) - 3.75d) * d) + 1.0d) * d) - 0.08333333333333333d);
        double d4 = this.h * (((((((135.0d * d) / 8.0d) - 30.375d) * d) + 14.34375d) * r1) - 0.84375d);
        double d5 = this.h * (((((((-30.0d) * d) + 50.666666666666664d) * d) - 22.0d) * d * d) + 1.3333333333333333d);
        double d6 = this.h * (((((((125.0d * d) / 8.0d) - 26.041666666666668d) * d) + 11.71875d) * r1) - 1.3020833333333333d);
        double d7 = this.h * ((r1 * ((r17 / 12.0d) - 0.3125d)) - 0.10416666666666667d);
        double d8 = (((d * (16.0d - (10.0d * d))) - 7.5d) * d) + 1.0d;
        double d9 = ((((67.5d * d) - 91.125d) * d) + 28.6875d) * d;
        double d10 = ((d * (152.0d - (120.0d * d))) - 44.0d) * d;
        double d11 = ((((62.5d * d) - 78.125d) * d) + 23.4375d) * d;
        double d12 = ((5.0d * d) / 8.0d) * ((2.0d * d) - 1.0d);
        char c = 0;
        int i = 0;
        while (i < this.interpolatedState.length) {
            double d13 = this.yDotK[c][i];
            double d14 = this.yDotK[2][i];
            double d15 = this.yDotK[3][i];
            double d16 = this.yDotK[4][i];
            double d17 = this.yDotK[5][i];
            double d18 = d8;
            this.interpolatedState[i] = this.currentState[i] + (d3 * d13) + (d4 * d14) + (d5 * d15) + (d6 * d16) + (d7 * d17);
            this.interpolatedDerivatives[i] = (d13 * d18) + (d14 * d9) + (d15 * d10) + (d16 * d11) + (d17 * d12);
            i++;
            d8 = d18;
            c = 0;
        }
    }
}
