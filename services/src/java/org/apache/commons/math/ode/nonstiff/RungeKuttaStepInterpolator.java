package org.apache.commons.math.ode.nonstiff;

/* loaded from: classes3.dex */
abstract class RungeKuttaStepInterpolator extends org.apache.commons.math.ode.sampling.AbstractStepInterpolator {
    protected org.apache.commons.math.ode.AbstractIntegrator integrator;
    protected double[][] yDotK;

    protected RungeKuttaStepInterpolator() {
        this.yDotK = null;
        this.integrator = null;
    }

    public RungeKuttaStepInterpolator(org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator rungeKuttaStepInterpolator) {
        super(rungeKuttaStepInterpolator);
        if (rungeKuttaStepInterpolator.currentState != null) {
            int length = this.currentState.length;
            this.yDotK = new double[rungeKuttaStepInterpolator.yDotK.length][];
            for (int i = 0; i < rungeKuttaStepInterpolator.yDotK.length; i++) {
                this.yDotK[i] = new double[length];
                java.lang.System.arraycopy(rungeKuttaStepInterpolator.yDotK[i], 0, this.yDotK[i], 0, length);
            }
        } else {
            this.yDotK = null;
        }
        this.integrator = null;
    }

    public void reinitialize(org.apache.commons.math.ode.AbstractIntegrator abstractIntegrator, double[] dArr, double[][] dArr2, boolean z) {
        reinitialize(dArr, z);
        this.yDotK = dArr2;
        this.integrator = abstractIntegrator;
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator, java.io.Externalizable
    public void writeExternal(java.io.ObjectOutput objectOutput) throws java.io.IOException {
        writeBaseExternal(objectOutput);
        int length = this.currentState == null ? -1 : this.currentState.length;
        int length2 = this.yDotK != null ? this.yDotK.length : -1;
        objectOutput.writeInt(length2);
        for (int i = 0; i < length2; i++) {
            for (int i2 = 0; i2 < length; i2++) {
                objectOutput.writeDouble(this.yDotK[i][i2]);
            }
        }
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator, java.io.Externalizable
    public void readExternal(java.io.ObjectInput objectInput) throws java.io.IOException {
        double readBaseExternal = readBaseExternal(objectInput);
        int length = this.currentState == null ? -1 : this.currentState.length;
        int readInt = objectInput.readInt();
        this.yDotK = readInt < 0 ? null : new double[readInt][];
        for (int i = 0; i < readInt; i++) {
            this.yDotK[i] = length < 0 ? null : new double[length];
            for (int i2 = 0; i2 < length; i2++) {
                this.yDotK[i][i2] = objectInput.readDouble();
            }
        }
        this.integrator = null;
        if (this.currentState != null) {
            setInterpolatedTime(readBaseExternal);
        } else {
            this.interpolatedTime = readBaseExternal;
        }
    }
}
