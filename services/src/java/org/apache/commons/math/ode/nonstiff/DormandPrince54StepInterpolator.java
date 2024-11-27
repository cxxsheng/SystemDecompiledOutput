package org.apache.commons.math.ode.nonstiff;

/* loaded from: classes3.dex */
class DormandPrince54StepInterpolator extends org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator {
    private static final double A70 = 0.09114583333333333d;
    private static final double A72 = 0.44923629829290207d;
    private static final double A73 = 0.6510416666666666d;
    private static final double A74 = -0.322376179245283d;
    private static final double A75 = 0.13095238095238096d;
    private static final double D0 = -1.1270175653862835d;
    private static final double D2 = 2.675424484351598d;
    private static final double D3 = -5.685526961588504d;
    private static final double D4 = 3.5219323679207912d;
    private static final double D5 = -1.7672812570757455d;
    private static final double D6 = 2.382468931778144d;
    private static final long serialVersionUID = 4104157279605906956L;
    private double[] v1;
    private double[] v2;
    private double[] v3;
    private double[] v4;
    private boolean vectorsInitialized;

    public DormandPrince54StepInterpolator() {
        this.v1 = null;
        this.v2 = null;
        this.v3 = null;
        this.v4 = null;
        this.vectorsInitialized = false;
    }

    public DormandPrince54StepInterpolator(org.apache.commons.math.ode.nonstiff.DormandPrince54StepInterpolator dormandPrince54StepInterpolator) {
        super(dormandPrince54StepInterpolator);
        if (dormandPrince54StepInterpolator.v1 == null) {
            this.v1 = null;
            this.v2 = null;
            this.v3 = null;
            this.v4 = null;
            this.vectorsInitialized = false;
            return;
        }
        this.v1 = (double[]) dormandPrince54StepInterpolator.v1.clone();
        this.v2 = (double[]) dormandPrince54StepInterpolator.v2.clone();
        this.v3 = (double[]) dormandPrince54StepInterpolator.v3.clone();
        this.v4 = (double[]) dormandPrince54StepInterpolator.v4.clone();
        this.vectorsInitialized = dormandPrince54StepInterpolator.vectorsInitialized;
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    protected org.apache.commons.math.ode.sampling.StepInterpolator doCopy() {
        return new org.apache.commons.math.ode.nonstiff.DormandPrince54StepInterpolator(this);
    }

    @Override // org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator
    public void reinitialize(org.apache.commons.math.ode.AbstractIntegrator abstractIntegrator, double[] dArr, double[][] dArr2, boolean z) {
        super.reinitialize(abstractIntegrator, dArr, dArr2, z);
        this.v1 = null;
        this.v2 = null;
        this.v3 = null;
        this.v4 = null;
        this.vectorsInitialized = false;
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    public void storeTime(double d) {
        super.storeTime(d);
        this.vectorsInitialized = false;
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    protected void computeInterpolatedStateAndDerivatives(double d, double d2) throws org.apache.commons.math.ode.DerivativeException {
        char c = 0;
        if (!this.vectorsInitialized) {
            if (this.v1 == null) {
                this.v1 = new double[this.interpolatedState.length];
                this.v2 = new double[this.interpolatedState.length];
                this.v3 = new double[this.interpolatedState.length];
                this.v4 = new double[this.interpolatedState.length];
            }
            int i = 0;
            while (i < this.interpolatedState.length) {
                double d3 = this.yDotK[c][i];
                double d4 = this.yDotK[2][i];
                double d5 = this.yDotK[3][i];
                double d6 = this.yDotK[4][i];
                double d7 = this.yDotK[5][i];
                double d8 = this.yDotK[6][i];
                this.v1[i] = (A70 * d3) + (A72 * d4) + (A73 * d5) + (A74 * d6) + (A75 * d7);
                this.v2[i] = d3 - this.v1[i];
                this.v3[i] = (this.v1[i] - this.v2[i]) - d8;
                this.v4[i] = (d3 * D0) + (d4 * D2) + (d5 * D3) + (d6 * D4) + (d7 * D5) + (d8 * D6);
                i++;
                c = 0;
            }
            this.vectorsInitialized = true;
        }
        double d9 = 1.0d - d;
        double d10 = d * 2.0d;
        double d11 = 1.0d - d10;
        double d12 = (2.0d - (d * 3.0d)) * d;
        double d13 = d10 * (((d10 - 3.0d) * d) + 1.0d);
        int i2 = 0;
        while (i2 < this.interpolatedState.length) {
            double d14 = d13;
            double d15 = d12;
            this.interpolatedState[i2] = this.currentState[i2] - (d2 * (this.v1[i2] - (d * (this.v2[i2] + (d * (this.v3[i2] + (this.v4[i2] * d9)))))));
            this.interpolatedDerivatives[i2] = this.v1[i2] + (this.v2[i2] * d11) + (this.v3[i2] * d15) + (this.v4[i2] * d14);
            i2++;
            d13 = d14;
            d12 = d15;
        }
    }
}
