package org.apache.commons.math.ode.jacobians;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class FirstOrderIntegratorWithJacobians {
    private int evaluations;
    private final org.apache.commons.math.ode.FirstOrderIntegrator integrator;
    private int maxEvaluations;
    private final org.apache.commons.math.ode.jacobians.ODEWithJacobians ode;

    static /* synthetic */ int access$104(org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians firstOrderIntegratorWithJacobians) {
        int i = firstOrderIntegratorWithJacobians.evaluations + 1;
        firstOrderIntegratorWithJacobians.evaluations = i;
        return i;
    }

    static /* synthetic */ int access$112(org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians firstOrderIntegratorWithJacobians, int i) {
        int i2 = firstOrderIntegratorWithJacobians.evaluations + i;
        firstOrderIntegratorWithJacobians.evaluations = i2;
        return i2;
    }

    public FirstOrderIntegratorWithJacobians(org.apache.commons.math.ode.FirstOrderIntegrator firstOrderIntegrator, org.apache.commons.math.ode.jacobians.ParameterizedODE parameterizedODE, double[] dArr, double[] dArr2, double[] dArr3) {
        checkDimension(parameterizedODE.getDimension(), dArr2);
        checkDimension(parameterizedODE.getParametersDimension(), dArr);
        checkDimension(parameterizedODE.getParametersDimension(), dArr3);
        this.integrator = firstOrderIntegrator;
        this.ode = new org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.FiniteDifferencesWrapper(parameterizedODE, dArr, dArr2, dArr3);
        setMaxEvaluations(-1);
    }

    public FirstOrderIntegratorWithJacobians(org.apache.commons.math.ode.FirstOrderIntegrator firstOrderIntegrator, org.apache.commons.math.ode.jacobians.ODEWithJacobians oDEWithJacobians) {
        this.integrator = firstOrderIntegrator;
        this.ode = oDEWithJacobians;
        setMaxEvaluations(-1);
    }

    public void addStepHandler(org.apache.commons.math.ode.jacobians.StepHandlerWithJacobians stepHandlerWithJacobians) {
        this.integrator.addStepHandler(new org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.StepHandlerWrapper(stepHandlerWithJacobians, this.ode.getDimension(), this.ode.getParametersDimension()));
    }

    public java.util.Collection<org.apache.commons.math.ode.jacobians.StepHandlerWithJacobians> getStepHandlers() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (org.apache.commons.math.ode.sampling.StepHandler stepHandler : this.integrator.getStepHandlers()) {
            if (stepHandler instanceof org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.StepHandlerWrapper) {
                arrayList.add(((org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.StepHandlerWrapper) stepHandler).getHandler());
            }
        }
        return arrayList;
    }

    public void clearStepHandlers() {
        this.integrator.clearStepHandlers();
    }

    public void addEventHandler(org.apache.commons.math.ode.jacobians.EventHandlerWithJacobians eventHandlerWithJacobians, double d, double d2, int i) {
        this.integrator.addEventHandler(new org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.EventHandlerWrapper(eventHandlerWithJacobians, this.ode.getDimension(), this.ode.getParametersDimension()), d, d2, i);
    }

    public java.util.Collection<org.apache.commons.math.ode.jacobians.EventHandlerWithJacobians> getEventHandlers() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (org.apache.commons.math.ode.events.EventHandler eventHandler : this.integrator.getEventHandlers()) {
            if (eventHandler instanceof org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.EventHandlerWrapper) {
                arrayList.add(((org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.EventHandlerWrapper) eventHandler).getHandler());
            }
        }
        return arrayList;
    }

    public void clearEventHandlers() {
        this.integrator.clearEventHandlers();
    }

    public double integrate(double d, double[] dArr, double[][] dArr2, double d2, double[] dArr3, double[][] dArr4, double[][] dArr5) throws org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.IntegratorException {
        int dimension = this.ode.getDimension();
        int parametersDimension = this.ode.getParametersDimension();
        checkDimension(dimension, dArr);
        checkDimension(dimension, dArr3);
        checkDimension(dimension, dArr4);
        checkDimension(dimension, dArr4[0]);
        if (parametersDimension != 0) {
            checkDimension(dimension, dArr2);
            checkDimension(parametersDimension, dArr2[0]);
            checkDimension(dimension, dArr5);
            checkDimension(parametersDimension, dArr5[0]);
        }
        int i = dimension + 1;
        double[] dArr6 = new double[(i + parametersDimension) * dimension];
        java.lang.System.arraycopy(dArr, 0, dArr6, 0, dimension);
        for (int i2 = 0; i2 < dimension; i2++) {
            dArr6[(i2 * i) + dimension] = 1.0d;
            java.lang.System.arraycopy(dArr2[i2], 0, dArr6, (dimension * i) + (i2 * parametersDimension), parametersDimension);
        }
        this.evaluations = 0;
        double integrate = this.integrator.integrate(new org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.MappingWrapper(), d, dArr6, d2, dArr6);
        dispatchCompoundState(dArr6, dArr3, dArr4, dArr5);
        return integrate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void dispatchCompoundState(double[] dArr, double[] dArr2, double[][] dArr3, double[][] dArr4) {
        int length = dArr2.length;
        int length2 = dArr4[0].length;
        java.lang.System.arraycopy(dArr, 0, dArr2, 0, length);
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            java.lang.System.arraycopy(dArr, length * i2, dArr3[i], 0, length);
            i = i2;
        }
        for (int i3 = 0; i3 < length; i3++) {
            java.lang.System.arraycopy(dArr, ((length + 1) * length) + (i3 * length2), dArr4[i3], 0, length2);
        }
    }

    public double getCurrentStepStart() {
        return this.integrator.getCurrentStepStart();
    }

    public double getCurrentSignedStepsize() {
        return this.integrator.getCurrentSignedStepsize();
    }

    public void setMaxEvaluations(int i) {
        if (i < 0) {
            i = Integer.MAX_VALUE;
        }
        this.maxEvaluations = i;
    }

    public int getMaxEvaluations() {
        return this.maxEvaluations;
    }

    public int getEvaluations() {
        return this.evaluations;
    }

    private void checkDimension(int i, java.lang.Object obj) throws java.lang.IllegalArgumentException {
        int length = obj == null ? 0 : java.lang.reflect.Array.getLength(obj);
        if (length != i) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(length), java.lang.Integer.valueOf(i));
        }
    }

    private class MappingWrapper implements org.apache.commons.math.ode.ExtendedFirstOrderDifferentialEquations {
        private final double[][] dFdP;
        private final double[][] dFdY;
        private final double[] y;
        private final double[] yDot;

        public MappingWrapper() {
            int dimension = org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.this.ode.getDimension();
            int parametersDimension = org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.this.ode.getParametersDimension();
            this.y = new double[dimension];
            this.yDot = new double[dimension];
            this.dFdY = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, dimension, dimension);
            this.dFdP = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, dimension, parametersDimension);
        }

        @Override // org.apache.commons.math.ode.FirstOrderDifferentialEquations
        public int getDimension() {
            int length = this.y.length;
            return length * (length + 1 + this.dFdP[0].length);
        }

        @Override // org.apache.commons.math.ode.ExtendedFirstOrderDifferentialEquations
        public int getMainSetDimension() {
            return org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.this.ode.getDimension();
        }

        @Override // org.apache.commons.math.ode.FirstOrderDifferentialEquations
        public void computeDerivatives(double d, double[] dArr, double[] dArr2) throws org.apache.commons.math.ode.DerivativeException {
            int length = this.y.length;
            int length2 = this.dFdP[0].length;
            java.lang.System.arraycopy(dArr, 0, this.y, 0, length);
            if (org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.access$104(org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.this) <= org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.this.maxEvaluations) {
                org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.this.ode.computeDerivatives(d, this.y, this.yDot);
                org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.this.ode.computeJacobians(d, this.y, this.yDot, this.dFdY, this.dFdP);
                java.lang.System.arraycopy(this.yDot, 0, dArr2, 0, length);
                for (int i = 0; i < length; i++) {
                    double[] dArr3 = this.dFdY[i];
                    for (int i2 = 0; i2 < length; i2++) {
                        int i3 = length + i2;
                        double d2 = 0.0d;
                        int i4 = i3;
                        for (int i5 = 0; i5 < length; i5++) {
                            d2 += dArr3[i5] * dArr[i4];
                            i4 += length;
                        }
                        dArr2[i3 + (i * length)] = d2;
                    }
                }
                for (int i6 = 0; i6 < length; i6++) {
                    double[] dArr4 = this.dFdY[i6];
                    double[] dArr5 = this.dFdP[i6];
                    for (int i7 = 0; i7 < length2; i7++) {
                        double d3 = dArr5[i7];
                        int i8 = ((length + 1) * length) + i7;
                        int i9 = i8;
                        for (int i10 = 0; i10 < length; i10++) {
                            d3 += dArr4[i10] * dArr[i9];
                            i9 += length2;
                        }
                        dArr2[i8 + (i6 * length2)] = d3;
                    }
                }
                return;
            }
            throw new org.apache.commons.math.ode.DerivativeException(new org.apache.commons.math.MaxEvaluationsExceededException(org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.this.maxEvaluations));
        }
    }

    private class FiniteDifferencesWrapper implements org.apache.commons.math.ode.jacobians.ODEWithJacobians {
        private final double[] hP;
        private final double[] hY;
        private final org.apache.commons.math.ode.jacobians.ParameterizedODE ode;
        private final double[] p;
        private final double[] tmpDot;

        public FiniteDifferencesWrapper(org.apache.commons.math.ode.jacobians.ParameterizedODE parameterizedODE, double[] dArr, double[] dArr2, double[] dArr3) {
            this.ode = parameterizedODE;
            this.p = (double[]) dArr.clone();
            this.hY = (double[]) dArr2.clone();
            this.hP = (double[]) dArr3.clone();
            this.tmpDot = new double[parameterizedODE.getDimension()];
        }

        @Override // org.apache.commons.math.ode.FirstOrderDifferentialEquations
        public int getDimension() {
            return this.ode.getDimension();
        }

        @Override // org.apache.commons.math.ode.FirstOrderDifferentialEquations
        public void computeDerivatives(double d, double[] dArr, double[] dArr2) throws org.apache.commons.math.ode.DerivativeException {
            this.ode.computeDerivatives(d, dArr, dArr2);
        }

        @Override // org.apache.commons.math.ode.jacobians.ODEWithJacobians
        public int getParametersDimension() {
            return this.ode.getParametersDimension();
        }

        @Override // org.apache.commons.math.ode.jacobians.ODEWithJacobians
        public void computeJacobians(double d, double[] dArr, double[] dArr2, double[][] dArr3, double[][] dArr4) throws org.apache.commons.math.ode.DerivativeException {
            int length = this.hY.length;
            int length2 = this.hP.length;
            org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.access$112(org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.this, length + length2);
            if (org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.this.evaluations > org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.this.maxEvaluations) {
                throw new org.apache.commons.math.ode.DerivativeException(new org.apache.commons.math.MaxEvaluationsExceededException(org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.this.maxEvaluations));
            }
            for (int i = 0; i < length; i++) {
                double d2 = dArr[i];
                dArr[i] = dArr[i] + this.hY[i];
                this.ode.computeDerivatives(d, dArr, this.tmpDot);
                for (int i2 = 0; i2 < length; i2++) {
                    dArr3[i2][i] = (this.tmpDot[i2] - dArr2[i2]) / this.hY[i];
                }
                dArr[i] = d2;
            }
            for (int i3 = 0; i3 < length2; i3++) {
                this.ode.setParameter(i3, this.p[i3] + this.hP[i3]);
                this.ode.computeDerivatives(d, dArr, this.tmpDot);
                for (int i4 = 0; i4 < length; i4++) {
                    dArr4[i4][i3] = (this.tmpDot[i4] - dArr2[i4]) / this.hP[i3];
                }
                this.ode.setParameter(i3, this.p[i3]);
            }
        }
    }

    private static class StepHandlerWrapper implements org.apache.commons.math.ode.sampling.StepHandler {
        private final org.apache.commons.math.ode.jacobians.StepHandlerWithJacobians handler;
        private final int k;
        private final int n;

        public StepHandlerWrapper(org.apache.commons.math.ode.jacobians.StepHandlerWithJacobians stepHandlerWithJacobians, int i, int i2) {
            this.handler = stepHandlerWithJacobians;
            this.n = i;
            this.k = i2;
        }

        public org.apache.commons.math.ode.jacobians.StepHandlerWithJacobians getHandler() {
            return this.handler;
        }

        @Override // org.apache.commons.math.ode.sampling.StepHandler
        public void handleStep(org.apache.commons.math.ode.sampling.StepInterpolator stepInterpolator, boolean z) throws org.apache.commons.math.ode.DerivativeException {
            this.handler.handleStep(new org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.StepInterpolatorWrapper(stepInterpolator, this.n, this.k), z);
        }

        @Override // org.apache.commons.math.ode.sampling.StepHandler
        public boolean requiresDenseOutput() {
            return this.handler.requiresDenseOutput();
        }

        @Override // org.apache.commons.math.ode.sampling.StepHandler
        public void reset() {
            this.handler.reset();
        }
    }

    private static class StepInterpolatorWrapper implements org.apache.commons.math.ode.jacobians.StepInterpolatorWithJacobians {
        private double[][] dydp;
        private double[][] dydpDot;
        private double[][] dydy0;
        private double[][] dydy0Dot;
        private org.apache.commons.math.ode.sampling.StepInterpolator interpolator;
        private double[] y;
        private double[] yDot;

        public StepInterpolatorWrapper() {
        }

        public StepInterpolatorWrapper(org.apache.commons.math.ode.sampling.StepInterpolator stepInterpolator, int i, int i2) {
            this.interpolator = stepInterpolator;
            this.y = new double[i];
            this.dydy0 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, i, i);
            this.dydp = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, i, i2);
            this.yDot = new double[i];
            this.dydy0Dot = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, i, i);
            this.dydpDot = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, i, i2);
        }

        @Override // org.apache.commons.math.ode.jacobians.StepInterpolatorWithJacobians
        public void setInterpolatedTime(double d) {
            this.interpolator.setInterpolatedTime(d);
        }

        @Override // org.apache.commons.math.ode.jacobians.StepInterpolatorWithJacobians
        public boolean isForward() {
            return this.interpolator.isForward();
        }

        @Override // org.apache.commons.math.ode.jacobians.StepInterpolatorWithJacobians
        public double getPreviousTime() {
            return this.interpolator.getPreviousTime();
        }

        @Override // org.apache.commons.math.ode.jacobians.StepInterpolatorWithJacobians
        public double getInterpolatedTime() {
            return this.interpolator.getInterpolatedTime();
        }

        @Override // org.apache.commons.math.ode.jacobians.StepInterpolatorWithJacobians
        public double[] getInterpolatedY() throws org.apache.commons.math.ode.DerivativeException {
            java.lang.System.arraycopy(this.interpolator.getInterpolatedState(), 0, this.y, 0, this.y.length);
            return this.y;
        }

        @Override // org.apache.commons.math.ode.jacobians.StepInterpolatorWithJacobians
        public double[][] getInterpolatedDyDy0() throws org.apache.commons.math.ode.DerivativeException {
            double[] interpolatedState = this.interpolator.getInterpolatedState();
            int length = this.y.length;
            int i = length;
            for (int i2 = 0; i2 < length; i2++) {
                java.lang.System.arraycopy(interpolatedState, i, this.dydy0[i2], 0, length);
                i += length;
            }
            return this.dydy0;
        }

        @Override // org.apache.commons.math.ode.jacobians.StepInterpolatorWithJacobians
        public double[][] getInterpolatedDyDp() throws org.apache.commons.math.ode.DerivativeException {
            double[] interpolatedState = this.interpolator.getInterpolatedState();
            int length = this.y.length;
            int length2 = this.dydp[0].length;
            int i = (length + 1) * length;
            for (int i2 = 0; i2 < length; i2++) {
                java.lang.System.arraycopy(interpolatedState, i, this.dydp[i2], 0, length2);
                i += length2;
            }
            return this.dydp;
        }

        @Override // org.apache.commons.math.ode.jacobians.StepInterpolatorWithJacobians
        public double[] getInterpolatedYDot() throws org.apache.commons.math.ode.DerivativeException {
            java.lang.System.arraycopy(this.interpolator.getInterpolatedDerivatives(), 0, this.yDot, 0, this.yDot.length);
            return this.yDot;
        }

        @Override // org.apache.commons.math.ode.jacobians.StepInterpolatorWithJacobians
        public double[][] getInterpolatedDyDy0Dot() throws org.apache.commons.math.ode.DerivativeException {
            double[] interpolatedDerivatives = this.interpolator.getInterpolatedDerivatives();
            int length = this.y.length;
            int i = length;
            for (int i2 = 0; i2 < length; i2++) {
                java.lang.System.arraycopy(interpolatedDerivatives, i, this.dydy0Dot[i2], 0, length);
                i += length;
            }
            return this.dydy0Dot;
        }

        @Override // org.apache.commons.math.ode.jacobians.StepInterpolatorWithJacobians
        public double[][] getInterpolatedDyDpDot() throws org.apache.commons.math.ode.DerivativeException {
            double[] interpolatedDerivatives = this.interpolator.getInterpolatedDerivatives();
            int length = this.y.length;
            int length2 = this.dydpDot[0].length;
            int i = (length + 1) * length;
            for (int i2 = 0; i2 < length; i2++) {
                java.lang.System.arraycopy(interpolatedDerivatives, i, this.dydpDot[i2], 0, length2);
                i += length2;
            }
            return this.dydpDot;
        }

        @Override // org.apache.commons.math.ode.jacobians.StepInterpolatorWithJacobians
        public double getCurrentTime() {
            return this.interpolator.getCurrentTime();
        }

        @Override // org.apache.commons.math.ode.jacobians.StepInterpolatorWithJacobians
        public org.apache.commons.math.ode.jacobians.StepInterpolatorWithJacobians copy() throws org.apache.commons.math.ode.DerivativeException {
            org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.StepInterpolatorWrapper stepInterpolatorWrapper = new org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.StepInterpolatorWrapper(this.interpolator.copy(), this.y.length, this.dydp[0].length);
            copyArray(this.y, stepInterpolatorWrapper.y);
            copyArray(this.dydy0, stepInterpolatorWrapper.dydy0);
            copyArray(this.dydp, stepInterpolatorWrapper.dydp);
            copyArray(this.yDot, stepInterpolatorWrapper.yDot);
            copyArray(this.dydy0Dot, stepInterpolatorWrapper.dydy0Dot);
            copyArray(this.dydpDot, stepInterpolatorWrapper.dydpDot);
            return stepInterpolatorWrapper;
        }

        @Override // java.io.Externalizable
        public void writeExternal(java.io.ObjectOutput objectOutput) throws java.io.IOException {
            objectOutput.writeObject(this.interpolator);
            objectOutput.writeInt(this.y.length);
            objectOutput.writeInt(this.dydp[0].length);
            writeArray(objectOutput, this.y);
            writeArray(objectOutput, this.dydy0);
            writeArray(objectOutput, this.dydp);
            writeArray(objectOutput, this.yDot);
            writeArray(objectOutput, this.dydy0Dot);
            writeArray(objectOutput, this.dydpDot);
        }

        @Override // java.io.Externalizable
        public void readExternal(java.io.ObjectInput objectInput) throws java.io.IOException, java.lang.ClassNotFoundException {
            this.interpolator = (org.apache.commons.math.ode.sampling.StepInterpolator) objectInput.readObject();
            int readInt = objectInput.readInt();
            int readInt2 = objectInput.readInt();
            this.y = new double[readInt];
            this.dydy0 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, readInt, readInt);
            this.dydp = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, readInt, readInt2);
            this.yDot = new double[readInt];
            this.dydy0Dot = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, readInt, readInt);
            this.dydpDot = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, readInt, readInt2);
            readArray(objectInput, this.y);
            readArray(objectInput, this.dydy0);
            readArray(objectInput, this.dydp);
            readArray(objectInput, this.yDot);
            readArray(objectInput, this.dydy0Dot);
            readArray(objectInput, this.dydpDot);
        }

        private static void copyArray(double[] dArr, double[] dArr2) {
            java.lang.System.arraycopy(dArr, 0, dArr2, 0, dArr.length);
        }

        private static void copyArray(double[][] dArr, double[][] dArr2) {
            for (int i = 0; i < dArr.length; i++) {
                copyArray(dArr[i], dArr2[i]);
            }
        }

        private static void writeArray(java.io.ObjectOutput objectOutput, double[] dArr) throws java.io.IOException {
            for (double d : dArr) {
                objectOutput.writeDouble(d);
            }
        }

        private static void writeArray(java.io.ObjectOutput objectOutput, double[][] dArr) throws java.io.IOException {
            for (double[] dArr2 : dArr) {
                writeArray(objectOutput, dArr2);
            }
        }

        private static void readArray(java.io.ObjectInput objectInput, double[] dArr) throws java.io.IOException {
            for (int i = 0; i < dArr.length; i++) {
                dArr[i] = objectInput.readDouble();
            }
        }

        private static void readArray(java.io.ObjectInput objectInput, double[][] dArr) throws java.io.IOException {
            for (double[] dArr2 : dArr) {
                readArray(objectInput, dArr2);
            }
        }
    }

    private static class EventHandlerWrapper implements org.apache.commons.math.ode.events.EventHandler {
        private double[][] dydp;
        private double[][] dydy0;
        private final org.apache.commons.math.ode.jacobians.EventHandlerWithJacobians handler;
        private double[] y;

        public EventHandlerWrapper(org.apache.commons.math.ode.jacobians.EventHandlerWithJacobians eventHandlerWithJacobians, int i, int i2) {
            this.handler = eventHandlerWithJacobians;
            this.y = new double[i];
            this.dydy0 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, i, i);
            this.dydp = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, i, i2);
        }

        public org.apache.commons.math.ode.jacobians.EventHandlerWithJacobians getHandler() {
            return this.handler;
        }

        @Override // org.apache.commons.math.ode.events.EventHandler
        public int eventOccurred(double d, double[] dArr, boolean z) throws org.apache.commons.math.ode.events.EventException {
            org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.dispatchCompoundState(dArr, this.y, this.dydy0, this.dydp);
            return this.handler.eventOccurred(d, this.y, this.dydy0, this.dydp, z);
        }

        @Override // org.apache.commons.math.ode.events.EventHandler
        public double g(double d, double[] dArr) throws org.apache.commons.math.ode.events.EventException {
            org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.dispatchCompoundState(dArr, this.y, this.dydy0, this.dydp);
            return this.handler.g(d, this.y, this.dydy0, this.dydp);
        }

        @Override // org.apache.commons.math.ode.events.EventHandler
        public void resetState(double d, double[] dArr) throws org.apache.commons.math.ode.events.EventException {
            org.apache.commons.math.ode.jacobians.FirstOrderIntegratorWithJacobians.dispatchCompoundState(dArr, this.y, this.dydy0, this.dydp);
            this.handler.resetState(d, this.y, this.dydy0, this.dydp);
        }
    }
}
