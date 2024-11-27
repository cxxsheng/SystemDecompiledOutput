package org.apache.commons.math.ode.sampling;

/* loaded from: classes3.dex */
public class NordsieckStepInterpolator extends org.apache.commons.math.ode.sampling.AbstractStepInterpolator {
    private static final long serialVersionUID = -7179861704951334960L;
    private org.apache.commons.math.linear.Array2DRowRealMatrix nordsieck;
    private double referenceTime;
    private double[] scaled;
    private double scalingH;
    protected double[] stateVariation;

    public NordsieckStepInterpolator() {
    }

    public NordsieckStepInterpolator(org.apache.commons.math.ode.sampling.NordsieckStepInterpolator nordsieckStepInterpolator) {
        super(nordsieckStepInterpolator);
        this.scalingH = nordsieckStepInterpolator.scalingH;
        this.referenceTime = nordsieckStepInterpolator.referenceTime;
        if (nordsieckStepInterpolator.scaled != null) {
            this.scaled = (double[]) nordsieckStepInterpolator.scaled.clone();
        }
        if (nordsieckStepInterpolator.nordsieck != null) {
            this.nordsieck = new org.apache.commons.math.linear.Array2DRowRealMatrix(nordsieckStepInterpolator.nordsieck.getDataRef(), true);
        }
        if (nordsieckStepInterpolator.stateVariation != null) {
            this.stateVariation = (double[]) nordsieckStepInterpolator.stateVariation.clone();
        }
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    protected org.apache.commons.math.ode.sampling.StepInterpolator doCopy() {
        return new org.apache.commons.math.ode.sampling.NordsieckStepInterpolator(this);
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    public void reinitialize(double[] dArr, boolean z) {
        super.reinitialize(dArr, z);
        this.stateVariation = new double[dArr.length];
    }

    public void reinitialize(double d, double d2, double[] dArr, org.apache.commons.math.linear.Array2DRowRealMatrix array2DRowRealMatrix) {
        this.referenceTime = d;
        this.scalingH = d2;
        this.scaled = dArr;
        this.nordsieck = array2DRowRealMatrix;
        setInterpolatedTime(getInterpolatedTime());
    }

    public void rescale(double d) {
        double d2 = d / this.scalingH;
        for (int i = 0; i < this.scaled.length; i++) {
            double[] dArr = this.scaled;
            dArr[i] = dArr[i] * d2;
        }
        double d3 = d2;
        for (double[] dArr2 : this.nordsieck.getDataRef()) {
            d3 *= d2;
            for (int i2 = 0; i2 < dArr2.length; i2++) {
                dArr2[i2] = dArr2[i2] * d3;
            }
        }
        this.scalingH = d;
    }

    public double[] getInterpolatedStateVariation() throws org.apache.commons.math.ode.DerivativeException {
        getInterpolatedState();
        return this.stateVariation;
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    protected void computeInterpolatedStateAndDerivatives(double d, double d2) {
        int i;
        double d3 = this.interpolatedTime - this.referenceTime;
        double d4 = d3 / this.scalingH;
        java.util.Arrays.fill(this.stateVariation, 0.0d);
        java.util.Arrays.fill(this.interpolatedDerivatives, 0.0d);
        double[][] dataRef = this.nordsieck.getDataRef();
        int length = dataRef.length;
        while (true) {
            length--;
            i = 0;
            if (length < 0) {
                break;
            }
            double[] dArr = dataRef[length];
            double d5 = length + 2;
            double pow = org.apache.commons.math.util.FastMath.pow(d4, d5);
            while (i < dArr.length) {
                double d6 = dArr[i] * pow;
                double[] dArr2 = this.stateVariation;
                dArr2[i] = dArr2[i] + d6;
                double[] dArr3 = this.interpolatedDerivatives;
                dArr3[i] = dArr3[i] + (d6 * d5);
                i++;
            }
        }
        while (i < this.currentState.length) {
            double[] dArr4 = this.stateVariation;
            dArr4[i] = dArr4[i] + (this.scaled[i] * d4);
            this.interpolatedState[i] = this.currentState[i] + this.stateVariation[i];
            this.interpolatedDerivatives[i] = (this.interpolatedDerivatives[i] + (this.scaled[i] * d4)) / d3;
            i++;
        }
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator, java.io.Externalizable
    public void writeExternal(java.io.ObjectOutput objectOutput) throws java.io.IOException {
        writeBaseExternal(objectOutput);
        objectOutput.writeDouble(this.scalingH);
        objectOutput.writeDouble(this.referenceTime);
        int length = this.currentState == null ? -1 : this.currentState.length;
        if (this.scaled == null) {
            objectOutput.writeBoolean(false);
        } else {
            objectOutput.writeBoolean(true);
            for (int i = 0; i < length; i++) {
                objectOutput.writeDouble(this.scaled[i]);
            }
        }
        if (this.nordsieck == null) {
            objectOutput.writeBoolean(false);
        } else {
            objectOutput.writeBoolean(true);
            objectOutput.writeObject(this.nordsieck);
        }
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator, java.io.Externalizable
    public void readExternal(java.io.ObjectInput objectInput) throws java.io.IOException, java.lang.ClassNotFoundException {
        double readBaseExternal = readBaseExternal(objectInput);
        this.scalingH = objectInput.readDouble();
        this.referenceTime = objectInput.readDouble();
        int length = this.currentState == null ? -1 : this.currentState.length;
        boolean readBoolean = objectInput.readBoolean();
        if (readBoolean) {
            this.scaled = new double[length];
            for (int i = 0; i < length; i++) {
                this.scaled[i] = objectInput.readDouble();
            }
        } else {
            this.scaled = null;
        }
        boolean readBoolean2 = objectInput.readBoolean();
        if (readBoolean2) {
            this.nordsieck = (org.apache.commons.math.linear.Array2DRowRealMatrix) objectInput.readObject();
        } else {
            this.nordsieck = null;
        }
        if (readBoolean && readBoolean2) {
            this.stateVariation = new double[length];
            setInterpolatedTime(readBaseExternal);
        } else {
            this.stateVariation = null;
        }
    }
}
