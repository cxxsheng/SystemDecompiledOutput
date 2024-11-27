package org.apache.commons.math.analysis.solvers;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public abstract class UnivariateRealSolverImpl extends org.apache.commons.math.ConvergingAlgorithmImpl implements org.apache.commons.math.analysis.solvers.UnivariateRealSolver {
    protected double defaultFunctionValueAccuracy;

    @java.lang.Deprecated
    protected org.apache.commons.math.analysis.UnivariateRealFunction f;
    protected double functionValue;
    protected double functionValueAccuracy;
    protected double result;
    protected boolean resultComputed;

    @java.lang.Deprecated
    protected UnivariateRealSolverImpl(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, int i, double d) {
        super(i, d);
        this.resultComputed = false;
        if (univariateRealFunction == null) {
            throw new org.apache.commons.math.exception.NullArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.FUNCTION);
        }
        this.f = univariateRealFunction;
        this.defaultFunctionValueAccuracy = 1.0E-15d;
        this.functionValueAccuracy = this.defaultFunctionValueAccuracy;
    }

    protected UnivariateRealSolverImpl(int i, double d) {
        super(i, d);
        this.resultComputed = false;
        this.defaultFunctionValueAccuracy = 1.0E-15d;
        this.functionValueAccuracy = this.defaultFunctionValueAccuracy;
    }

    protected void checkResultComputed() throws java.lang.IllegalStateException {
        if (!this.resultComputed) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.exception.util.LocalizedFormats.NO_RESULT_AVAILABLE, new java.lang.Object[0]);
        }
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    public double getResult() {
        checkResultComputed();
        return this.result;
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    public double getFunctionValue() {
        checkResultComputed();
        return this.functionValue;
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    public void setFunctionValueAccuracy(double d) {
        this.functionValueAccuracy = d;
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    public double getFunctionValueAccuracy() {
        return this.functionValueAccuracy;
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    public void resetFunctionValueAccuracy() {
        this.functionValueAccuracy = this.defaultFunctionValueAccuracy;
    }

    public double solve(int i, org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException {
        throw org.apache.commons.math.MathRuntimeException.createUnsupportedOperationException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_OVERRIDEN, new java.lang.Object[0]);
    }

    public double solve(int i, org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, double d3) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        throw org.apache.commons.math.MathRuntimeException.createUnsupportedOperationException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_OVERRIDEN, new java.lang.Object[0]);
    }

    protected final void setResult(double d, int i) {
        this.result = d;
        this.iterationCount = i;
        this.resultComputed = true;
    }

    protected final void setResult(double d, double d2, int i) {
        this.result = d;
        this.functionValue = d2;
        this.iterationCount = i;
        this.resultComputed = true;
    }

    protected final void clearResult() {
        this.iterationCount = 0;
        this.resultComputed = false;
    }

    protected boolean isBracketing(double d, double d2, org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction) throws org.apache.commons.math.FunctionEvaluationException {
        double value = univariateRealFunction.value(d);
        double value2 = univariateRealFunction.value(d2);
        return (value > 0.0d && value2 < 0.0d) || (value < 0.0d && value2 > 0.0d);
    }

    protected boolean isSequence(double d, double d2, double d3) {
        return d < d2 && d2 < d3;
    }

    protected void verifyInterval(double d, double d2) {
        if (d >= d2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.ENDPOINTS_NOT_AN_INTERVAL, java.lang.Double.valueOf(d), java.lang.Double.valueOf(d2));
        }
    }

    protected void verifySequence(double d, double d2, double d3) {
        if (!isSequence(d, d2, d3)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INVALID_INTERVAL_INITIAL_VALUE_PARAMETERS, java.lang.Double.valueOf(d), java.lang.Double.valueOf(d2), java.lang.Double.valueOf(d3));
        }
    }

    protected void verifyBracketing(double d, double d2, org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction) throws org.apache.commons.math.FunctionEvaluationException {
        verifyInterval(d, d2);
        if (!isBracketing(d, d2, univariateRealFunction)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.SAME_SIGN_AT_ENDPOINTS, java.lang.Double.valueOf(d), java.lang.Double.valueOf(d2), java.lang.Double.valueOf(univariateRealFunction.value(d)), java.lang.Double.valueOf(univariateRealFunction.value(d2)));
        }
    }
}
