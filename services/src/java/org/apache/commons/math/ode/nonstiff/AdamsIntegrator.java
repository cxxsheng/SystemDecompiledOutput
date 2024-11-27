package org.apache.commons.math.ode.nonstiff;

/* loaded from: classes3.dex */
public abstract class AdamsIntegrator extends org.apache.commons.math.ode.MultistepIntegrator {
    private final org.apache.commons.math.ode.nonstiff.AdamsNordsieckTransformer transformer;

    @Override // org.apache.commons.math.ode.nonstiff.AdaptiveStepsizeIntegrator, org.apache.commons.math.ode.FirstOrderIntegrator
    public abstract double integrate(org.apache.commons.math.ode.FirstOrderDifferentialEquations firstOrderDifferentialEquations, double d, double[] dArr, double d2, double[] dArr2) throws org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.IntegratorException;

    public AdamsIntegrator(java.lang.String str, int i, int i2, double d, double d2, double d3, double d4) throws java.lang.IllegalArgumentException {
        super(str, i, i2, d, d2, d3, d4);
        this.transformer = org.apache.commons.math.ode.nonstiff.AdamsNordsieckTransformer.getInstance(i);
    }

    public AdamsIntegrator(java.lang.String str, int i, int i2, double d, double d2, double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException {
        super(str, i, i2, d, d2, dArr, dArr2);
        this.transformer = org.apache.commons.math.ode.nonstiff.AdamsNordsieckTransformer.getInstance(i);
    }

    @Override // org.apache.commons.math.ode.MultistepIntegrator
    protected org.apache.commons.math.linear.Array2DRowRealMatrix initializeHighOrderDerivatives(double[] dArr, double[][] dArr2) {
        return this.transformer.initializeHighOrderDerivatives(dArr, dArr2);
    }

    public org.apache.commons.math.linear.Array2DRowRealMatrix updateHighOrderDerivativesPhase1(org.apache.commons.math.linear.Array2DRowRealMatrix array2DRowRealMatrix) {
        return this.transformer.updateHighOrderDerivativesPhase1(array2DRowRealMatrix);
    }

    public void updateHighOrderDerivativesPhase2(double[] dArr, double[] dArr2, org.apache.commons.math.linear.Array2DRowRealMatrix array2DRowRealMatrix) {
        this.transformer.updateHighOrderDerivativesPhase2(dArr, dArr2, array2DRowRealMatrix);
    }
}
