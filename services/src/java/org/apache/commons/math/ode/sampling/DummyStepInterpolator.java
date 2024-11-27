package org.apache.commons.math.ode.sampling;

/* loaded from: classes3.dex */
public class DummyStepInterpolator extends org.apache.commons.math.ode.sampling.AbstractStepInterpolator {
    private static final long serialVersionUID = 1708010296707839488L;
    private double[] currentDerivative;

    public DummyStepInterpolator() {
        this.currentDerivative = null;
    }

    public DummyStepInterpolator(double[] dArr, double[] dArr2, boolean z) {
        super(dArr, z);
        this.currentDerivative = dArr2;
    }

    public DummyStepInterpolator(org.apache.commons.math.ode.sampling.DummyStepInterpolator dummyStepInterpolator) {
        super(dummyStepInterpolator);
        this.currentDerivative = (double[]) dummyStepInterpolator.currentDerivative.clone();
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    protected org.apache.commons.math.ode.sampling.StepInterpolator doCopy() {
        return new org.apache.commons.math.ode.sampling.DummyStepInterpolator(this);
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    protected void computeInterpolatedStateAndDerivatives(double d, double d2) {
        java.lang.System.arraycopy(this.currentState, 0, this.interpolatedState, 0, this.currentState.length);
        java.lang.System.arraycopy(this.currentDerivative, 0, this.interpolatedDerivatives, 0, this.currentDerivative.length);
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator, java.io.Externalizable
    public void writeExternal(java.io.ObjectOutput objectOutput) throws java.io.IOException {
        writeBaseExternal(objectOutput);
        if (this.currentDerivative != null) {
            for (int i = 0; i < this.currentDerivative.length; i++) {
                objectOutput.writeDouble(this.currentDerivative[i]);
            }
        }
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator, java.io.Externalizable
    public void readExternal(java.io.ObjectInput objectInput) throws java.io.IOException {
        double readBaseExternal = readBaseExternal(objectInput);
        if (this.currentState == null) {
            this.currentDerivative = null;
        } else {
            this.currentDerivative = new double[this.currentState.length];
            for (int i = 0; i < this.currentDerivative.length; i++) {
                this.currentDerivative[i] = objectInput.readDouble();
            }
        }
        setInterpolatedTime(readBaseExternal);
    }
}
