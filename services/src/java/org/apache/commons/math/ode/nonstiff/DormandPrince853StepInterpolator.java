package org.apache.commons.math.ode.nonstiff;

/* loaded from: classes3.dex */
class DormandPrince853StepInterpolator extends org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator {
    private static final double B_01 = 0.054293734116568765d;
    private static final double B_06 = 4.450312892752409d;
    private static final double B_07 = 1.8915178993145003d;
    private static final double B_08 = -5.801203960010585d;
    private static final double B_09 = 0.3111643669578199d;
    private static final double B_10 = -0.1521609496625161d;
    private static final double B_11 = 0.20136540080403034d;
    private static final double B_12 = 0.04471061572777259d;
    private static final double C14 = 0.1d;
    private static final double C15 = 0.2d;
    private static final double C16 = 0.7777777777777778d;
    private static final double[][] D = {new double[]{-8.428938276109013d, 0.5667149535193777d, -3.0689499459498917d, 2.38466765651207d, 2.1170345824450285d, -0.871391583777973d, 2.2404374302607883d, 0.6315787787694688d, -0.08899033645133331d, 18.148505520854727d, -9.194632392478356d, -4.436036387594894d}, new double[]{10.427508642579134d, 242.28349177525817d, 165.20045171727028d, -374.5467547226902d, -22.113666853125302d, 7.733432668472264d, -30.674084731089398d, -9.332130526430229d, 15.697238121770845d, -31.139403219565178d, -9.35292435884448d, 35.81684148639408d}, new double[]{19.985053242002433d, -387.0373087493518d, -189.17813819516758d, 527.8081592054236d, -11.573902539959631d, 6.8812326946963d, -1.0006050966910838d, 0.7777137798053443d, -2.778205752353508d, -60.19669523126412d, 84.32040550667716d, 11.99229113618279d}, new double[]{-25.69393346270375d, -154.18974869023643d, -231.5293791760455d, 357.6391179106141d, 93.4053241836243d, -37.45832313645163d, 104.0996495089623d, 29.8402934266605d, -43.53345659001114d, 96.32455395918828d, -39.17726167561544d, -149.72683625798564d}};
    private static final double K14_01 = 0.0018737681664791894d;
    private static final double K14_06 = -4.450312892752409d;
    private static final double K14_07 = -1.6380176890978755d;
    private static final double K14_08 = 5.554964922539782d;
    private static final double K14_09 = -0.4353557902216363d;
    private static final double K14_10 = 0.30545274794128174d;
    private static final double K14_11 = -0.19316434850839564d;
    private static final double K14_12 = -0.03714271806722689d;
    private static final double K14_13 = -0.008298d;
    private static final double K15_01 = -0.022459085953066622d;
    private static final double K15_06 = -4.422011983080043d;
    private static final double K15_07 = -1.8379759110070617d;
    private static final double K15_08 = 5.746280211439194d;
    private static final double K15_09 = -0.3111643669578199d;
    private static final double K15_10 = 0.1521609496625161d;
    private static final double K15_11 = -0.2014737481327276d;
    private static final double K15_12 = -0.04432804463693693d;
    private static final double K15_13 = -3.4046500868740456E-4d;
    private static final double K15_14 = 0.1413124436746325d;
    private static final double K16_01 = -0.4831900357003607d;
    private static final double K16_06 = -9.147934308113573d;
    private static final double K16_07 = 5.791903296748099d;
    private static final double K16_08 = 9.870193778407696d;
    private static final double K16_09 = 0.04556282049746119d;
    private static final double K16_10 = 0.1521609496625161d;
    private static final double K16_11 = -0.20136540080403034d;
    private static final double K16_12 = -0.04471061572777259d;
    private static final double K16_13 = -0.0013990241651590145d;
    private static final double K16_14 = 2.9475147891527724d;
    private static final double K16_15 = -9.15095847217987d;
    private static final long serialVersionUID = 7152276390558450974L;
    private double[][] v;
    private boolean vectorsInitialized;
    private double[][] yDotKLast;

    public DormandPrince853StepInterpolator() {
        this.yDotKLast = null;
        this.v = null;
        this.vectorsInitialized = false;
    }

    public DormandPrince853StepInterpolator(org.apache.commons.math.ode.nonstiff.DormandPrince853StepInterpolator dormandPrince853StepInterpolator) {
        super(dormandPrince853StepInterpolator);
        if (dormandPrince853StepInterpolator.currentState == null) {
            this.yDotKLast = null;
            this.v = null;
            this.vectorsInitialized = false;
            return;
        }
        int length = dormandPrince853StepInterpolator.currentState.length;
        this.yDotKLast = new double[3][];
        for (int i = 0; i < this.yDotKLast.length; i++) {
            this.yDotKLast[i] = new double[length];
            java.lang.System.arraycopy(dormandPrince853StepInterpolator.yDotKLast[i], 0, this.yDotKLast[i], 0, length);
        }
        this.v = new double[7][];
        for (int i2 = 0; i2 < this.v.length; i2++) {
            this.v[i2] = new double[length];
            java.lang.System.arraycopy(dormandPrince853StepInterpolator.v[i2], 0, this.v[i2], 0, length);
        }
        this.vectorsInitialized = dormandPrince853StepInterpolator.vectorsInitialized;
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    protected org.apache.commons.math.ode.sampling.StepInterpolator doCopy() {
        return new org.apache.commons.math.ode.nonstiff.DormandPrince853StepInterpolator(this);
    }

    @Override // org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator
    public void reinitialize(org.apache.commons.math.ode.AbstractIntegrator abstractIntegrator, double[] dArr, double[][] dArr2, boolean z) {
        super.reinitialize(abstractIntegrator, dArr, dArr2, z);
        int length = this.currentState.length;
        this.yDotKLast = new double[3][];
        for (int i = 0; i < this.yDotKLast.length; i++) {
            this.yDotKLast[i] = new double[length];
        }
        this.v = new double[7][];
        for (int i2 = 0; i2 < this.v.length; i2++) {
            this.v[i2] = new double[length];
        }
        this.vectorsInitialized = false;
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    public void storeTime(double d) {
        super.storeTime(d);
        this.vectorsInitialized = false;
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    protected void computeInterpolatedStateAndDerivatives(double d, double d2) throws org.apache.commons.math.ode.DerivativeException {
        char c = 6;
        if (!this.vectorsInitialized) {
            char c2 = 7;
            if (this.v == null) {
                this.v = new double[7][];
                for (int i = 0; i < 7; i++) {
                    this.v[i] = new double[this.interpolatedState.length];
                }
            }
            finalizeStep();
            int i2 = 0;
            while (i2 < this.interpolatedState.length) {
                double d3 = this.yDotK[0][i2];
                double d4 = this.yDotK[5][i2];
                double d5 = this.yDotK[c][i2];
                double d6 = this.yDotK[c2][i2];
                double d7 = this.yDotK[8][i2];
                double d8 = this.yDotK[9][i2];
                double d9 = this.yDotK[10][i2];
                double d10 = this.yDotK[11][i2];
                double d11 = this.yDotK[12][i2];
                double d12 = this.yDotKLast[0][i2];
                double d13 = this.yDotKLast[1][i2];
                double d14 = this.yDotKLast[2][i2];
                this.v[0][i2] = (B_01 * d3) + (B_06 * d4) + (B_07 * d5) + (B_08 * d6) + (B_09 * d7) + (B_10 * d8) + (B_11 * d9) + (B_12 * d10);
                this.v[1][i2] = d3 - this.v[0][i2];
                this.v[2][i2] = (this.v[0][i2] - this.v[1][i2]) - this.yDotK[12][i2];
                for (int i3 = 0; i3 < D.length; i3++) {
                    this.v[i3 + 3][i2] = (D[i3][0] * d3) + (D[i3][1] * d4) + (D[i3][2] * d5) + (D[i3][3] * d6) + (D[i3][4] * d7) + (D[i3][5] * d8) + (D[i3][6] * d9) + (D[i3][7] * d10) + (D[i3][8] * d11) + (D[i3][9] * d12) + (D[i3][10] * d13) + (D[i3][11] * d14);
                }
                i2++;
                c2 = 7;
                c = 6;
            }
            this.vectorsInitialized = true;
        }
        double d15 = 1.0d - d;
        double d16 = d * 2.0d;
        double d17 = d * d;
        double d18 = 1.0d - d16;
        double d19 = (2.0d - (d * 3.0d)) * d;
        double d20 = d16 * (((d16 - 3.0d) * d) + 1.0d);
        double d21 = ((((5.0d * d) - 8.0d) * d) + 3.0d) * d17;
        double d22 = ((((d * (15.0d - (6.0d * d))) - 12.0d) * d) + 3.0d) * d17;
        double d23 = d17 * d * ((((d * (18.0d - (7.0d * d))) - 15.0d) * d) + 4.0d);
        for (int i4 = 0; i4 < this.interpolatedState.length; i4++) {
            this.interpolatedState[i4] = this.currentState[i4] - ((this.v[0][i4] - ((this.v[1][i4] + ((this.v[2][i4] + ((this.v[3][i4] + ((this.v[4][i4] + ((this.v[5][i4] + (this.v[6][i4] * d)) * d15)) * d)) * d15)) * d)) * d)) * d2);
            this.interpolatedDerivatives[i4] = this.v[0][i4] + (this.v[1][i4] * d18) + (this.v[2][i4] * d19) + (this.v[3][i4] * d20) + (this.v[4][i4] * d21) + (this.v[5][i4] * d22) + (this.v[6][i4] * d23);
        }
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    protected void doFinalize() throws org.apache.commons.math.ode.DerivativeException {
        char c;
        char c2;
        if (this.currentState == null) {
            return;
        }
        double[] dArr = new double[this.currentState.length];
        double globalPreviousTime = getGlobalPreviousTime();
        int i = 0;
        while (true) {
            c = '\n';
            c2 = '\t';
            if (i >= this.currentState.length) {
                break;
            }
            dArr[i] = this.currentState[i] + (this.h * ((this.yDotK[0][i] * K14_01) + (this.yDotK[5][i] * K14_06) + (this.yDotK[6][i] * K14_07) + (this.yDotK[7][i] * K14_08) + (this.yDotK[8][i] * K14_09) + (this.yDotK[9][i] * K14_10) + (this.yDotK[10][i] * K14_11) + (this.yDotK[11][i] * K14_12) + (this.yDotK[12][i] * K14_13)));
            i++;
        }
        this.integrator.computeDerivatives((this.h * C14) + globalPreviousTime, dArr, this.yDotKLast[0]);
        int i2 = 0;
        while (i2 < this.currentState.length) {
            dArr[i2] = this.currentState[i2] + (this.h * ((this.yDotK[0][i2] * K15_01) + (this.yDotK[5][i2] * K15_06) + (this.yDotK[6][i2] * K15_07) + (this.yDotK[7][i2] * K15_08) + (this.yDotK[8][i2] * K15_09) + (this.yDotK[c2][i2] * 0.1521609496625161d) + (this.yDotK[c][i2] * K15_11) + (this.yDotK[11][i2] * K15_12) + (this.yDotK[12][i2] * K15_13) + (this.yDotKLast[0][i2] * K15_14)));
            i2++;
            c = '\n';
            c2 = '\t';
        }
        this.integrator.computeDerivatives((this.h * C15) + globalPreviousTime, dArr, this.yDotKLast[1]);
        for (int i3 = 0; i3 < this.currentState.length; i3++) {
            dArr[i3] = this.currentState[i3] + (this.h * ((this.yDotK[0][i3] * K16_01) + (this.yDotK[5][i3] * K16_06) + (this.yDotK[6][i3] * K16_07) + (this.yDotK[7][i3] * K16_08) + (this.yDotK[8][i3] * K16_09) + (this.yDotK[9][i3] * 0.1521609496625161d) + (this.yDotK[10][i3] * K16_11) + (this.yDotK[11][i3] * K16_12) + (this.yDotK[12][i3] * K16_13) + (this.yDotKLast[0][i3] * K16_14) + (this.yDotKLast[1][i3] * K16_15)));
        }
        this.integrator.computeDerivatives(globalPreviousTime + (this.h * C16), dArr, this.yDotKLast[2]);
    }

    @Override // org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator, org.apache.commons.math.ode.sampling.AbstractStepInterpolator, java.io.Externalizable
    public void writeExternal(java.io.ObjectOutput objectOutput) throws java.io.IOException {
        try {
            finalizeStep();
            int length = this.currentState == null ? -1 : this.currentState.length;
            objectOutput.writeInt(length);
            for (int i = 0; i < length; i++) {
                objectOutput.writeDouble(this.yDotKLast[0][i]);
                objectOutput.writeDouble(this.yDotKLast[1][i]);
                objectOutput.writeDouble(this.yDotKLast[2][i]);
            }
            super.writeExternal(objectOutput);
        } catch (org.apache.commons.math.ode.DerivativeException e) {
            java.io.IOException iOException = new java.io.IOException(e.getLocalizedMessage());
            iOException.initCause(e);
            throw iOException;
        }
    }

    @Override // org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator, org.apache.commons.math.ode.sampling.AbstractStepInterpolator, java.io.Externalizable
    public void readExternal(java.io.ObjectInput objectInput) throws java.io.IOException {
        this.yDotKLast = new double[3][];
        int readInt = objectInput.readInt();
        this.yDotKLast[0] = readInt < 0 ? null : new double[readInt];
        this.yDotKLast[1] = readInt < 0 ? null : new double[readInt];
        this.yDotKLast[2] = readInt >= 0 ? new double[readInt] : null;
        for (int i = 0; i < readInt; i++) {
            this.yDotKLast[0][i] = objectInput.readDouble();
            this.yDotKLast[1][i] = objectInput.readDouble();
            this.yDotKLast[2][i] = objectInput.readDouble();
        }
        super.readExternal(objectInput);
    }
}
