package org.apache.commons.math.ode;

/* loaded from: classes3.dex */
public class ContinuousOutputModel implements org.apache.commons.math.ode.sampling.StepHandler, java.io.Serializable {
    private static final long serialVersionUID = -1417964919405031606L;
    private double finalTime;
    private boolean forward;
    private int index;
    private double initialTime;
    private java.util.List<org.apache.commons.math.ode.sampling.StepInterpolator> steps = new java.util.ArrayList();

    public ContinuousOutputModel() {
        reset();
    }

    public void append(org.apache.commons.math.ode.ContinuousOutputModel continuousOutputModel) throws org.apache.commons.math.ode.DerivativeException {
        if (continuousOutputModel.steps.size() == 0) {
            return;
        }
        if (this.steps.size() == 0) {
            this.initialTime = continuousOutputModel.initialTime;
            this.forward = continuousOutputModel.forward;
        } else {
            if (getInterpolatedState().length != continuousOutputModel.getInterpolatedState().length) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(getInterpolatedState().length), java.lang.Integer.valueOf(continuousOutputModel.getInterpolatedState().length));
            }
            if (this.forward ^ continuousOutputModel.forward) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.PROPAGATION_DIRECTION_MISMATCH, new java.lang.Object[0]);
            }
            org.apache.commons.math.ode.sampling.StepInterpolator stepInterpolator = this.steps.get(this.index);
            double currentTime = stepInterpolator.getCurrentTime();
            double previousTime = currentTime - stepInterpolator.getPreviousTime();
            double initialTime = continuousOutputModel.getInitialTime() - currentTime;
            if (org.apache.commons.math.util.FastMath.abs(initialTime) > org.apache.commons.math.util.FastMath.abs(previousTime) * 0.001d) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.HOLE_BETWEEN_MODELS_TIME_RANGES, java.lang.Double.valueOf(org.apache.commons.math.util.FastMath.abs(initialTime)));
            }
        }
        java.util.Iterator<org.apache.commons.math.ode.sampling.StepInterpolator> it = continuousOutputModel.steps.iterator();
        while (it.hasNext()) {
            this.steps.add(it.next().copy());
        }
        this.index = this.steps.size() - 1;
        this.finalTime = this.steps.get(this.index).getCurrentTime();
    }

    @Override // org.apache.commons.math.ode.sampling.StepHandler
    public boolean requiresDenseOutput() {
        return true;
    }

    @Override // org.apache.commons.math.ode.sampling.StepHandler
    public void reset() {
        this.initialTime = Double.NaN;
        this.finalTime = Double.NaN;
        this.forward = true;
        this.index = 0;
        this.steps.clear();
    }

    @Override // org.apache.commons.math.ode.sampling.StepHandler
    public void handleStep(org.apache.commons.math.ode.sampling.StepInterpolator stepInterpolator, boolean z) throws org.apache.commons.math.ode.DerivativeException {
        if (this.steps.size() == 0) {
            this.initialTime = stepInterpolator.getPreviousTime();
            this.forward = stepInterpolator.isForward();
        }
        this.steps.add(stepInterpolator.copy());
        if (z) {
            this.finalTime = stepInterpolator.getCurrentTime();
            this.index = this.steps.size() - 1;
        }
    }

    public double getInitialTime() {
        return this.initialTime;
    }

    public double getFinalTime() {
        return this.finalTime;
    }

    public double getInterpolatedTime() {
        return this.steps.get(this.index).getInterpolatedTime();
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00fc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setInterpolatedTime(double d) {
        int max;
        int i = 0;
        org.apache.commons.math.ode.sampling.StepInterpolator stepInterpolator = this.steps.get(0);
        double d2 = 0.5d;
        double previousTime = (stepInterpolator.getPreviousTime() + stepInterpolator.getCurrentTime()) * 0.5d;
        int size = this.steps.size() - 1;
        org.apache.commons.math.ode.sampling.StepInterpolator stepInterpolator2 = this.steps.get(size);
        double previousTime2 = (stepInterpolator2.getPreviousTime() + stepInterpolator2.getCurrentTime()) * 0.5d;
        if (locatePoint(d, stepInterpolator) <= 0) {
            this.index = 0;
            stepInterpolator.setInterpolatedTime(d);
            return;
        }
        if (locatePoint(d, stepInterpolator2) >= 0) {
            this.index = size;
            stepInterpolator2.setInterpolatedTime(d);
            return;
        }
        while (size - i > 5) {
            org.apache.commons.math.ode.sampling.StepInterpolator stepInterpolator3 = this.steps.get(this.index);
            int locatePoint = locatePoint(d, stepInterpolator3);
            if (locatePoint < 0) {
                size = this.index;
                previousTime2 = (stepInterpolator3.getPreviousTime() + stepInterpolator3.getCurrentTime()) * d2;
            } else if (locatePoint > 0) {
                i = this.index;
                previousTime = (stepInterpolator3.getPreviousTime() + stepInterpolator3.getCurrentTime()) * d2;
            } else {
                stepInterpolator3.setInterpolatedTime(d);
                return;
            }
            int i2 = (i + size) / 2;
            org.apache.commons.math.ode.sampling.StepInterpolator stepInterpolator4 = this.steps.get(i2);
            double previousTime3 = (stepInterpolator4.getPreviousTime() + stepInterpolator4.getCurrentTime()) * d2;
            double d3 = previousTime3 - previousTime;
            if (org.apache.commons.math.util.FastMath.abs(d3) >= 1.0E-6d) {
                double d4 = previousTime2 - previousTime3;
                if (org.apache.commons.math.util.FastMath.abs(d4) >= 1.0E-6d) {
                    double d5 = previousTime2 - previousTime;
                    double d6 = d - previousTime2;
                    double d7 = d - previousTime3;
                    double d8 = d - previousTime;
                    this.index = (int) org.apache.commons.math.util.FastMath.rint((((((d7 * d8) * d3) * size) - (((d8 * d6) * d5) * i2)) + (((d6 * d7) * d4) * i)) / ((d4 * d3) * d5));
                    max = org.apache.commons.math.util.FastMath.max(i + 1, ((i * 9) + size) / 10);
                    int min = org.apache.commons.math.util.FastMath.min(size - 1, ((size * 9) + i) / 10);
                    if (this.index >= max) {
                        this.index = max;
                    } else if (this.index > min) {
                        this.index = min;
                    }
                    d2 = 0.5d;
                }
            }
            this.index = i2;
            max = org.apache.commons.math.util.FastMath.max(i + 1, ((i * 9) + size) / 10);
            int min2 = org.apache.commons.math.util.FastMath.min(size - 1, ((size * 9) + i) / 10);
            if (this.index >= max) {
            }
            d2 = 0.5d;
        }
        this.index = i;
        while (this.index <= size && locatePoint(d, this.steps.get(this.index)) > 0) {
            this.index++;
        }
        this.steps.get(this.index).setInterpolatedTime(d);
    }

    public double[] getInterpolatedState() throws org.apache.commons.math.ode.DerivativeException {
        return this.steps.get(this.index).getInterpolatedState();
    }

    private int locatePoint(double d, org.apache.commons.math.ode.sampling.StepInterpolator stepInterpolator) {
        if (this.forward) {
            if (d < stepInterpolator.getPreviousTime()) {
                return -1;
            }
            return d > stepInterpolator.getCurrentTime() ? 1 : 0;
        }
        if (d > stepInterpolator.getPreviousTime()) {
            return -1;
        }
        return d < stepInterpolator.getCurrentTime() ? 1 : 0;
    }
}
