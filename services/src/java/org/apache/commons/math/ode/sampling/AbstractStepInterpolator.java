package org.apache.commons.math.ode.sampling;

/* loaded from: classes3.dex */
public abstract class AbstractStepInterpolator implements org.apache.commons.math.ode.sampling.StepInterpolator {
    protected double[] currentState;
    private boolean dirtyState;
    private boolean finalized;
    private boolean forward;
    private double globalCurrentTime;
    private double globalPreviousTime;
    protected double h;
    protected double[] interpolatedDerivatives;
    protected double[] interpolatedState;
    protected double interpolatedTime;
    private double softCurrentTime;
    private double softPreviousTime;

    protected abstract void computeInterpolatedStateAndDerivatives(double d, double d2) throws org.apache.commons.math.ode.DerivativeException;

    protected abstract org.apache.commons.math.ode.sampling.StepInterpolator doCopy();

    @Override // java.io.Externalizable
    public abstract void readExternal(java.io.ObjectInput objectInput) throws java.io.IOException, java.lang.ClassNotFoundException;

    @Override // java.io.Externalizable
    public abstract void writeExternal(java.io.ObjectOutput objectOutput) throws java.io.IOException;

    protected AbstractStepInterpolator() {
        this.globalPreviousTime = Double.NaN;
        this.globalCurrentTime = Double.NaN;
        this.softPreviousTime = Double.NaN;
        this.softCurrentTime = Double.NaN;
        this.h = Double.NaN;
        this.interpolatedTime = Double.NaN;
        this.currentState = null;
        this.interpolatedState = null;
        this.interpolatedDerivatives = null;
        this.finalized = false;
        this.forward = true;
        this.dirtyState = true;
    }

    protected AbstractStepInterpolator(double[] dArr, boolean z) {
        this.globalPreviousTime = Double.NaN;
        this.globalCurrentTime = Double.NaN;
        this.softPreviousTime = Double.NaN;
        this.softCurrentTime = Double.NaN;
        this.h = Double.NaN;
        this.interpolatedTime = Double.NaN;
        this.currentState = dArr;
        this.interpolatedState = new double[dArr.length];
        this.interpolatedDerivatives = new double[dArr.length];
        this.finalized = false;
        this.forward = z;
        this.dirtyState = true;
    }

    protected AbstractStepInterpolator(org.apache.commons.math.ode.sampling.AbstractStepInterpolator abstractStepInterpolator) {
        this.globalPreviousTime = abstractStepInterpolator.globalPreviousTime;
        this.globalCurrentTime = abstractStepInterpolator.globalCurrentTime;
        this.softPreviousTime = abstractStepInterpolator.softPreviousTime;
        this.softCurrentTime = abstractStepInterpolator.softCurrentTime;
        this.h = abstractStepInterpolator.h;
        this.interpolatedTime = abstractStepInterpolator.interpolatedTime;
        if (abstractStepInterpolator.currentState != null) {
            this.currentState = (double[]) abstractStepInterpolator.currentState.clone();
            this.interpolatedState = (double[]) abstractStepInterpolator.interpolatedState.clone();
            this.interpolatedDerivatives = (double[]) abstractStepInterpolator.interpolatedDerivatives.clone();
        } else {
            this.currentState = null;
            this.interpolatedState = null;
            this.interpolatedDerivatives = null;
        }
        this.finalized = abstractStepInterpolator.finalized;
        this.forward = abstractStepInterpolator.forward;
        this.dirtyState = abstractStepInterpolator.dirtyState;
    }

    protected void reinitialize(double[] dArr, boolean z) {
        this.globalPreviousTime = Double.NaN;
        this.globalCurrentTime = Double.NaN;
        this.softPreviousTime = Double.NaN;
        this.softCurrentTime = Double.NaN;
        this.h = Double.NaN;
        this.interpolatedTime = Double.NaN;
        this.currentState = dArr;
        this.interpolatedState = new double[dArr.length];
        this.interpolatedDerivatives = new double[dArr.length];
        this.finalized = false;
        this.forward = z;
        this.dirtyState = true;
    }

    @Override // org.apache.commons.math.ode.sampling.StepInterpolator
    public org.apache.commons.math.ode.sampling.StepInterpolator copy() throws org.apache.commons.math.ode.DerivativeException {
        finalizeStep();
        return doCopy();
    }

    public void shift() {
        this.globalPreviousTime = this.globalCurrentTime;
        this.softPreviousTime = this.globalPreviousTime;
        this.softCurrentTime = this.globalCurrentTime;
    }

    public void storeTime(double d) {
        this.globalCurrentTime = d;
        this.softCurrentTime = this.globalCurrentTime;
        this.h = this.globalCurrentTime - this.globalPreviousTime;
        setInterpolatedTime(d);
        this.finalized = false;
    }

    public void setSoftPreviousTime(double d) {
        this.softPreviousTime = d;
    }

    public void setSoftCurrentTime(double d) {
        this.softCurrentTime = d;
    }

    public double getGlobalPreviousTime() {
        return this.globalPreviousTime;
    }

    public double getGlobalCurrentTime() {
        return this.globalCurrentTime;
    }

    @Override // org.apache.commons.math.ode.sampling.StepInterpolator
    public double getPreviousTime() {
        return this.softPreviousTime;
    }

    @Override // org.apache.commons.math.ode.sampling.StepInterpolator
    public double getCurrentTime() {
        return this.softCurrentTime;
    }

    @Override // org.apache.commons.math.ode.sampling.StepInterpolator
    public double getInterpolatedTime() {
        return this.interpolatedTime;
    }

    @Override // org.apache.commons.math.ode.sampling.StepInterpolator
    public void setInterpolatedTime(double d) {
        this.interpolatedTime = d;
        this.dirtyState = true;
    }

    @Override // org.apache.commons.math.ode.sampling.StepInterpolator
    public boolean isForward() {
        return this.forward;
    }

    @Override // org.apache.commons.math.ode.sampling.StepInterpolator
    public double[] getInterpolatedState() throws org.apache.commons.math.ode.DerivativeException {
        if (this.dirtyState) {
            double d = this.globalCurrentTime - this.interpolatedTime;
            computeInterpolatedStateAndDerivatives(this.h != 0.0d ? (this.h - d) / this.h : 0.0d, d);
            this.dirtyState = false;
        }
        return this.interpolatedState;
    }

    @Override // org.apache.commons.math.ode.sampling.StepInterpolator
    public double[] getInterpolatedDerivatives() throws org.apache.commons.math.ode.DerivativeException {
        if (this.dirtyState) {
            double d = this.globalCurrentTime - this.interpolatedTime;
            computeInterpolatedStateAndDerivatives(this.h != 0.0d ? (this.h - d) / this.h : 0.0d, d);
            this.dirtyState = false;
        }
        return this.interpolatedDerivatives;
    }

    public final void finalizeStep() throws org.apache.commons.math.ode.DerivativeException {
        if (!this.finalized) {
            doFinalize();
            this.finalized = true;
        }
    }

    protected void doFinalize() throws org.apache.commons.math.ode.DerivativeException {
    }

    protected void writeBaseExternal(java.io.ObjectOutput objectOutput) throws java.io.IOException {
        if (this.currentState == null) {
            objectOutput.writeInt(-1);
        } else {
            objectOutput.writeInt(this.currentState.length);
        }
        objectOutput.writeDouble(this.globalPreviousTime);
        objectOutput.writeDouble(this.globalCurrentTime);
        objectOutput.writeDouble(this.softPreviousTime);
        objectOutput.writeDouble(this.softCurrentTime);
        objectOutput.writeDouble(this.h);
        objectOutput.writeBoolean(this.forward);
        if (this.currentState != null) {
            for (int i = 0; i < this.currentState.length; i++) {
                objectOutput.writeDouble(this.currentState[i]);
            }
        }
        objectOutput.writeDouble(this.interpolatedTime);
        try {
            finalizeStep();
        } catch (org.apache.commons.math.ode.DerivativeException e) {
            java.io.IOException iOException = new java.io.IOException(e.getLocalizedMessage());
            iOException.initCause(e);
            throw iOException;
        }
    }

    protected double readBaseExternal(java.io.ObjectInput objectInput) throws java.io.IOException {
        int readInt = objectInput.readInt();
        this.globalPreviousTime = objectInput.readDouble();
        this.globalCurrentTime = objectInput.readDouble();
        this.softPreviousTime = objectInput.readDouble();
        this.softCurrentTime = objectInput.readDouble();
        this.h = objectInput.readDouble();
        this.forward = objectInput.readBoolean();
        this.dirtyState = true;
        if (readInt < 0) {
            this.currentState = null;
        } else {
            this.currentState = new double[readInt];
            for (int i = 0; i < this.currentState.length; i++) {
                this.currentState[i] = objectInput.readDouble();
            }
        }
        this.interpolatedTime = Double.NaN;
        this.interpolatedState = readInt < 0 ? null : new double[readInt];
        this.interpolatedDerivatives = readInt >= 0 ? new double[readInt] : null;
        this.finalized = true;
        return objectInput.readDouble();
    }
}
