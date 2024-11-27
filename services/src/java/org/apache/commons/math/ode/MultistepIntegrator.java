package org.apache.commons.math.ode;

/* loaded from: classes3.dex */
public abstract class MultistepIntegrator extends org.apache.commons.math.ode.nonstiff.AdaptiveStepsizeIntegrator {
    private double exp;
    private double maxGrowth;
    private double minReduction;
    private final int nSteps;
    protected org.apache.commons.math.linear.Array2DRowRealMatrix nordsieck;
    private double safety;
    protected double[] scaled;
    private org.apache.commons.math.ode.FirstOrderIntegrator starter;

    public interface NordsieckTransformer {
        org.apache.commons.math.linear.RealMatrix initializeHighOrderDerivatives(double[] dArr, double[][] dArr2);
    }

    protected abstract org.apache.commons.math.linear.Array2DRowRealMatrix initializeHighOrderDerivatives(double[] dArr, double[][] dArr2);

    protected MultistepIntegrator(java.lang.String str, int i, int i2, double d, double d2, double d3, double d4) {
        super(str, d, d2, d3, d4);
        if (i > 0) {
            this.starter = new org.apache.commons.math.ode.nonstiff.DormandPrince853Integrator(d, d2, d3, d4);
            this.nSteps = i;
            this.exp = (-1.0d) / i2;
            setSafety(0.9d);
            setMinReduction(0.2d);
            setMaxGrowth(org.apache.commons.math.util.FastMath.pow(2.0d, -this.exp));
            return;
        }
        throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INTEGRATION_METHOD_NEEDS_AT_LEAST_ONE_PREVIOUS_POINT, str);
    }

    protected MultistepIntegrator(java.lang.String str, int i, int i2, double d, double d2, double[] dArr, double[] dArr2) {
        super(str, d, d2, dArr, dArr2);
        this.starter = new org.apache.commons.math.ode.nonstiff.DormandPrince853Integrator(d, d2, dArr, dArr2);
        this.nSteps = i;
        this.exp = (-1.0d) / i2;
        setSafety(0.9d);
        setMinReduction(0.2d);
        setMaxGrowth(org.apache.commons.math.util.FastMath.pow(2.0d, -this.exp));
    }

    public org.apache.commons.math.ode.ODEIntegrator getStarterIntegrator() {
        return this.starter;
    }

    public void setStarterIntegrator(org.apache.commons.math.ode.FirstOrderIntegrator firstOrderIntegrator) {
        this.starter = firstOrderIntegrator;
    }

    protected void start(double d, double[] dArr, double d2) throws org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.IntegratorException {
        this.starter.clearEventHandlers();
        this.starter.clearStepHandlers();
        this.starter.addStepHandler(new org.apache.commons.math.ode.MultistepIntegrator.NordsieckInitializer(dArr.length));
        try {
            this.starter.integrate(new org.apache.commons.math.ode.MultistepIntegrator.CountingDifferentialEquations(dArr.length), d, dArr, d2, new double[dArr.length]);
        } catch (org.apache.commons.math.ode.DerivativeException e) {
            if (!(e instanceof org.apache.commons.math.ode.MultistepIntegrator.InitializationCompletedMarkerException)) {
                throw e;
            }
        }
        this.starter.clearStepHandlers();
    }

    public double getMinReduction() {
        return this.minReduction;
    }

    public void setMinReduction(double d) {
        this.minReduction = d;
    }

    public double getMaxGrowth() {
        return this.maxGrowth;
    }

    public void setMaxGrowth(double d) {
        this.maxGrowth = d;
    }

    public double getSafety() {
        return this.safety;
    }

    public void setSafety(double d) {
        this.safety = d;
    }

    protected double computeStepGrowShrinkFactor(double d) {
        return org.apache.commons.math.util.FastMath.min(this.maxGrowth, org.apache.commons.math.util.FastMath.max(this.minReduction, this.safety * org.apache.commons.math.util.FastMath.pow(d, this.exp)));
    }

    private class NordsieckInitializer implements org.apache.commons.math.ode.sampling.StepHandler {
        private final int n;

        public NordsieckInitializer(int i) {
            this.n = i;
        }

        @Override // org.apache.commons.math.ode.sampling.StepHandler
        public void handleStep(org.apache.commons.math.ode.sampling.StepInterpolator stepInterpolator, boolean z) throws org.apache.commons.math.ode.DerivativeException {
            double previousTime = stepInterpolator.getPreviousTime();
            double currentTime = stepInterpolator.getCurrentTime();
            org.apache.commons.math.ode.MultistepIntegrator.this.stepStart = previousTime;
            org.apache.commons.math.ode.MultistepIntegrator.this.stepSize = (currentTime - previousTime) / (org.apache.commons.math.ode.MultistepIntegrator.this.nSteps + 1);
            stepInterpolator.setInterpolatedTime(previousTime);
            org.apache.commons.math.ode.MultistepIntegrator.this.scaled = (double[]) stepInterpolator.getInterpolatedDerivatives().clone();
            for (int i = 0; i < this.n; i++) {
                double[] dArr = org.apache.commons.math.ode.MultistepIntegrator.this.scaled;
                dArr[i] = dArr[i] * org.apache.commons.math.ode.MultistepIntegrator.this.stepSize;
            }
            double[][] dArr2 = new double[org.apache.commons.math.ode.MultistepIntegrator.this.nSteps][];
            for (int i2 = 1; i2 <= org.apache.commons.math.ode.MultistepIntegrator.this.nSteps; i2++) {
                stepInterpolator.setInterpolatedTime((org.apache.commons.math.ode.MultistepIntegrator.this.stepSize * i2) + previousTime);
                double[] dArr3 = (double[]) stepInterpolator.getInterpolatedDerivatives().clone();
                for (int i3 = 0; i3 < this.n; i3++) {
                    dArr3[i3] = dArr3[i3] * org.apache.commons.math.ode.MultistepIntegrator.this.stepSize;
                }
                dArr2[i2 - 1] = dArr3;
            }
            org.apache.commons.math.ode.MultistepIntegrator.this.nordsieck = org.apache.commons.math.ode.MultistepIntegrator.this.initializeHighOrderDerivatives(org.apache.commons.math.ode.MultistepIntegrator.this.scaled, dArr2);
            throw new org.apache.commons.math.ode.MultistepIntegrator.InitializationCompletedMarkerException();
        }

        @Override // org.apache.commons.math.ode.sampling.StepHandler
        public boolean requiresDenseOutput() {
            return true;
        }

        @Override // org.apache.commons.math.ode.sampling.StepHandler
        public void reset() {
        }
    }

    private static class InitializationCompletedMarkerException extends org.apache.commons.math.ode.DerivativeException {
        private static final long serialVersionUID = -4105805787353488365L;

        public InitializationCompletedMarkerException() {
            super(null);
        }
    }

    private class CountingDifferentialEquations implements org.apache.commons.math.ode.ExtendedFirstOrderDifferentialEquations {
        private final int dimension;

        public CountingDifferentialEquations(int i) {
            this.dimension = i;
        }

        @Override // org.apache.commons.math.ode.FirstOrderDifferentialEquations
        public void computeDerivatives(double d, double[] dArr, double[] dArr2) throws org.apache.commons.math.ode.DerivativeException {
            org.apache.commons.math.ode.MultistepIntegrator.this.computeDerivatives(d, dArr, dArr2);
        }

        @Override // org.apache.commons.math.ode.FirstOrderDifferentialEquations
        public int getDimension() {
            return this.dimension;
        }

        @Override // org.apache.commons.math.ode.ExtendedFirstOrderDifferentialEquations
        public int getMainSetDimension() {
            return ((org.apache.commons.math.ode.nonstiff.AdaptiveStepsizeIntegrator) org.apache.commons.math.ode.MultistepIntegrator.this).mainSetDimension;
        }
    }
}
