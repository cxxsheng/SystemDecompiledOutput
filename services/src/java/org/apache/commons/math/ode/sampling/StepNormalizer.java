package org.apache.commons.math.ode.sampling;

/* loaded from: classes3.dex */
public class StepNormalizer implements org.apache.commons.math.ode.sampling.StepHandler {
    private boolean forward;
    private double h;
    private final org.apache.commons.math.ode.sampling.FixedStepHandler handler;
    private double[] lastDerivatives;
    private double[] lastState;
    private double lastTime;

    public StepNormalizer(double d, org.apache.commons.math.ode.sampling.FixedStepHandler fixedStepHandler) {
        this.h = org.apache.commons.math.util.FastMath.abs(d);
        this.handler = fixedStepHandler;
        reset();
    }

    @Override // org.apache.commons.math.ode.sampling.StepHandler
    public boolean requiresDenseOutput() {
        return true;
    }

    @Override // org.apache.commons.math.ode.sampling.StepHandler
    public void reset() {
        this.lastTime = Double.NaN;
        this.lastState = null;
        this.lastDerivatives = null;
        this.forward = true;
    }

    @Override // org.apache.commons.math.ode.sampling.StepHandler
    public void handleStep(org.apache.commons.math.ode.sampling.StepInterpolator stepInterpolator, boolean z) throws org.apache.commons.math.ode.DerivativeException {
        if (this.lastState == null) {
            this.lastTime = stepInterpolator.getPreviousTime();
            stepInterpolator.setInterpolatedTime(this.lastTime);
            this.lastState = (double[]) stepInterpolator.getInterpolatedState().clone();
            this.lastDerivatives = (double[]) stepInterpolator.getInterpolatedDerivatives().clone();
            this.forward = stepInterpolator.getCurrentTime() >= this.lastTime;
            if (!this.forward) {
                this.h = -this.h;
            }
        }
        double d = this.lastTime + this.h;
        boolean z2 = this.forward ^ (d > stepInterpolator.getCurrentTime());
        while (z2) {
            this.handler.handleStep(this.lastTime, this.lastState, this.lastDerivatives, false);
            this.lastTime = d;
            stepInterpolator.setInterpolatedTime(this.lastTime);
            java.lang.System.arraycopy(stepInterpolator.getInterpolatedState(), 0, this.lastState, 0, this.lastState.length);
            java.lang.System.arraycopy(stepInterpolator.getInterpolatedDerivatives(), 0, this.lastDerivatives, 0, this.lastDerivatives.length);
            d += this.h;
            z2 = this.forward ^ (d > stepInterpolator.getCurrentTime());
        }
        if (z) {
            this.handler.handleStep(this.lastTime, this.lastState, this.lastDerivatives, true);
        }
    }
}
